package databasetest1.demo;
/*
 * it works. it may or may not work great 
 * but you know what? it f*****g works.
 */
 import java.io.IOException;
 import java.net.URI;
 import java.net.URISyntaxException;
 import java.net.http.HttpClient;
 import java.net.http.HttpRequest;
 import java.net.http.HttpResponse;
 import com.google.gson.*;

import databasetest1.demo.jsonClasses.CompilerGsonReq;
import databasetest1.demo.jsonClasses.CompilerGsonRes;
 
 public class CompilerApi extends Api {
     

    private static String apiKey = "Put your api key here";

 
    CompilerApi(){
        //bandaid solution, will be fixed
        super(apiKey, "online-code-compiler.p.rapidapi.com", "application/json");
    }
 
    public String getAnswer(String code, String language, String input) throws URISyntaxException, IOException, InterruptedException, Exception{
        
        //create the body of the request
        CompilerGsonReq compilerGsonRes = new CompilerGsonReq(language, "latest", code, input);
        Gson gson = new Gson();
        String body = gson.toJson(compilerGsonRes);

        //makes a hhtp request
        HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://online-code-compiler.p.rapidapi.com/v1/"))
        .header("content-type", ContentType)
        .header("X-RapidAPI-Key", ApiKey)
        .header("X-RapidAPI-Host", ApiHost)
        .method("POST", HttpRequest.BodyPublishers.ofString(body))
        .build();

        //gets a response
        try{
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            int errorCode = response.statusCode();
            System.out.println("\n==HTML response code: " + errorCode + "==\n");
            if(errorCode == 200){
                System.out.println("yay we did it!!!");
            }
            return extractOutput(response);
        }catch(Exception e){
            return "\n==Error, network connection issues (problem with sending a response to url)==\n";
        }finally{}

    }
 
    private static String extractOutput(HttpResponse<String> res){
        Gson gson = new Gson();
        CompilerGsonRes compilerGsonRes = new CompilerGsonRes();
        compilerGsonRes = gson.fromJson(res.body(), CompilerGsonRes.class);
        System.out.println(res.body());
        System.out.println("************");
        return compilerGsonRes.getOutput();
    }
 
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException,Exception {
        CompilerApi api = new CompilerApi();
        System.out.println(api.getAnswer("import java.util.Scanner;\npublic class myClass{\npublic static void main(String[] args) {\nScanner in = new Scanner(System.in);\nint twoTimes = in.nextInt();\nSystem.out.println(twoTimes * 2);\nin.close();\n}\n}", "java", "3"));
    }
}