## About H.I.V.E.

H.I.V.E. is a coding application designed by team .wasp that contains various coding questions for users and an admin interface for admins to add new questions to the database.

Members of team ".wasp":
[@halavurtberke](https://github.com/halavurtberke),
[@balcibora](https://github.com/balcibora),
[@joonullus](https://github.com/joonullus),
[@esertt](https://github.com/esertt),
[@erkam3](https://github.com/erkam3),
[@Gokdeniz-Derelioglu](https://github.com/Gokdeniz-Derelioglu)

## On how to Run the program

In order to run the program properly, follow the steps below:
1. First, make sure that you have Maven installed and properly working on your device: https://maven.apache.org/download.cgi. 
2. Navigate to the ServerLauncher.java class and fill the .env part in the following code (represented with "//here") with the path to your service key to the/your database (more info about the database below): "FileInputStream serviceAccount = new FileInputStream(//here)".
3. Navigate to the GptApi.java class and set the .env part in the following code to your own OpenAI API key: "private String ApiKey = //here".
4. Navigate to the CompilerApi.java class and fill in the .env in the following code to your own Online Code Compiler API key: super(//here, "online-code-compiler.p.rapidapi.com", "application/json").
5. Navigate to the ServerLauncher.java class and run the main. The server should be connected and online after this.
6. Navigate to the Main.java class and run the main. The program should run without issues now. Happy coding!

## About UI

The project contains two user interface pages: one for the users who will solve questions and another for admins who will add questions.
This allows the admins to have an easier experience when adding questions to the database.

JavaFX is used for the user interface. Additionally, SceneBuilder was used to create the scenes for admin and user interfaces,
different question types, etc.

## About Database

Firestore is used as the database for this project. The database is connected to the local server, which is launched separately. The account key for the database should be put to HIVEbywasp->server->demo->src->main->resources folder. The communication between the client and the server is done using URLs and Spring Boot which is launched by Maven.

## About external sources

- `Gson`: library used to convert JSON files to strings and vice versa
- `OpenAI API`: used to get advanced feedback for write-the-code questions
- `Online Code Compiler API by Glavier`: used to compile the user answers for write-the-code questions
- `Maven`: used to launch Spring Boot and add Firestore as a dependency
- `Spring Boot`: used to set the REST API to communicate with the client
- `Firestore Cloud`: used as the database
- `Lombok`: used for auto getters/setters in the server, as a Maven dependency

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
