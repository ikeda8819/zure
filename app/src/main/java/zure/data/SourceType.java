package zure;

public enum SourceType {
    MySQL("rdb", "mysql", "com.mysql.jdbc.Driver", "jdbc:mysql:", "RdbService"),
    PostgreSQL("rdb", "postgresql", "org.postgresql.Driver", "jdbc:postgresql:", "RdbService"),
    Oracle("rdb", "oracle", "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@", "RdbService"),
    SQLServer("rdb", "sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "test", "RdbService"),
    SQLite("rdb", "sqlite", "org.sqlite.JDBC", "jdbc:sqlite:jdbc:sqlite:jdbc:sqlite:", "RdbService"),
    DB2("rdb", "db2", "com.ibm.db2.jcc.DB2Driver", "testtesttesttesttesttesttesttesttest", "RdbService"),
    Redshift("rdb", "redshift", "com.amazon.redshift.jdbc.Driver", "test", "RdbService"),
    MongoDB("nosql", "mongodb", "mongodb.jdbc.MongoDriver", "test", "MongoDbService"),
    Elasticsearch("nosql", "elasticsearch", "--", "test", "RdbService"),
    Redis("nosql", "redis", "--", "testtesttesttesttesttesttesttesttesttest", "RdbService"),
    CSV("file", "csv", "--", "testtesttesttesttesttesttesttesttesttesttesttest", "RdbService"),
    TSV("file", "tsv", "--", "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttest", "RdbService"),
    JSON("file", "json", "--", "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttest", "RdbService"),
    XML("file", "xml", "--", "test", "RdbService");

    private String genre;
    private String label; // pk
    private String driverName;
    private String urlPrefix;
    private String serviceClass;

    private SourceType(String genre, String label, String driverName, String urlPrefix, String serviceClass) {
        this.genre = genre;
        this.label = label;
        this.driverName = driverName;
        this.urlPrefix = urlPrefix;
        this.serviceClass = serviceClass;
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

    public String getServiceClass() {
        return serviceClass;
    }

    public static SourceType getByType(String type) {
        String _type = type.toLowerCase();
        for (SourceType source : SourceType.values()) {
            if (_type.equals(source.getLabel())) {
                return source;
            }
        }
        return null;
    }

    public static String getDriverNameByType(String type) {
        return getByType(type).driverName;
    }

    public static String getUrlPrefixByType(String type) {
        return getByType(type).urlPrefix;
    }

    public static String getGenreByType(String type) {
        return getByType(type).genre;
    }

    public static Class<?> getServiceClassByType(String type) throws ClassNotFoundException {
        return Class.forName("zure.service." + getByType(type).serviceClass);
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
