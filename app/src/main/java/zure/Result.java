package zure;

import java.util.List;

final class Result {

    public static final String resultFilePath = "C:\\\\Users\\user\\Desktop\\zure\\result\\";

    public static final String LAYOUT = "<html><title>RESULT</title><head>{{STYLE}}</head>{{BODY}}</html>";

    public static final String STYLE = "<style></style>";

    public static final String BODY = "<body></body>";

    public static final String RESULT = LAYOUT.replace("{{BODY}}", BODY).replace("{{STYLE}}", STYLE);

    public static String getContent(Object result) {
        return "";
    }

    public static void outputFile(String content) {

    }

}