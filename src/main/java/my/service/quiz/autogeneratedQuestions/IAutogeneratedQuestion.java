package my.service.quiz.autogeneratedQuestions;

import my.service.quiz.entities.AutogeneratedQuestion;


/*
TODO: Probably the best way to store logic for questions autogeneration outside of controllers
 */
public interface IAutogeneratedQuestion {

    AutogeneratedQuestion generateQuestion(String topic);
}
