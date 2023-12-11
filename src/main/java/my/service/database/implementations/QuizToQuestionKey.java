package my.service.database.implementations;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
class QuizToQuestionKey implements Serializable {

    @Column(name = "quiz_id")
    int quizId;

    @Column(name = "question_id")
    int questionId;

    @Column(name = "question_order")
    public int questionsOrder;

    public QuizToQuestionKey(int quizId, int questionId, int questionOrder){
        this.quizId = quizId;
        this.questionId = questionId;
        this.questionsOrder = questionOrder;
    }

    public QuizToQuestionKey(){}
}
