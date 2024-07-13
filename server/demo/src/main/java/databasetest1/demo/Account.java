package databasetest1.demo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account
{
    private String id;
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;
    private String dateOfCreation;
}