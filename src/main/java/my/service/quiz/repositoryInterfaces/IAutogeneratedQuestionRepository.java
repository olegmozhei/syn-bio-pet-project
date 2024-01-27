package my.service.quiz.repositoryInterfaces;

import my.service.quiz.entities.AutogeneratedQuestion;
import my.service.quiz.entities.OrdinaryQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAutogeneratedQuestionRepository extends CrudRepository<AutogeneratedQuestion, Integer> {

}
