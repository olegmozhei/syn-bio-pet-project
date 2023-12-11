package my.service.database.implementations;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizToQuestionRepository extends CrudRepository<QuizToQuestions, QuizToQuestionKey> {
}
