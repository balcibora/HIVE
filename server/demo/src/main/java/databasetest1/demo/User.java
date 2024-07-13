package databasetest1.demo;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class User extends Account
{
    private int streak;
    private int highestStreak;
    private Map<String, Boolean> friendList;
    private String lastTimeSolved;
    private Map<String, String> solvedQuestions;
}
