package my.service.quiz.controllers;

import my.service.quiz.autogeneratedQuestions.QuestionsGenerator;
import my.service.quiz.entities.*;
import my.service.quiz.enums.QuestionType;
import my.service.quiz.repositoryInterfaces.IAutogeneratedQuestionRepository;
import my.service.quiz.repositoryInterfaces.IOrdinaryQuestionRepository;
import my.service.quiz.repositoryInterfaces.IQuizRepository;
import my.service.quiz.repositoryInterfaces.IQuizToQuestionRepository;
import my.service.utils.Utils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static my.service.utils.Utils.validateJson;

@RestController
public class QuizController {

    private final IOrdinaryQuestionRepository questionRepository;
    private final IAutogeneratedQuestionRepository autogeneratedQuestionsRepository;
    private final IQuizRepository quizRepository;
    private final IQuizToQuestionRepository quizToQuestionRepository;
    private final QuestionsGenerator questionsGenerator;
    private final Utils utils = new Utils();

    public QuizController(IOrdinaryQuestionRepository questionRepository, IAutogeneratedQuestionRepository autogeneratedQuestionsRepository, IQuizRepository quizRepository, IQuizToQuestionRepository quizToQuestionRepository, QuestionsGenerator questionsGenerator) {
        this.questionRepository = questionRepository;
        this.autogeneratedQuestionsRepository = autogeneratedQuestionsRepository;
        this.quizRepository = quizRepository;
        this.quizToQuestionRepository = quizToQuestionRepository;
        this.questionsGenerator = questionsGenerator;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(path = "/quiz/{uuid}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String addQuestionToQuiz(@PathVariable String uuid, @RequestBody String input) {
        JSONObject inputJson = new JSONObject(input);
        validateJson(inputJson, new String[] {"questionId"});

        // TODO: fix adding autogenerated questions
        QuizToQuestions qtq = new QuizToQuestions(quizRepository.findByUUID(uuid), questionRepository.findById(inputJson.getInt("questionId")).get(), null, 1);

        quizToQuestionRepository.save(qtq);
        JSONObject result = new JSONObject();
        result.put("result", "done");
        return result.toString();
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(path = "/quiz/{uuid}/question/{number}", method = RequestMethod.GET, produces = "application/json")
    public String getQuestion(@PathVariable String uuid, @PathVariable int number) {
        Set<QuizToQuestions> relations = quizRepository.findByUUID(uuid).relations;
        QuizToQuestions relation = relations.stream().filter(e->e.getOrder() == number).findFirst().get();
        Question question = (relation.autogeneratedQuestion == null) ? relation.question : relation.autogeneratedQuestion;
        return question.toJson().toString();
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(path = "/question/start_new_quiz", method = RequestMethod.POST, produces = "application/json")
    public String startNewQuiz(@RequestParam("Number of questions") int number,
                               @RequestParam(value = "Topic", required = false) String topic) {

        // prepare list of questions suitable for testing
        Iterable<OrdinaryQuestion> questions = (topic == null) ? questionRepository.findAll() : questionRepository.findByTopic(topic);
        List<OrdinaryQuestion> allQuestions = new ArrayList<>();
        questions.forEach(allQuestions::add);
        allQuestions = utils.shuffleQuestionsBasedOnComplexityAndLastCorrectAnswer(allQuestions);
        allQuestions = allQuestions.stream().limit(number).collect(Collectors.toList());

        // shuffle questions again (the most difficult should not go first)
        Collections.shuffle(allQuestions);

        // save new instance of quiz into database
        Quiz quiz = new Quiz();
        quiz = quizRepository.save(quiz);

        // prepare quiz - ordinaryQuestion - AutogeneratedQuestion relations:
        HashSet<QuizToQuestions> relations = new HashSet<>();
        for (int i = 1; i <= allQuestions.size(); i++) {
            OrdinaryQuestion ordinaryQuestion = allQuestions.get(i - 1);
            AutogeneratedQuestion autogeneratedQuestion = null;
            if (ordinaryQuestion.type == QuestionType.AUTOGENERATED){
                autogeneratedQuestion = questionsGenerator.generator.get(ordinaryQuestion.classNumber - 1).generateQuestion(ordinaryQuestion.topic);
                autogeneratedQuestionsRepository.save(autogeneratedQuestion);
            }
            relations.add(new QuizToQuestions(quiz, ordinaryQuestion, autogeneratedQuestion, i));
        }
        quiz.updateQuestions(relations);
        quizToQuestionRepository.saveAll(quiz.relations);

        // return first question and quiz instance information
        JSONObject result = quiz.getQuestions().get(0).toJson();
        result.put("quiz_uuid", quiz.uuid);
        result.put("total_questions", quiz.getQuestions().size());
        result.put("question_number", 1);

        return result.toString();
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(path = "/question/check", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String checkQuestion(@RequestParam("Question number") int number, @RequestParam("Quiz uuid") String uuid, @RequestBody String input) {
        JSONObject inputJson = new JSONObject(input);
        validateJson(inputJson, new String[] {"answer"});

        String answer = inputJson.getString("answer");

        Set<QuizToQuestions> relations = quizRepository.findByUUID(uuid).relations;
        QuizToQuestions relation = relations.stream().filter(e->e.getOrder() == number).findFirst().get();
        OrdinaryQuestion question = relation.question;
        Question questionToCheck = (relation.autogeneratedQuestion == null) ? question : relation.autogeneratedQuestion;

        JSONObject result = new JSONObject();
        result.put("result", questionToCheck.checkAnswer(answer));

        // modify complexity and last correct date:
        if (result.getString("result").equals("correct")){
            if (question.complexity > 1) question.complexity--;
            question.lastCorrectAnswer = LocalDateTime.now();
        } else {
            // TODO: try to think about penalty and max_complexity as env variables
            int penalty = 3;
            int maxComplexity = 10;
            if (question.complexity + penalty >= maxComplexity) question.complexity = question.complexity + penalty;
        }
        questionRepository.save(question);

        return result.toString();
    }


}
