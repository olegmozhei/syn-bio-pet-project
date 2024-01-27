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
    @Column(name = "quiz_id")
    public int quizId;

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

    public void updateQuestions(HashSet<QuizToQuestions> relations){
        this.relations = relations;
    }

    public List<Question> getQuestions(){
        return relations.stream().sorted().map(e -> {
            if (e.autogeneratedQuestion != null){
                return e.autogeneratedQuestion;
            }
            return e.question;
        }).toList();
    }
}
