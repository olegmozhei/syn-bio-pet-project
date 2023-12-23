package my.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import my.service.database.implementations.IQuestionRepository;
import my.service.database.implementations.Question;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;


@RestController
public class QuestionController {

    private final IQuestionRepository questionRepository;

    public QuestionController(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @Operation(description = """
             1 - show answers and select correct;\n
             2 - type in free answer case-sensitive;\n
             3 - type in free answer case-insensitive;\n
             4 - type in formula (similar to type 2);
            """)
    @RequestMapping(path = "/question", method = RequestMethod.POST, produces = "application/json")
    public String addNewQuestion(@RequestParam("Question type") int type,
                                 @RequestParam("Task") String task,
                                 @RequestParam("Answer1") String answer1,
                                 @RequestParam(value = "Answer2", required = false) String answer2,
                                 @RequestParam(value = "Answer3", required = false) String answer3,
                                 @RequestParam(value = "Answer4", required = false) String answer4,
                                 @RequestParam(value = "Topic", required = false) String topic) {

        Question q = new Question(type, task, answer1, answer2, answer3,answer4, topic);

        questionRepository.save(q);
        JSONObject result = new JSONObject();
        result.put("result", "done");
        return result.toString();
    }
}
