package my.service.database.implementations;

import my.service.database.implementations.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionRepository extends CrudRepository<Question, Integer> {

    @Query(value = "select * from quiz.questions q order by random() limit 1",
            nativeQuery = true)
    Question getRandonQuestion();
}
