package my.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import my.service.database.implementations.IQuestionRepository;
import my.service.database.implementations.Question;
import my.service.database.implementations.QuizToQuestions;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import static my.service.utils.Utils.validateJson;

@RestController
public class QuestionController {

    private final IQuestionRepository questionRepository;

    public QuestionController(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Operation(summary = "Add new question. 1 - show answers and select correct; 2 - type in free answer case-sensitive; 3 - type in free answer case-insensitive")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(path = "/question", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String addNewQuestion(@RequestBody String input) {
        JSONObject inputJson = new JSONObject(input);
        validateJson(inputJson, new String[] {"type", "task", "answer1"});

        Question q = new Question(inputJson.getInt("type"),
                inputJson.getString("task"),
                inputJson.getString("answer1"),
                (inputJson.has("answer2")?inputJson.getString("answer2") : null),
                (inputJson.has("answer3")?inputJson.getString("answer3") : null),
                (inputJson.has("answer4")?inputJson.getString("answer4") : null));


        questionRepository.save(q);
        JSONObject result = new JSONObject();
        result.put("result", "done");
        return result.toString();
    }
}
