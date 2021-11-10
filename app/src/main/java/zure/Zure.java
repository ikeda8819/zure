package zure;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class Zure {

    private static final String fileRootPath = "C:\\\\Users\\user\\Desktop\\zure\\targets\\";

    private static final String resultFilePath = "C:\\\\Users\\user\\Desktop\\zure\\result\\";

    public static final String ok_status = "{{ok_status}}";
    public static final String ng_status = "{{ng_status}}";
    public static final String not_yet = "{{notyet_status}}";

    public static final String separate = "{{separate}}";
    public static final String kv_separate = "{{kv_separate}}";

    public static void bulkCheck(List<String> list_A, List<String> list_B, Map<String, String> result) {

        for (String data_A : list_A) {
            String[] kv_A = data_A.split(Pattern.quote(kv_separate));
            String key_A = kv_A[0].replace(not_yet, "");
            String val_A = kv_A[1];
            for (String data_B : list_B) {
                if (!data_B.startsWith(not_yet)) {
                    continue;
                }
                String[] kv_B = data_B.split(Pattern.quote(kv_separate));
                String key_B = kv_B[0].replace(not_yet, "");
                String val_B = kv_B[1];
                if (key_A.equals(key_B)) {
                    // 照合開始
                    data_A = data_A.replace(not_yet, val_A.equals(val_B) ? ok_status : ng_status);
                    data_B = data_B.replace(not_yet, val_A.equals(val_B) ? ok_status : ng_status);
                    System.out.println("RESRERSERESRSERESRSEval_data_A->" + data_A);
                    System.out.println("RESRERSERESRSERESRSEval_data_B->" + data_B);
                }
            }
        }

    }

    public static TargetData loadDataFromFile(String filename) {
        try {
            List<String> list = new ArrayList<>();
            Files.lines(Paths.get(fileRootPath + filename)).forEach(e -> list.add(e));

            return targetDataMapping(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static TargetData targetDataMapping(List<String> list) {
        TargetData data = new TargetData();
        for (String line : list) {
            String _line = line.trim();
            if ("".equals(_line)) {
                continue;
            }
            String[] kv = _line.split(":");
            if (kv.length != 2) {
                continue;
            }
            String key = kv[0].trim();
            String val = kv[1].trim();

            if ("type".equals(key)) {
                data.type = val;
            } else if ("host".equals(key)) {
                data.host = val;
            } else if ("port".equals(key)) {
                data.port = val;
            } else if ("username".equals(key)) {
                data.username = val;
            } else if ("password".equals(key)) {
                data.password = val;
            } else if ("database".equals(key)) {
                data.database = val;
            } else if ("query_file".equals(key)) {
                data.queryFile = val;
            } else if ("table".equals(key)) {
                data.table = val;
            }
        }

        // 比較するカラムデータをセット
        List<String> keyColumns = new ArrayList<>();
        List<String> targetColumns = new ArrayList<>();
        boolean keyFound = false;
        boolean targetFound = false;
        for (String line : list) {
            String _line = line.trim();
            if (_line.startsWith("key_columns")) {
                keyFound = true;
                targetFound = false;
            }

            if (_line.startsWith("target_columns")) {
                keyFound = false;
                targetFound = true;
            }

            if (_line.startsWith("-")) {
                if (keyFound && !targetFound) {
                    keyColumns.add(_line.split("-")[1].trim());
                } else if (!keyFound && targetFound) {
                    targetColumns.add(_line.split("-")[1].trim());
                }
            }
        }

        data.keyColumns = keyColumns;
        data.targetColumns = targetColumns;

        return data;
    }

    public static String buildSQL(String table, List<String> keys, List<String> targets) {
        String columns = "";
        for (int i = 0; i < keys.size(); i++) {
            if (i != 0) {
                columns += ", ";
            }
            columns += keys.get(i);
        }
        for (int i = 0; i < targets.size(); i++) {
            columns += ", " + targets.get(i);
        }
        return "select {{columns}} from {{table}};".replace("{{columns}}", columns).replace("{{table}}", table);
    }

    public static List<String> executeSQL(Connection connection, String sql, TargetData targetData) {
        System.out.println(">>>>>>>>>>>>>>>>>>executeSQL.sql:" + sql);

        List<String> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                StringBuilder keyAndtarget = new StringBuilder(not_yet);
                int i = 0;
                for (String keyColumn : targetData.keyColumns) {
                    if (i++ != 0) {
                        keyAndtarget.append(separate);
                    }
                    keyAndtarget.append(String.valueOf(rs.getObject(keyColumn)));
                }
                keyAndtarget.append(kv_separate); // この文字列でsplitしてkeyとtargetを判別出来るようにする

                i = 0;
                for (String targetColumn : targetData.targetColumns) {
                    if (i++ != 0) {
                        keyAndtarget.append(separate);
                    }
                    keyAndtarget.append(String.valueOf(rs.getObject(targetColumn)));
                }

                System.out.println("keytarget=" + keyAndtarget.toString());

                resultList.add(keyAndtarget.toString());
            }

            return resultList;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
