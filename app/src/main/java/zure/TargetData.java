package zure;

import java.util.List;

class TargetData {

    public String id;

    public String type;

    public String host;
    public String port;
    public String username;
    public String password;
    public String schema;
    public String database;

    public String queryFile;
    public String table;

    public List<String> keyColumns;
    public List<String> targetColumns;

    public String checkResult;

    public String getDBUrl() {
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
        return url;
    }

    public boolean isSQLFile() {
        return false;
    }
}