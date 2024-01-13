package my.service.quiz.entities;

import jakarta.persistence.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

@Table(name="questions", schema = "quiz")
@Entity
public class OrdinaryQuestion extends Question {

    public OrdinaryQuestion(int type, String task, String answer1, String answer2, String answer3, String answer4, String topic, String image){
        super(type, task, answer1, answer2, answer3, answer4, topic, image);
    }

    public OrdinaryQuestion(){
        super();
    }
}
