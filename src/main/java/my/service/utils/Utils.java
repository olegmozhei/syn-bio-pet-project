package my.service.utils;

import my.service.quiz.entities.OrdinaryQuestion;
import my.service.quiz.entities.Question;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.*;

public class Utils {

    public static void validateJson(JSONObject jsonToValidate, String[] requiredKeys){
        for (String key: requiredKeys) {
            if (!jsonToValidate.has(key)) throw new RuntimeException("Key '" + key + "' not found");
        }
    }

    /*
    The idea behind this method is to provide the most difficult questions first or the questions that were provided quite a long ago
    It's not interesting to solve the simplest questions or solve the same questions over and over again
     */
    public List<OrdinaryQuestion> shuffleQuestionsBasedOnComplexityAndLastCorrectAnswer(List<OrdinaryQuestion> questions){
        // transform complexity into order
        Random r = new Random();
        Collections.shuffle(questions);
        List<OrderQuestionPair> orderAndQuestions = new ArrayList<>(questions.stream().map(e -> {
            if (e.lastCorrectAnswer == null || LocalDateTime.now().minusDays(10).isAfter(e.lastCorrectAnswer)) return new OrderQuestionPair(100, e);
            int temp = r.nextInt(20) - 10;                  // get int from -10 to 10
            temp = temp + (e.complexity * 10);                    // transform complexity into complexity * 10 +- 10
            return new OrderQuestionPair(temp, e);                // save order for the Question
        }).toList());

        // sort questions by order in desc:
        orderAndQuestions.sort(Comparator.comparing(p -> -p.getLeft()));

        // transform list back
        return orderAndQuestions.stream().map(OrderQuestionPair::getRight).toList();
    }
}
