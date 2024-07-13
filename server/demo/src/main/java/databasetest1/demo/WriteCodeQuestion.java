package databasetest1.demo;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriteCodeQuestion extends Question
{
    private Map<String, String> testcases; 
}
