package databasetest1.demo.jsonClasses;

public class CompilerGsonRes {
    private double cpuTime = 10.1;
    private String output = "7";
    private long memory = 12434;
    private language lang;

    
    public CompilerGsonRes(double cpuTime, String outPut, long memory, language lang){
        this.cpuTime = cpuTime;
        this.output = outPut;
        this.memory = memory;
        this.lang = lang;
        this.lang = lang;
    }
    
    public CompilerGsonRes(){
        
    }
    
    public language getLang() {
        return lang;
    }

    public void setLang(language lang) {
        this.lang = lang;
    }
    
    public double getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(double cpuTime) {
        this.cpuTime = cpuTime;
    }

    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    protected class language{
        private String id;
        private String version_name;
        private String version;
        
        public String getId() {
            return id;
        }
    
        public void setId(String id) {
            this.id = id;
        }
    
        public String getVersion() {
            return version;
        }
    
        public void setVersion(String version) {
            this.version = version;
        }
    
        public String getVersion_name() {
            return version_name;
        }
    
        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }
    
        public language(String id, String version, String version_name){
            this.id = id;
            this.version = version;
            this.version_name = version_name;
        }
    }
}
