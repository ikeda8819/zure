package zure;

public enum SourceType {
    MySQL("rdb", "mysql", "com.mysql.jdbc.Driver", "jdbc:mysql://"),
    PostgreSQL("rdb", "postgresql", "org.postgresql.Driver", "jdbc:postgresql://"),
    Oracle("rdb", "oracle", "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@"),
    SQLServer("rdb", "sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "test"),
    SQLite("rdb", "sqlite", "org.sqlite.JDBC", "jdbc:sqlite:jdbc:sqlite:jdbc:sqlite:"),
    DB2("rdb", "db2", "com.ibm.db2.jcc.DB2Driver", "testtesttesttesttesttesttesttesttest"),
    Redshift("rdb", "redshift", "com.amazon.redshift.jdbc.Driver", "test"),
    MongoDB("nosql", "mongodb", "mongodb.jdbc.MongoDriver", "test"),
    Elasticsearch("nosql", "elasticsearch", "--", "test"),
    Redis("nosql", "redis", "--", "testtesttesttesttesttesttesttesttesttest"),
    CSV("file", "csv", "--", "testtesttesttesttesttesttesttesttesttesttesttest"),
    TSV("file", "tsv", "--", "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"),
    JSON("file", "json", "--", "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"),
    XML("file", "xml", "--", "test");

    private String genre;
    private String label; // pk
    private String driverName;
    private String urlPrefix;

    private SourceType(String genre, String label, String driverName, String urlPrefix) {
        this.genre = genre;
        this.label = label;
        this.driverName = driverName;
        this.urlPrefix = urlPrefix;
    }

    public String getGenre() {
        return genre;
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

    public static String getDriverNameByType(String type) {
        String _type = type.toLowerCase();
        for (SourceType source : SourceType.values()) {
            if (_type.equals(source.getLabel())) {
                return source.getDriverName();
            }
        }
        return "";
    }

    public static String getUrlPrefixByType(String type) {
        String _type = type.toLowerCase();
        for (SourceType source : SourceType.values()) {
            if (_type.equals(source.getUrlPrefix())) {
                return source.getUrlPrefix();
            }
        }
        return "";
    }

    public static String getGenreByType(String type) {
        String _type = type.toLowerCase();
        for (SourceType source : SourceType.values()) {
            if (_type.contains(source.getGenre())) {
                return source.getGenre();
            }
        }
        return "";
    }

    public static boolean isRDB(String type) {
        return getGenreByType(type) == "rdb";
    }

    public static boolean isNoSQL(String type) {
        return getGenreByType(type) == "nosql";
    }

    public static boolean isFile(String type) {
        return getGenreByType(type) == "file";
    }
}
