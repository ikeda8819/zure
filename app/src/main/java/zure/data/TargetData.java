package zure.data;

import java.util.List;

public class TargetData {

    public String id;

    public String type;

    public String url;
    public String host;
    public String port;
    public String username;
    public String password;
    public String schema;
    public String database;

    public String queryFile;
    public String table;

    public String file;
    public String header;

    public List<String> keyColumns;
    public List<String> targetColumns;

    public boolean hasSQLFile() {
        return (queryFile != null && queryFile != "") && (table == null || table == "");
    }
}