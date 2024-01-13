package my.service.utils;

import my.service.quiz.entities.OrdinaryQuestion;
import my.service.quiz.entities.Question;
import org.apache.commons.lang3.tuple.Pair;

public class OrderQuestionPair extends Pair<Integer, OrdinaryQuestion> {
    final int order;
    final OrdinaryQuestion question;

    public OrderQuestionPair(int order, OrdinaryQuestion question) {
        this.order = order;
        this.question = question;
    }

    @Override
    public Integer getLeft() {
        return order;
    }

    @Override
    public OrdinaryQuestion getRight() {
        return question;
    }

    @Override
    public OrdinaryQuestion setValue(OrdinaryQuestion value) {
        throw new RuntimeException("can't change final value");
    }
}
