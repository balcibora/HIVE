package Accounts;

import java.util.HashMap;
import java.util.Map;

public class Admin extends Account {

    private Map<String, String> writtenList = new HashMap<>();
    
    public Admin(String aUsername, String aEmail, boolean adminStatus, String aPassword) {
        super(aUsername, aEmail, adminStatus, aPassword);
    }

    public int getQuestionsAdded() {
        int length = 0;
        if (!this.writtenList.isEmpty())
        {
            length = this.writtenList.size();
        }
        return length;
    }
}