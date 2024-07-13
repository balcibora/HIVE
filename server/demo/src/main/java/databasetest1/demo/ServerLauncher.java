package databasetest1.demo;

import java.io.FileInputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class})
public class ServerLauncher 
{
	public static void main (String[] args) 
	{
		try 
		{
			// Put your full path to serviceAccountKey.json below.
			FileInputStream serviceAccount = new FileInputStream("//here");

			FirebaseOptions options = FirebaseOptions.builder()
			.setCredentials (GoogleCredentials.fromStream (serviceAccount))
			.build();

			FirebaseApp.initializeApp(options);
		}
		catch (Exception e)
		{
			System.out.println ("An error occured while fetching the service key.");
			System.out.println (e.getMessage());
		}

		SpringApplication.run (ServerLauncher.class, args);
	}
}