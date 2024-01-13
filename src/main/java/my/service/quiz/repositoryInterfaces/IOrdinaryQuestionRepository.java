package my.service.quiz.repositoryInterfaces;

import my.service.quiz.entities.OrdinaryQuestion;
import my.service.quiz.entities.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdinaryQuestionRepository extends CrudRepository<OrdinaryQuestion, Integer> {

    @Query(value = "select * from quiz.questions q order by random() limit 1",
            nativeQuery = true)
    Question getRandonQuestion();

    List<OrdinaryQuestion> findByTopic(String topic);
}
