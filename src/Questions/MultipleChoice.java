package Questions;
import java.util.HashMap;
import java.util.Map;

public class MultipleChoice extends Question {
    private String statement;
    private Map<String,String> choices;
    private int correctIndex;
    public MultipleChoice(String aName, String anAuthor, String aLanguage, String aDifficulity) {
        super(aName, anAuthor, aLanguage, aDifficulity, "Multiple Choice");
        this.choices = new HashMap<>();
    }

    public String checkAnswer(String userAnswer) {
        if(userAnswer.equals("" + (char)('A' + correctIndex))) {
            return "Correct";
        } 
        else {
            return "Incorrect";
        }
    }

    public void setStatement(String aStatement) {
        statement = aStatement;
    }

    //editted this a bit.
    public void setChoices(String[] aChoices) {
        for (int i = 0; i < aChoices.length; i++) 
        {
            choices.put("" + i, aChoices[i]);
        }
    }

    public void setCorrectChoice(int c) {
        correctIndex = c;
    }

    public String getStatement() {
        return statement;
    }
  
    public Map<String,String> getChoices() {
        return this.choices;
    }
    
    public int getCorrectIndex() {
        return correctIndex;
    }

    public String getCorrectChoice() {
        return choices.get("" + correctIndex);
    }
}
