package databasetest1.demo;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultipleChoiceQuestion extends Question
{
    private Map<String,String> choices;
    private int correctIndex;
}
