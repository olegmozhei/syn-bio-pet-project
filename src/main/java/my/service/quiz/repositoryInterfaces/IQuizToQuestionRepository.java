package my.service.quiz.repositoryInterfaces;

import my.service.quiz.entities.QuizToQuestions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizToQuestionRepository extends CrudRepository<QuizToQuestions, Integer> {
}
