package zure;

class ResultTemplate {

    public static final String resultFilePath = "../result/";

    public static final String TEMPLATE = "<html><head><meta charset='utf-8'><title>RESULT</title>{{BOOTSTRAP}}</head>{{BODY}}</html>";

    public static final String BOOTSTRAP = "<link rel='stylesheet' href='./static/bootstrap.min.css'><script src='./static/bootstrap.min.js'></script>";

    public static final String BODY = "<body><h1>RESULT</h1><section style='border-radius:16px;width:400px;height:240px;border:1px solid black;'></section></body>";

    public static final String RESULT = TEMPLATE.replace("{{BODY}}", BODY).replace("{{BOOTSTRAP}}", BOOTSTRAP);

    public static String getContent(Object result) {
        return "";
    }

    public static void outputFile(String content) {

    }

}