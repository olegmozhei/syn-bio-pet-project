package my.service.quiz;

import my.service.quiz.entities.OrdinaryQuestion;
import my.service.quiz.entities.Question;
import my.service.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuizTests {
    Utils sut = new Utils();

    @Test
    public void questionsShufflingTest(){
        OrdinaryQuestion simpleQuestion = new OrdinaryQuestion();
        simpleQuestion.complexity = 0;
        simpleQuestion.lastCorrectAnswer = LocalDateTime.now();

        OrdinaryQuestion difficultQuestion = new OrdinaryQuestion();
        difficultQuestion.complexity = 10;
        difficultQuestion.lastCorrectAnswer = LocalDateTime.now();

        List<OrdinaryQuestion> allQuestions = new ArrayList<>() {{
            add(simpleQuestion);
            add(difficultQuestion);
        }};
        allQuestions = sut.shuffleQuestionsBasedOnComplexityAndLastCorrectAnswer(allQuestions);

        Assert.isTrue(allQuestions.get(0) == difficultQuestion, "First questions should be difficult ones");
    }

}
