package my.service.controller;

import my.service.database.implementations.*;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static my.service.utils.Utils.validateJson;

@RestController
public class QuizController {

    private final IQuestionRepository questionRepository;
    private final IQuizRepository quizRepository;
    private final IQuizToQuestionRepository quizToQuestionRepository;

    public QuizController(IQuestionRepository questionRepository, IQuizRepository quizRepository, IQuizToQuestionRepository quizToQuestionRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.quizToQuestionRepository = quizToQuestionRepository;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(path = "/quiz/{uuid}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String addQuestionToQuiz(@PathVariable String uuid, @RequestBody String input) {
        JSONObject inputJson = new JSONObject(input);
        validateJson(inputJson, new String[] {"questionId"});

        QuizToQuestions qtq = new QuizToQuestions(quizRepository.findByUUID(uuid), questionRepository.findById(inputJson.getInt("questionId")).get(),1);

        quizToQuestionRepository.save(qtq);
        JSONObject result = new JSONObject();
        result.put("result", "done");
        return result.toString();
    }


    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(path = "/question/get_random", method = RequestMethod.GET, produces = "application/json")
    public String getRandomQuestion() {
        Question question = questionRepository.getRandonQuestion();
        JSONObject result = question.toJson();
        return result.toString();
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(path = "/quiz/{uuid}/question/{number}", method = RequestMethod.GET, produces = "application/json")
    public String getQuestion(@PathVariable String uuid, @PathVariable int number) {
        Set<QuizToQuestions> relations = quizRepository.findByUUID(uuid).relations;
        Question question = relations.stream().filter(e->e.getOrder() == number).findFirst().get().question;
        return question.toJson().toString();
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(path = "/question/start_new_quiz", method = RequestMethod.POST, produces = "application/json")
    public String startNewQuiz(@RequestParam("Number of questions") int number) {

        // prepare list of questions suitable for testing
        Iterable<Question> questions = questionRepository.findAll();
        List<Question> allQuestions = new ArrayList<>();
        questions.forEach(allQuestions::add);
        Collections.shuffle(allQuestions);
        allQuestions = allQuestions.stream().limit(number).collect(Collectors.toList());

        // save new instance of quiz into database
        Quiz quiz = new Quiz();
        quizRepository.save(quiz);

        quiz.updateQuestions(allQuestions);
        quizToQuestionRepository.saveAll(quiz.relations);

        //quiz = quizRepository.findByUUID(quiz.uuid);
        quiz = quizRepository.findById(quiz.id).get();


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
        Question question = relations.stream().filter(e->e.getOrder() == number).findFirst().get().question;

        JSONObject result = new JSONObject();
        result.put("result", question.checkAnswer(answer));
        return result.toString();
    }


}
