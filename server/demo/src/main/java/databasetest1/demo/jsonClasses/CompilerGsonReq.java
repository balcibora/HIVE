package databasetest1.demo.jsonClasses;

public class CompilerGsonReq {
    private String language;
    private String version;
    private String code;
    private String input;

    public CompilerGsonReq(String language, String version, String code, String input){
        this.language = language;
        this.version = version;
        this.code = code;
        this.input = input;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getInput() {
        return input;
    }
    public void setInput(String input) {
        this.input = input;
    }
}
