package zure;

public enum SourceType {
    MySQL("mysql", "", "jdbc:mysql://"), PostgreSQL("postgresql,postgres", "", "jdbc:postgresql://"),
    Oracle("oracle", "", "jdbc:oracle:thin:@"), SQLServer("sqlserver", "", "test"),
    SQLite("sqlite", "", "jdbc:sqlite:"), DB2("db2", "", "test"), CSV("csv", "", "test"), TSV("tsv", "", "test"),
    JSON("json", "", "test");

    private String label;
    private String driverName;
    private String urlPrefix;

    private SourceType(String label, String driverName, String urlPrefix) {
        this.label = label;
        this.driverName = driverName;
        this.urlPrefix = urlPrefix;
    }

    public String getLabel() {
        return label;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public static String getDriverNameByType(String val) {
        String _type = val.toLowerCase();
        for (SourceType type : SourceType.values()) {
            if (_type.equals(type.getDriverName())) {
                return type.getDriverName();
            }
        }
        return "";
    }

    public static String getUrlPrefixByType(String val) {
        String _type = val.toLowerCase();
        for (SourceType type : SourceType.values()) {
            if (type.getUrlPrefix().contains(_type)) {
                return type.getUrlPrefix();
            }
        }
        return "";
    }
}
