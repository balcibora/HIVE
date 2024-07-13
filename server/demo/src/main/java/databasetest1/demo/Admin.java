package databasetest1.demo;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class Admin extends Account
{
    private Map<String, String> writtenList;
}