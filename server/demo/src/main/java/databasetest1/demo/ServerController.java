package databasetest1.demo;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class ServerController 
{
    public RestService service;
    
    public ServerController (RestService aService)
    {
        this.service = aService;
    }

    @PostMapping("/createUser")
    public String createUser (@RequestBody User aUser) throws InterruptedException, ExecutionException
    {
        return service.createUser (aUser);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser (@RequestParam String userId) throws InterruptedException, ExecutionException
    {
        return service.deleteUser (userId);
    }

    @GetMapping("/getAllUserIDs")
    public String[] getAllUserIDs() throws InterruptedException, ExecutionException
    {
        return service.getAllUserIDs();
    }

    @GetMapping("/getAllAdminIDs")
    public String[] getAllAdminIDs() throws InterruptedException, ExecutionException
    {
        return service.getAllAdminIDs();
    }
    

    @GetMapping("/getTotalAccountCount")
    public int getTotalAccountCount() throws InterruptedException, ExecutionException
    {
        return service.getTotalAccountCount();
    }

    @GetMapping("/getTotalQuestionCount")
    public int getTotalQuestionCount() throws InterruptedException, ExecutionException
    {
        return service.getTotalQuestionCount();
    }

    @GetMapping("/getUsername")
    public String getUsername (@RequestParam String accountId) throws InterruptedException, ExecutionException
    {
        return service.getUsername (accountId);
    }

    @GetMapping("/getEmail")
    public String getEmail (@RequestParam String accountId) throws InterruptedException, ExecutionException
    {
        return service.getEmail (accountId);
    }

    @GetMapping("/getStreak")
    public int getStreak (@RequestParam String userId) throws InterruptedException, ExecutionException
    {
        return service.getStreak (userId);
    }

    @GetMapping("/getHighestStreak")
    public int getHighestStreak (@RequestParam String userId) throws InterruptedException, ExecutionException
    {
        return service.getHighestStreak (userId);
    }

    @GetMapping("/getLastTimeSolved")
    public String getLastTimeSolved (@RequestParam String userId) throws InterruptedException, ExecutionException
    {
        return service.getLastTimeSolved (userId);
    }

    @PutMapping("/setLastTimeSolved")
    public String setLastTimeSolved (@RequestParam String userId, @RequestParam String date) throws InterruptedException, ExecutionException
    {
        return service.setLastTimeSolved (userId, date);
    }
    
    @GetMapping("/getDateOfCreation")
    public String getDateOfCreation (@RequestParam String userId) throws InterruptedException, ExecutionException
    {
        return service.getDateOfCreation (userId);
    }

    @GetMapping("/usernameAlreadyExists")
    public boolean usernameAlreadyExists (@RequestParam String username) throws InterruptedException, ExecutionException
    {
        return service.usernameAlreadyExists (username);
    }

    @GetMapping("/login")
    public String login (@RequestParam String username, @RequestParam String password) throws InterruptedException, ExecutionException
    {
        return service.login (username, password);
    }

    @PutMapping("/changePassword")
    public boolean changePassword (@RequestParam String userId, @RequestParam String newPassword, @RequestParam String oldPassword) throws InterruptedException, ExecutionException
    {
        return service.changePassword (userId, newPassword, oldPassword);
    }

    @PutMapping("/resetStreak")
    public String resetStreak (@RequestParam String userId) throws InterruptedException, ExecutionException
    {
        return service.resetStreak (userId);
    }

    @PutMapping("/increaseStreak")
    public String increaseStreak (@RequestParam String userId) throws InterruptedException, ExecutionException
    {
        return service.increaseStreak (userId);
    }

    @PutMapping("/increaseHighestStreak")
    public String increaseHighestStreak (@RequestParam String userId) throws InterruptedException, ExecutionException
    {
        return service.increaseHighestStreak (userId);
    }

    @GetMapping("/getFriendList")
    public Map<String, Boolean> getFriendList (@RequestParam String userId) throws InterruptedException, ExecutionException
    {
        return service.getFriendList (userId);
    }

    @PutMapping("/addFriend")
    public boolean addFriend (@RequestParam String userId, @RequestParam String friendId) throws InterruptedException, ExecutionException
    {
        return service.addFriend (userId, friendId);
    }

    @PutMapping("/removeFriend")
    public boolean removeFriend (@RequestParam String userId, @RequestParam String friendId) throws InterruptedException, ExecutionException
    {
        return service.removeFriend (userId, friendId);
    }

    @GetMapping("/getSolvedQuestions")
    public Map<String, String> getSolvedQuestions (@RequestParam String userId) throws InterruptedException, ExecutionException
    {
        return service.getSolvedQuestions (userId);
    }

    @PutMapping("/addToSolvedQuestions")
    public String addToSolvedQuestions (@RequestParam String userId, @RequestParam String questionId) throws InterruptedException, ExecutionException
    {
        return service.addToSolvedQuestions (userId, questionId);
    }

    @GetMapping("/getNumberOfWrittenQuestions")
    public int getNumberOfWrittenQuestions (@RequestParam String adminId) throws InterruptedException, ExecutionException
    {
        return service.getNumberOfWrittenQuestions (adminId);
    }

    @GetMapping("/getWrittenList")
    public Map<String, String> getWrittenList (@RequestParam String adminId) throws InterruptedException, ExecutionException
    {
        return service.getWrittenList (adminId);
    }

    @PostMapping("/createOrEditFillTheBlankQuestion")
    public String createOrEditFTBQuestion (@RequestBody FillTheBlankQuestion anFTBQuestion) throws InterruptedException, ExecutionException
    {
        return service.createOrEditFTBQuestion (anFTBQuestion);
    }

    @PostMapping("/createOrEditFindErrorQuestion")
    public String createOrEditFEQuestion (@RequestBody FindErrorQuestion anFEQuestion) throws InterruptedException, ExecutionException
    {
        return service.createOrEditFEQuestion (anFEQuestion);
    }

    @PostMapping("/createOrEditWriteCodeQuestion")
    public String createOrEditWCQuestion (@RequestBody WriteCodeQuestion aWCQuestion) throws InterruptedException, ExecutionException
    {
        return service.createOrEditWCQuestion (aWCQuestion);
    }

    @PostMapping("/createOrEditTraceCodeQuestion")
    public String createOrEditTCQuestion (@RequestBody TraceCodeQuestion aTCQuestion) throws InterruptedException, ExecutionException
    {
        return service.createOrEditTCQuestion (aTCQuestion);
    }

    @PostMapping("/createOrEditMultipleChoiceQuestion")
    public String createOrEditMCQuestion (@RequestBody MultipleChoiceQuestion anMCQuestion) throws InterruptedException, ExecutionException
    {
        return service.createOrEditMCQuestion (anMCQuestion);
    }

    @GetMapping("/getAllQuestionIDs")
    public String[] getAllQuestionIDs() throws InterruptedException, ExecutionException
    {
        return service.getAllQuestionIDs();
    }

    @GetMapping("/getQuestionTitle")
    public String getQuestionTitle (@RequestParam String questionId) throws InterruptedException, ExecutionException
    {
        return service.getQuestionTitle (questionId);
    }

    @GetMapping("/getFillTheBlankQuestion")
    public FillTheBlankQuestion getFTBQuestion (@RequestParam String questionId) throws InterruptedException, ExecutionException
    {
        return service.getFTBQuestion (questionId);
    }
    
    @GetMapping("/getFindErrorQuestion")
    public FindErrorQuestion getFEQuestion (@RequestParam String questionId) throws InterruptedException, ExecutionException
    {
        return service.getFEQuestion (questionId);
    }

    @GetMapping("/getWriteCodeQuestion")
    public WriteCodeQuestion getWCQuestion (@RequestParam String questionId) throws InterruptedException, ExecutionException
    {
        return service.getWCQuestion (questionId);
    }

    @GetMapping("/getTraceCodeQuestion")
    public TraceCodeQuestion getTCQuestion (@RequestParam String questionId) throws InterruptedException, ExecutionException
    {
        return service.getTCQuestion (questionId);
    }

    @GetMapping("/getMultipleChoiceQuestion")
    public MultipleChoiceQuestion getMCQuestion (@RequestParam String questionId) throws InterruptedException, ExecutionException
    {
        return service.getMCQuestion (questionId);
    }

    @DeleteMapping("/deleteQuestion")
    public String deleteQuestion (@RequestParam String questionId) throws InterruptedException, ExecutionException
    {
        return service.deleteQuestion (questionId);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testGetEndpoint() 
    {
        return ResponseEntity.ok ("testGetEndpoint is working!");
    }

    @GetMapping("/getChatGPTResponse")
    public String getChatGPTResponse (@RequestParam String prompt) throws URISyntaxException, IOException, InterruptedException
    {
        GptApi a = new GptApi(prompt);
        return a.getAnswer(0, 0);
    }

    @GetMapping("/getCompilerResult")
    public String getCompilerResult (@RequestParam String code, @RequestParam String language, @RequestParam String input) throws URISyntaxException, IOException, InterruptedException, Exception
    {
        //"python3"
        CompilerApi api = new CompilerApi();
        return api.getAnswer(code, language, input);
    }
}
