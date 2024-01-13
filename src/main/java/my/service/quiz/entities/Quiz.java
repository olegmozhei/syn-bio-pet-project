package my.service.quiz.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="quizzes", schema = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int id;

    @OneToMany(mappedBy = "quiz")
    public Set<QuizToQuestions> relations;

    @Column
    public final String uuid;

    public Quiz(String uuid) {
        this.uuid = uuid;
    }

    public Quiz() {
        this.uuid = UUID.randomUUID().toString();
    }

    public void updateQuestions(List<OrdinaryQuestion> newQuestions){
        relations = new HashSet<>();
        for (int i = 1; i <= newQuestions.size(); i++) {
            relations.add(new QuizToQuestions(this, newQuestions.get(i - 1), i));
        }
    }

    public List<OrdinaryQuestion> getQuestions(){
        return relations.stream().sorted().map(e -> e.question).toList();
    }
}
