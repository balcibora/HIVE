package databasetest1.demo.jsonClasses;

import java.util.ArrayList;
import java.util.List;

public class GptGsonReq {
    
    private String model;
    private List<message> messages = new ArrayList<>();
    
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public GptGsonReq(String model, String content){
        this.model = model;
        this.messages.add(new message("user", content));
    }   

    public class message{
        private String role;
        private String content;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public message(String role, String content){
            this.role = role;
            this.content = content;
        }
    }
}

