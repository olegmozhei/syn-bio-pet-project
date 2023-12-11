package my.service.database.implementations;

import jakarta.persistence.*;

@Entity
@Table(name="quizzes_to_questions", schema = "quiz")
public
class QuizToQuestions implements Comparable<QuizToQuestions>{

    @EmbeddedId
    public QuizToQuestionKey id;

    @ManyToOne
    @MapsId("quizId")
    @JoinColumn(name = "quiz_id")
    Quiz quiz;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "question_id")
    public Question question;

    // standard constructors, getters, and setters
    public QuizToQuestions(Quiz quiz, Question question, int order){
        this.question = question;
        this.quiz = quiz;
        this.id = new QuizToQuestionKey(quiz.id, question.id, order);
    }

    public QuizToQuestions(){}

    public int getOrder(){
        return id.questionsOrder;
    }

    @Override
    public int compareTo(QuizToQuestions o) {
        return this.id.questionsOrder - o.id.questionsOrder;
    }
}