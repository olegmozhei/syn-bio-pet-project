package my.service.database.implementations;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizRepository extends CrudRepository<Quiz, Integer> {
    @Query(value = "select * from quiz.quizzes q where q.uuid = ?1",
            nativeQuery = true)
    Quiz findByUUID(String uuid);
}
