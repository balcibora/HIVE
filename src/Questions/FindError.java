package Questions;
public class FindError extends Question {
    private String statement;
    private String answer;

    public FindError(String aName, String anAuthor, String aLanguage, String aDifficulity) {
        super(aName, anAuthor, aLanguage, aDifficulity, "Find Error");
    }

    public String checkAnswer(String userAnswer) {
        if(answer.equals(userAnswer)) {
            return "Correct";
        } 
        else {
            return "Incorrect";
        }
    }

    public void setStatement(String aStatement) {
        statement = aStatement;
    }

    public void setAnswer(String anAnswer) {
        answer = anAnswer;
    }

    public String getStatement() {
        return statement;
    }
  
    public String getAnswer() {
        return answer;
    }
}
