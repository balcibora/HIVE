package databasetest1.demo.jsonClasses;

import java.util.ArrayList;
import java.util.List;

public class GptGsonRes {
    private String id;
    private String object;
    private String created;
    private String model;
    private List<chocie> choices = new ArrayList<>();
    private usage usage;
    private String system_footprint;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<chocie> getChoices() {
        return choices;
    }

    public void setChoices(List<chocie> choices) {
        this.choices = choices;
    }

    public usage getUsage() {
        return usage;
    }

    public void setUsage(usage usage) {
        this.usage = usage;
    }

    public String getSystem_footprint() {
        return system_footprint;
    }

    public void setSystem_footprint(String system_footprint) {
        this.system_footprint = system_footprint;
    }

    public class chocie{
        private int index;
        private message message;
        private String logprobs;
        private String finish_reason;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public message getMessage() {
            return message;
        }

        public void setMessage(message message) {
            this.message = message;
        }

        public String getLogprobs() {
            return logprobs;
        }

        public void setLogprobs(String logprobs) {
            this.logprobs = logprobs;
        }

        public String getFinish_reason() {
            return finish_reason;
        }

        public void setFinish_reason(String finish_reason) {
            this.finish_reason = finish_reason;
        }

        public class message {
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
        }
    }

    public class usage{
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
        public int getPrompt_tokens() {
            return prompt_tokens;
        }
        public void setPrompt_tokens(int prompt_tokens) {
            this.prompt_tokens = prompt_tokens;
        }
        public int getCompletion_tokens() {
            return completion_tokens;
        }
        public void setCompletion_tokens(int completion_tokens) {
            this.completion_tokens = completion_tokens;
        }
        public int getTotal_tokens() {
            return total_tokens;
        }
        public void setTotal_tokens(int total_tokens) {
            this.total_tokens = total_tokens;
        }
    }
}
