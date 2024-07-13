package Questions;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import Main.Main;

public class CodeWriting extends Question{

    private String statement;
    private String answer;
    private Map<String, String> testcases;

    public CodeWriting(String aName, String anAuthor, String aLanguage, String aDifficulity) {
        super(aName, anAuthor, aLanguage, aDifficulity, "Code Writing");
        testcases = new HashMap<>();
    }

    public void addTestcase(String input, String output) {
        testcases.put(input, output);
    }

    public String checkAnswer(String userAnswer) throws IOException, URISyntaxException{
        String output = "", input;
        if(testcases.keySet().isEmpty()){
            output = "blank"; // bandaid fix
        }else{
            for(String testCase: testcases.keySet()){
                input = "\n" + Main.server.getCompilerResult(userAnswer, this.language, testCase);
                System.out.println(input + "---" + testcases.get(testCase));
                System.out.println("------------");
                if(input.equals(testcases.get(testCase))){
                    output += "correct,";
                }else{
                    output += "incorrect,";
                }
            }
        }
        output = output.substring(0, output.length() - 1); //remove the last (",") index
        return output;
    }

    public String getGptResponse(String userAnswer, boolean isCorrect) throws IOException, URISyntaxException{
        String input = "this student answered ";
        input += this.getStatement() + " question";
        if(isCorrect){
            input += " correctly.";
        }else{
            input += " incorrectly.";
        }
        input += "here is the code\n" + userAnswer + "\n";
        input += "can you evaluate the answer? What could the user done better? did the user do a good job?";
        System.out.println(input);
        System.out.println();
        String output = Main.server.getChatGPTResponse(input);
        System.out.println(output);
        return output;
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

    public Map<String, String> getTestCases(){
        return testcases;
    }

    public String writeTestCases() {
        String output = ""; //temp
        for (String input : this.testcases.keySet()) 
        {
            String currentCaseInput = input;
            String currentCaseOutput = this.testcases.get(input);
            output += currentCaseInput + ":";
            output += currentCaseOutput + ",\n";
        }
        return output;
    }
}
