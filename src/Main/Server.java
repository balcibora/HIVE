package Main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import Accounts.User;
import Questions.*;

/**
 * This is the class where we will store the methods for the server
 * These methods will make http requests easier by creating designated methods
 */

public class Server {
    private static HttpURLConnection connection = null;
    private static Gson gson = new Gson();
    private static String serverURL = "http://localhost:8080";

    /**
     * Adds new user to database
     * @param u
     * @return string stating whether the proccess was successful
     */
    public String addUser(User u) throws IOException, URISyntaxException {
        String url = serverURL + "/createUser";
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        
        // Write JSON data to request body
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = u.toString().getBytes("utf-8");
            outputStream.write(input, 0, input.length);           
        }

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        if (connection != null) {
            connection.disconnect();
        }
        return response.toString();
    }

    /**
     * Deletes an existing user from the database
     * @param u
     * @return string stating whether the proccess was successful
     */
    public String deleteUser(String userId) throws IOException, URISyntaxException
    {
        String url = serverURL + "/deleteUser?userId=" + URLEncoder.encode(userId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("DELETE");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    /**
     * This method returns the IDs of all the existing users
     * @return String array of all user IDs
     */
    public String[] getAllUserIDs() throws IOException, URISyntaxException
    {
        String url = serverURL + "/getAllUserIDs";
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        String output = response.toString();
        String[] ret = output.substring(1, output.length()-1).replaceAll("\"", "").split(",");
        return ret;
    }

    /**
     * This method returns the IDs of all the existing admins
     * @return String array of all admin IDs
     */
    public String[] getAllAdminIDs() throws IOException, URISyntaxException
    {
        String url = serverURL + "/getAllAdminIDs";
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        String output = response.toString();
        String[] ret = output.substring(1, output.length()-1).replaceAll("\"", "").split(",");
        return ret;
    }

    /**
     * Gives the total number of accounts
     * @return total number of accounts as int
     */
    public int getTotalAccountCount() throws IOException, URISyntaxException
    {
        String url = serverURL + "/getTotalAccountCount";
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        if (connection != null) {
            connection.disconnect();
        }

        return Integer.parseInt(response.toString());
    }

    /**
     * Gives the total number of questions
     * @return total number of questions as int
     */
    public int getTotalQuestionCount() throws IOException, URISyntaxException
    {
        String url = serverURL + "/getTotalQuestionCount";
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        if (connection != null) {
            connection.disconnect();
        }

        return Integer.parseInt(response.toString());
    }
    /**
     * Checks if the account with the given id is an admin
     * @param accountId
     * @return true if admin
     * @return false if not admin
     */
    public boolean isAdmin (String accountId)
    {
        return (accountId.charAt(0) == 'A');
    }

    /**
     * Gives the username of the account with the given id
     * @param accountId
     * @return username
     */
    public String getUsername (String accountId) throws IOException, URISyntaxException
    {
        String url = serverURL + "/getUsername?accountId=" + URLEncoder.encode(accountId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    /**
     * Gives the email of the account with the given id
     * @param accountId
     * @return email
     */
    public String getEmail (String accountId) throws URISyntaxException, IOException
    {
        String url = serverURL + "/getEmail?accountId=" + URLEncoder.encode(accountId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    /**
     * Gives the streak of the account with the given id
     * @param userId
     * @return streak
     */
    public int getStreak (String userId) throws IOException, URISyntaxException
    {
        String url = serverURL + "/getStreak?userId=" + URLEncoder.encode(userId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return Integer.parseInt(response.toString());
    }

    /**
     * Gives the highest streak of the account with the given id
     * @param userId
     * @return highest streak
     */
    public int getHighestStreak (String userId) throws IOException, URISyntaxException
    {
        String url = serverURL + "/getHighestStreak?userId=" + URLEncoder.encode(userId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return Integer.parseInt(response.toString());
    }

    /**
     * @param userId
     * @return The date of the last time the user with the given id solved a question
     */
    public String getLastTimeSolved (String userId) throws IOException, URISyntaxException
    {
        String url = serverURL + "/getLastTimeSolved?userId=" + URLEncoder.encode(userId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    /**
     * Sets the date of the last time the user with the given id solved a question
     * @param userId
     * @param date
     * @return String stating whether the process was successful
     */
    public String setLastTimeSolved (String userId, String date) throws URISyntaxException, IOException
    {
        String url = serverURL + "/setLastTimeSolved?userId=" + URLEncoder.encode(userId, "UTF-8") + "&date=" + URLEncoder.encode(date, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("PUT");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    /**
     * @param accountId
     * @return The date of creation of the account
     */
    public String getDateOfCreation (String accountId) throws URISyntaxException, IOException
    {
        String url = serverURL + "/getDateOfCreation?userId=" + URLEncoder.encode(accountId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    /**
     * Checks if an account with the given username already exists or not
     * @param username
     * @return true if there is already an account with this username
     * @return false if there isnt an account with this username
     */
    public boolean usernameAlreadyExists (String username) throws URISyntaxException, IOException
    {
        String url = serverURL + "/usernameAlreadyExists?username=" + URLEncoder.encode(username, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return Boolean.parseBoolean(response.toString());
    }

    /**
     * This method logs in
     * @param username
     * @param password
     * @return ID of user
     */
    public String login(String username, String password) throws IOException, URISyntaxException {
        String url = serverURL + "/login?username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        
        if (connection != null) {
            connection.disconnect();
        }
        
        return response.toString();
    }

    /**
     * Checks if the password can be changed & changes it if its possible
     * @param userId
     * @param newPassword
     * @return true if password was changed successfully
     * @return false if the given password is the current password
     */
    public boolean changePassword (String userId, String newPassword, String oldPassword)throws URISyntaxException, IOException
    {
        String url = serverURL + "/changePassword?userId=" + URLEncoder.encode(userId, "UTF-8") + "&newPassword=" + URLEncoder.encode(newPassword, "UTF-8") + "&oldPassword=" + URLEncoder.encode(oldPassword, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("PUT");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return Boolean.parseBoolean(response.toString());
    }

    /**
     * This method resets streak
     * @param id
     * @return string stating whether the proccess was successful
     */
    public String resetStreak(String id) throws IOException, URISyntaxException
    {
        String url = serverURL + "/resetStreak?userId=" + URLEncoder.encode(id, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("PUT");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    /**
     * This method increases the streak by 1
     * @param id
     * @return string stating whether the proccess was successful
     */
    public String increaseStreak(String id) throws IOException, URISyntaxException 
    {
        String url = serverURL + "/increaseStreak?userId=" + URLEncoder.encode(id, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("PUT");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    /**
     * This method gets the friend list of the user with the given id.
     * @param userId
     * @return friendList map
     */
    public Map<String, Boolean> getFriendList (String userId) throws URISyntaxException, IOException
    {
        String url = serverURL + "/getFriendList?userId=" + URLEncoder.encode(userId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        Type type = new com.google.gson.reflect.TypeToken<HashMap<String, Boolean>>() {}.getType();
        return gson.fromJson(response.toString(), type);
    }

    /**
     * This method adds a friend
     * @param userID
     * @param friendID
     * @return string stating whether the proccess was successful
     */
    public String addFriend(String userID, String friendID) throws IOException, URISyntaxException
    {
        String url = serverURL + "/addFriend?userId=" + URLEncoder.encode(userID, "UTF-8") + "&friendId=" + URLEncoder.encode(friendID, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("PUT");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    /**
     * This method removes a friend
     * @param userID
     * @param friendID
     * @return string stating whether the proccess was successful
     */
    public String removeFriend(String userID, String friendID) throws IOException, URISyntaxException
    {
        String url = serverURL + "/removeFriend?userId=" + URLEncoder.encode(userID, "UTF-8") + "&friendId=" + URLEncoder.encode(friendID, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("PUT");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    /**
     * This method gets the list of solved questions' IDs of the user with the given id.
     * @param userId
     * @return solvedQuestions map
     */
    public Map<String, String> getSolvedQuestions (String userId) throws URISyntaxException, IOException
    {
        String url = serverURL + "/getSolvedQuestions?userId=" + URLEncoder.encode(userId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        Type type = new com.google.gson.reflect.TypeToken<HashMap<String, String>>() {}.getType();
        return gson.fromJson(response.toString(), type);
    }

    /**
     * This method adds the solved question's id to the user's solvedQuestion list
     * @param userId
     * @param questionId
     * @return string stating whether the process was successful
     */
    public String solved (String userId, String questionId) throws IOException, URISyntaxException
    {
        String url = serverURL + "/addToSolvedQuestions?userId=" + URLEncoder.encode(userId, "UTF-8") + "&questionId=" + URLEncoder.encode(questionId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("PUT");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    /**
     * This method gets the number of questions written by the admin with the given id
     * @param adminId
     * @return number of written questions
     */
    public int getNumberOfWrittenQuestions (String adminId) throws IOException, URISyntaxException
    {
        String url = serverURL + "/getNumberOfWrittenQuestions?adminId=" + URLEncoder.encode(adminId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }

        return Integer.parseInt(response.toString());
    }

    /**
     * This method gets the list of written questions' IDs of the admin with the given id.
     * @param adminId
     * @return writtenList map
     */
    public Map<String, String> getWrittenList (String adminId) throws URISyntaxException, IOException
    {
        String url = serverURL + "/getWrittenList?adminId=" + URLEncoder.encode(adminId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        Type type = new com.google.gson.reflect.TypeToken<HashMap<String, String>>() {}.getType();
        return gson.fromJson(response.toString(), type);
    }

    /**
     * Adds new FillBlank question to database
     * @param FillBlank question
     * @return string stating whether the proccess was successful
     */
    public String addFillBlankQuestion (FillBlank anFTBQuestion) throws IOException, URISyntaxException
    {
        String url = serverURL + "/createOrEditFillTheBlankQuestion";
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        
        // Write JSON data to request body
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = gson.toJson(anFTBQuestion).getBytes("utf-8");
            outputStream.write(input, 0, input.length);           
        }

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        if (connection != null) {
            connection.disconnect();
        }
        return response.toString();
    }

    /**
     * Adds new FindError question to database
     * @param FindError question
     * @return string stating whether the proccess was successful
     */
    public String addFindErrorQuestion (FindError anFEQuestion) throws IOException, URISyntaxException
    {
        String url = serverURL + "/createOrEditFindErrorQuestion";
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        
        // Write JSON data to request body
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = gson.toJson(anFEQuestion).getBytes("utf-8");
            outputStream.write(input, 0, input.length);           
        }

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        if (connection != null) {
            connection.disconnect();
        }
        return response.toString();
    }

    /**
     * Adds new CodeWriting question to database
     * @param CodeWriting question
     * @return string stating whether the proccess was successful
     */
    public String addCodeWritingQuestion (CodeWriting aWCQuestion) throws IOException, URISyntaxException
    {
        String url = serverURL + "/createOrEditWriteCodeQuestion";
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        
        // Write JSON data to request body
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = gson.toJson(aWCQuestion).getBytes("utf-8");
            outputStream.write(input, 0, input.length);           
        }

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        if (connection != null) {
            connection.disconnect();
        }
        return response.toString();
    }

    /**
     * Adds new TraceCode question to database
     * @param TraceCode question
     * @return string stating whether the proccess was successful
     */
    public String addTraceCodeQuestion (TraceCode aTCQuestion) throws IOException, URISyntaxException
    {
        String url = serverURL + "/createOrEditTraceCodeQuestion";
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        
        // Write JSON data to request body
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = gson.toJson(aTCQuestion).getBytes("utf-8");
            outputStream.write(input, 0, input.length);           
        }

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        if (connection != null) {
            connection.disconnect();
        }
        return response.toString();
    }

    /**
     * Adds new MultipleChoice question to database
     * @param MultipleChoice question
     * @return string stating whether the proccess was successful
     */
    public String addMultipleChoiceQuestion (MultipleChoice anMCQuestion) throws IOException, URISyntaxException
    {
        String url = serverURL + "/createOrEditMultipleChoiceQuestion";
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        
        // Write JSON data to request body
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = gson.toJson(anMCQuestion).getBytes("utf-8");
            outputStream.write(input, 0, input.length);           
        }

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        if (connection != null) {
            connection.disconnect();
        }
        return response.toString();
    }

    /**
     * @return String[] all question IDs
     */
    public String[] getAllQuestionIDs() throws IOException, URISyntaxException
    {
        String url = serverURL + "/getAllQuestionIDs";
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        String output = response.toString();
        String[] ret = output.substring(1, output.length()-1).replaceAll("\"", "").split(",");
        return ret;
    }

    /**
     * This method recieves a fill the blank type question
     * @param questionId
     * @return  string stating whether the proccess was successful
     */
    public FillBlank getFTBQuestion (String questionId) throws IOException, URISyntaxException
    {
        String url = serverURL + "/getFillTheBlankQuestion?questionId=" + URLEncoder.encode(questionId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        return gson.fromJson(response.toString(), FillBlank.class);
    }

    /**
     * This method recieves a find error type question
     * @param questionId
     * @return  string stating whether the proccess was successful
     */
    public FindError getFEQuestion (String questionId) throws IOException, URISyntaxException
    {
        String url = serverURL + "/getFindErrorQuestion?questionId=" + URLEncoder.encode(questionId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        return gson.fromJson(response.toString(), FindError.class);
    }

    /**
     * This method recieves a write the code type question
     * @param questionId
     * @return  string stating whether the proccess was successful
     */
    public CodeWriting getWCQuestion (String questionId) throws IOException, URISyntaxException
    {
        String url = serverURL + "/getWriteCodeQuestion?questionId=" + URLEncoder.encode(questionId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        return gson.fromJson(response.toString(), CodeWriting.class);
    }

    /**
     * This method recieves a trace the code type question
     * @param questionId
     * @return  string stating whether the proccess was successful
     */
    public TraceCode getTCQuestion (String questionId) throws IOException, URISyntaxException
    {
        String url = serverURL + "/getTraceCodeQuestion?questionId=" + URLEncoder.encode(questionId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        return gson.fromJson(response.toString(), TraceCode.class);
    }

    /**
     * This method recieves a multiple choice type question
     * @param questionId
     * @return  string stating whether the proccess was successful
     */
    public MultipleChoice getMCQuestion (String questionId) throws IOException, URISyntaxException
    {
        String url = serverURL + "/getMultipleChoiceQuestion?questionId=" + URLEncoder.encode(questionId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        return gson.fromJson(response.toString(), MultipleChoice.class);
    }

    /**
     * Deletes an existing question from the database
     * @param question id
     * @return string stating whether the proccess was successful
     */
    public String deleteQuestion (String questionId)throws IOException, URISyntaxException
    {
        String url = serverURL + "/deleteQuestion?questionId=" + URLEncoder.encode(questionId, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("DELETE");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        return response.toString();
    }

    /**
     * Deletes an existing question from the database
     * @param question id
     * @return string stating whether the proccess was successful
     */
    public String getChatGPTResponse (String prompt) throws IOException, URISyntaxException
    {
        String url = serverURL + "/getChatGPTResponse?prompt=" + URLEncoder.encode(prompt, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        return response.toString();
    }

    public String getCompilerResult (String code, String language, String input) throws IOException, URISyntaxException
    {
        if(language.equals("Python")) language = "python3";
        if(language.equals("Java")) language = "java";
        String url = serverURL + "/getCompilerResult?code=" + URLEncoder.encode(code, "UTF-8") + "&language=" + URLEncoder.encode(language, "UTF-8") + "&input=" + URLEncoder.encode(input, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        // Read response
        String response = "";

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response += inputLine + "\n";
            }
            response = response.substring(0, response.length() - 1);
        }
        
        if (connection != null) {
            connection.disconnect();
        }
        return response;
    }
    
    public String getQuestionTitle (String questionID) throws IOException, URISyntaxException
    {
        String url = serverURL + "/getQuestionTitle?questionId=" + URLEncoder.encode(questionID, "UTF-8");
        connection = (HttpURLConnection) new URI(url).toURL().openConnection();
        connection.setRequestMethod("GET");

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        if (connection != null) {
            connection.disconnect();
        }

        return response.toString();
    }

    public static void main(String[] args) throws IOException, URISyntaxException{
        Server server  = new Server();
        // System.out.println(server.getCompilerResult("a=int(input());print(a+2)", "python3", "3"));
        // System.out.println(server.getChatGPTResponse("Write a poem"));
        // server.addFriend("user_o", "U1");
        // server.deleteQuestion("exampleQuestion");
        // CodeWriting wc = new CodeWriting("2n", "A1", "java", "Easy");
        // wc.setStatement("double the number");
        // wc.setId("WCPYE" + server.getTotalQuestionCount());
        // wc.addTestcase("3", "6");
        // System.out.println(server.addCodeWritingQuestion(wc));
        // System.out.println(gson.toJson(server.getWCQuestion("WCPYE171")));
        // server.deleteQuestion("FBHYZ003");
        System.out.println(server.login("Erkam", "Erkam123"));
        // System.out.println(server.getUsername("U111"));
        // System.out.println(server.getWrittenList("A1"));
    }
}