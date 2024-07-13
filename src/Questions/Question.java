package Questions;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class Question {
    protected String name, author, language, difficulty, type, id;
    protected static int questionCount = 0;

    public Question(String aName, String anAuthor, String aLanguage, String aDifficulty, String aType) {
        this.name = aName;
        this.author = anAuthor;
        this.language = aLanguage;
        this.difficulty = aDifficulty;
        this.type = aType;
    }

    abstract String checkAnswer(String userAnswer) throws IOException, URISyntaxException;

    public void setId(String newID) {
        id = newID;
    }

    public void setName(String aName) {
        name = aName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public String getDiff() {
        return difficulty;
    }
    
    public String getType() {
        return type;
    }
}