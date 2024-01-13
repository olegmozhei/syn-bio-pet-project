package my.service.quiz.entities;

import jakarta.persistence.*;

@Entity
@Table(name="quizzes_to_questions", schema = "quiz")
public
class QuizToQuestions implements Comparable<QuizToQuestions>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "relation_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "question_id")
    public OrdinaryQuestion question;

    @Column(name = "question_order")
    public int questionOrder;

    // standard constructors, getters, and setters
    public QuizToQuestions(Quiz quiz, OrdinaryQuestion question, int order){
        this.question = question;
        this.quiz = quiz;
        this.questionOrder = order;
    }

    public QuizToQuestions(){}

    public int getOrder(){
        return questionOrder;
    }

    @Override
    public int compareTo(QuizToQuestions o) {
        return this.questionOrder - o.questionOrder;
    }
}