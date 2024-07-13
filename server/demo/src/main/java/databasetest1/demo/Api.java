package databasetest1.demo;
/*
 * Time to start the abstract API class
 * Let me cook >:D
 * 
 * blud is cooking ^^^^
 */

public abstract class Api {
    
    protected String ApiKey;
    protected String ApiHost;
    protected String ContentType;

    
    //I think a constructor is needed
    public Api(String key, String link, String content) {
        this.ApiKey = key;
        this.ApiHost = link;
        this.ContentType = content;
    }

    //getters and setters.
    public String getApiKey() {
        return ApiKey;
    }
    
    public void setApiKey(String apiKey) {
        ApiKey = apiKey;
    }
    
    public String getApiHost() {
        return ApiHost;
    }
    
    public void setApiHost(String apiHost) {
        ApiHost = apiHost;
    }
    
    public String getContentType() {
        return ContentType;
    }
    
    public void setContentType(String contentType) {
        ContentType = contentType;
    }
}
