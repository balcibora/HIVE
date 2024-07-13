package Accounts;
//package shit don't work for some reason, idk bro
//package account;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import Questions.Question;

public class User extends Account{
    private int streak;
    private int highestStreak;
    private Map<String, String> solvedQuestions;
    private Map<String, Boolean> friendList;
    private String lastTimeSolved;

    public User(String aUsername, String aEmail, boolean adminStatus, String aPassword) {
        super(aUsername, aEmail, adminStatus, aPassword); // may be ++numberOfUsersCreated
        streak = 0;
        highestStreak = 0;
        this.solvedQuestions = new HashMap<>();
        this.lastTimeSolved = "";
        this.friendList = new HashMap<>();
    }

    public void solved(Question q) {
        solvedQuestions.put(q.getId(), "solved");
    }

    public void incrementStreak() {
        this.streak++;
    }
    public void resetStreak() {
        this.streak = 0;
    }
    public Map<String, Boolean> getFriendList() {
        Map<String, Boolean> copy = new HashMap<>();
        for(String key: friendList.keySet()) {
            copy.put(key, friendList.get(key));
        }
        return copy;
    }
    public int getStreak() {
        return streak;
    }
    public int getHighestStreak() {
        return highestStreak;
    }
    public int getQuestionsSolved() {
        return solvedQuestions.size();
    }
    public String toString() {
        String thisString = new Gson().toJson(this);
        return thisString;
    }
    public String getLastTimeSolved() {
        return lastTimeSolved;
    }
}
