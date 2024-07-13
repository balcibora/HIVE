package databasetest1.demo;
/* 
 * Code for gpt api connection
 */
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import databasetest1.demo.jsonClasses.*;

import java.io.*;
import com.google.gson.*;

public class GptApi {
    private String ApiKey = "Put your api key here"; //there is an error here
    private String ApiHost = "https://api.openai.com/v1/chat/completions";
    private String ContentType = "application/json";
    private String model = "gpt-3.5-turbo";
    private String prompt = "";
    private static int accountNumber = 0; //needs to be taken from the text file.
    private ArrayList<String> keyList = new ArrayList<String>();

    //addition of a constructor seems appropriate?
    //this constructor is disgusting. The super part is absolute SHIT.
    public GptApi(String sPropmt) {
        this.prompt = sPropmt;
        try 
        {
            FileReader reader = new FileReader("server\\demo\\src\\main\\java\\databasetest1\\demo\\keys.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) 
            {
                keyList.add(line);
            }
            accountNumber = keyList.size();
            reader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("Error - file not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setKey(String key) {
        this.ApiKey = key;
    }

    public String getAnswer(int initialKeyNumber, int currentKeyNumber) throws URISyntaxException, IOException, InterruptedException{
        
        //make the body of request
        GptGsonReq gptGsonReq = new GptGsonReq(model, prompt);
        Gson gson = new Gson();
        String body = gson.toJson(gptGsonReq);

        //makes a hhtp request
        HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://api.openai.com/v1/chat/completions"))
        .header("Content-Type", ContentType)
        .header("Authorization", "Bearer " + this.ApiKey)
        .header("X-RapidAPI-Host", ApiHost)
        .method("POST", HttpRequest.BodyPublishers.ofString(body)) 
        .build(); 

        //tries to get a response
        try 
        {
            System.out.println("\n" + this.ApiKey + "\n");
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            int errorCode = response.statusCode();
            System.out.println("\n==HTML response code: " + errorCode + "==\n");
            if (errorCode == 200)
            {
                //we could do something here if required, but it is not for now.
            }
            else
            {
                String check = this.changeAPIKey(0, currentKeyNumber);
                if (check.equals("true"))
                {
                    return "==error too many requests, ran out of requests==\n"; //it should probbably not return anything and just break, but I will keep it like this for now.
                }
                else
                {
                    return check;
                }
            }
            return extractOutput(response);
        } 
        catch (Exception e) 
        {
            return "\n==Error, network connection issues (problem with sending a response to url)==\n";
        }   
    }

    //This method works as intended: it keeps changing the apikey from the keyList but the OpenAI API is smarter and doesn't allow multiple requests from the same IP adress I guess.
    //I am not sure how they prevent it, but looping through a list of api keys and sending multiple requests does not work, even though the code itself does.
    //Also, we are aware that this is not the best practice and we would pay for the api services but this was for demonstration purposes only. Since we are students...
    private String changeAPIKey(int initialKeyNumber, int currentKeyNumber) throws URISyntaxException, IOException, InterruptedException {
        String output;
        currentKeyNumber = (currentKeyNumber + 1) % accountNumber;
        this.ApiKey = keyList.get(currentKeyNumber);

        if (initialKeyNumber % accountNumber == currentKeyNumber % accountNumber + 1)
        {
            output = "true"; //basically stop here
        }
        else
        {
            output = getAnswer(initialKeyNumber, currentKeyNumber); //we can keeep going
        }
        return output;
    }

    public static String extractOutput(HttpResponse<String> res) {
        Gson gson = new Gson();
        GptGsonRes gptGsonRes = new GptGsonRes();
        gptGsonRes = gson.fromJson(res.body(), GptGsonRes.class);

        //System.out.println(res.body());

        return gptGsonRes.getChoices().get(0).getMessage().getContent();
     }
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        GptApi a = new GptApi("can you write a beautiful poem for me?");
        System.out.println(a.getAnswer(0,0));
    }
}
