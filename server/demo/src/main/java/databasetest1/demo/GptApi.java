package databasetest1.demo;
/* In theory, this code should work (theory correct, code works)
 * I adapted the CompilerApi class to work with GPT
 * Some necessary additions were:
 * - GPT model
 * - Change in POST content
 * - Overall changes in json formatting and letter capitalizations
 * 
 * Another required change is:
 * - addition of a parameter to change the apikey (this is NOT optional, it is required in order to switch between accounts)
 * 
 * Hopefully it works :D (It does.)
 * We need to hide the api keys somehow :p
 * 
 * PS. - The final part of the code is:
 * 1. The ugliest thing I have ever seen
 * 2. Something so disgusting that I would also do it
 * 3. Something that I also did in order for the code to work
 * 
 * So, it works. But not as I intended... IT WORKS THOUGH. By brute force.
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

        //the super does not set the apikey of this object for some reason.
        //so when the apikey is left blank and when you try to take it only as an input, it does not get updated and is left blank
        //so there is an error since THERE IS NO APIKEY PRESENT
        //I DON'T KNOW WHY, AND I HATE ABSTRACT CLASSES
    }

    //a setter also seems appropriate to change the key when needed, perhaps an accountselector class should also be implemented?
    public void setKey(String key) {
        this.ApiKey = key;
    }

    //This part of the code can be carried to the abstract class maybe? But we would still have to override it I guess, since the content is different.
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
        .method("POST", HttpRequest.BodyPublishers.ofString(body)) //This part is no longer wtf
        .build(); //the order is different from my original code, that might cause some issues. Just noting it down here just in case.

        //tries to get a response
        try 
        {
            System.out.println("\n" + this.ApiKey + "\n");
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            int errorCode = response.statusCode();
            System.out.println("\n==HTML response code: " + errorCode + "==\n");
            if (errorCode == 200)
            {
                //we could do something here
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

    private String changeAPIKey(int initialKeyNumber, int currentKeyNumber) throws URISyntaxException, IOException, InterruptedException { //I don't enjoy the throws but they are required I guess...
        String output;
        currentKeyNumber = (currentKeyNumber + 1) % accountNumber;
        this.ApiKey = keyList.get(currentKeyNumber);

        if (initialKeyNumber % accountNumber == currentKeyNumber % accountNumber + 1)
        {
            output = "true"; //terminate everything, we failed.
        }
        else
        {
            output = getAnswer(initialKeyNumber, currentKeyNumber); //we can keeep going bro, keep looking 
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