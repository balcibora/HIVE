package databasetest1.demo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question 
{
    private String id;
    private String name;
    private String type;
    private String language;
    private String author;
    private String difficulty;
    private int solveCount;
    private String statement;
}
