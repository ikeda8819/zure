package zure.data;

public class Setting {

    // develop start
    // public static final String CONFIG_FILE_ROOT_PATH = "../config/";
    // public static final String RESULT_FILE_PATH = "../result/" + "result-" +
    // "{{replaceStr}}" + ".html";
    // public static final String RUSULT_TEMPLATE_FILE_PATH =
    // "../result/static/template.txt";
    // public static final String FILE_ROOT_PATH = "../file/";
    // public static final String QUERY_FILE_PATH = "../query/";
    // develop end

    // product start
    public static final String CONFIG_FILE_ROOT_PATH = "./config/";
    public static final String RESULT_FILE_PATH = "./result/" + "result-" +
            "{{replaceStr}}" + ".html";
    public static final String RUSULT_TEMPLATE_FILE_PATH = "./result/static/template.txt";
    public static final String FILE_ROOT_PATH = "./file/";
    public static final String QUERY_FILE_PATH = "./query/";
    // product end

    public static final String OK_STATUS = "{{ok}}";
    public static final String NG_STATUS = "{{ng}}";
    public static final String NOT_YET = "{{-}}";

    public static final String SEPARATE = "{{}}";
    public static final String KV_SEPARATE = "{{kv}}";

}
