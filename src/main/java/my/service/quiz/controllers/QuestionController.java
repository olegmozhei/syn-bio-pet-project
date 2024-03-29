package my.service.quiz.controllers;

import io.swagger.v3.oas.annotations.Operation;
import my.service.quiz.autogeneratedQuestions.IAutogeneratedQuestion;
import my.service.quiz.autogeneratedQuestions.Question1;
import my.service.quiz.autogeneratedQuestions.QuestionsGenerator;
import my.service.quiz.entities.OrdinaryQuestion;
import my.service.quiz.entities.Question;
import my.service.quiz.enums.QuestionType;
import my.service.quiz.repositoryInterfaces.IOrdinaryQuestionRepository;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;


@RestController
public class QuestionController {

    private final IOrdinaryQuestionRepository questionRepository;
    private final QuestionsGenerator questionsGenerator;

    public QuestionController(IOrdinaryQuestionRepository questionRepository, QuestionsGenerator questionsGenerator) {
        this.questionRepository = questionRepository;
        this.questionsGenerator = questionsGenerator;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @Operation(description = """
             0 - show answers and select correct;\n
             1 - type in free answer case-sensitive;\n
             2 - type in free answer case-insensitive;\n
             3 - type in formula (similar to type 2);\n
             4 - autogenerated free text case sensitive
            """)
    @RequestMapping(path = "/question", method = RequestMethod.POST, produces = "application/json")
    public String addNewQuestion(@RequestParam("Question type") int ordinal,
                                 @RequestParam("Task") String task,
                                 @RequestParam("Answer1") String answer1,
                                 @RequestParam(value = "Answer2", required = false) String answer2,
                                 @RequestParam(value = "Answer3", required = false) String answer3,
                                 @RequestParam(value = "Answer4", required = false) String answer4,
                                 @RequestParam(value = "Topic", required = false) String topic,
                                 @RequestParam(value = "Image", required = false) String image,
                                 @RequestParam(value = "ClassNumberIfAutogenerated", required = false) Integer number){

        // validate input:
        if (ordinal == 4 && (number == null || number == 0)) throw new RuntimeException("Please specify class number for autogenerated question");
        QuestionType questionType = QuestionType.values()[ordinal];

        OrdinaryQuestion q = new OrdinaryQuestion(questionType, task, answer1, answer2, answer3,answer4, topic, image, number);
        // new questions has default complexity 10
        q.complexity = 10;

        questionRepository.save(q);
        JSONObject result = new JSONObject();
        result.put("result", "done");
        return result.toString();
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(path = "/question/get_random", method = RequestMethod.GET, produces = "application/json")
    public String getRandomQuestion() {
        Question question = questionRepository.getRandomQuestion();
        if (question.type == QuestionType.AUTOGENERATED){
            // autogenerate Question
            question = questionsGenerator.generator.get(((OrdinaryQuestion) question).classNumber - 1).generateQuestion(question.topic);
        }
        JSONObject result = question.toJson();
        return result.toString();
    }
}
