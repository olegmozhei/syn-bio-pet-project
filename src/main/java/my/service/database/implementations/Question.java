package my.service.database.implementations;

import jakarta.persistence.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

@Entity
@Table(name="questions", schema = "quiz")
public class Question {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int id;

    /*
    1 - show answers and select correct
    2 - type in free answer case-sensitive
    3 - type in free answer case-insensitive
     */
    @Column
    private int type;

    @Column
    public String task;

    @Column
    private String answer1;

    @Column
    private String answer2;

    @Column
    private String answer3;

    @Column
    private String answer4;

    @Column
    private int complexity;

    public Question(){

    }

    public Question(int type, String task, String answer1, String answer2, String answer3, String answer4){
        this.type = type;
        this.task = task;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }

    public String checkAnswer(String answer){
        if (type == 1 && answer.equals(answer1)) return "correct";
        if (type == 2 && getListOfAnswers().stream().anyMatch(e-> e.equals(answer))) return "correct";
        if (type == 3 && getListOfAnswers().stream().anyMatch(e-> e.equalsIgnoreCase(answer))) return "correct";
        return "error. Correct answer is '" + answer1 + "'";
    }

    private ArrayList<String> getListOfAnswers(){
        ArrayList<String> correctAnswers = new ArrayList<>();
        if (answer1 != null && !answer1.equals("")) correctAnswers.add(answer1);
        if (answer2 != null && !answer2.equals("")) correctAnswers.add(answer2);
        if (answer3 != null && !answer3.equals("")) correctAnswers.add(answer3);
        if (answer4 != null && !answer4.equals("")) correctAnswers.add(answer4);
        return correctAnswers;
    }

    public JSONObject toJson(){
        JSONObject result = new JSONObject();
        result.put("task", this.task);
        JSONArray answersArray = new JSONArray();
        if (type == 1){
            ArrayList<String> answers = getListOfAnswers();
            Collections.shuffle(answers);
            for (String answer : answers) {
                answersArray.put(answer);
            }
            result.put("answers", answersArray);
        }
        return result;
    }
}
