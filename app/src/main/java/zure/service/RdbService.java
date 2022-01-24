package zure.service;

import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import zure.Executable;
import zure.data.Setting;
import zure.data.TargetData;

public class RdbService implements Executable {

    @Override
    public List<String> execute(TargetData loadedTargetData) throws Exception {
        String sql = "";
        if (loadedTargetData.hasSQLFile()) {
            List<String> list = new ArrayList<>();
            Files.lines(Paths.get(Setting.QUERY_FILE_PATH + loadedTargetData.queryFile)).forEach(e -> list.add(e));
            sql = String.join(" ", list);
        } else {
            StringBuilder columns = new StringBuilder();
            for (int i = 0; i < loadedTargetData.keyColumns.size(); i++) {
                if (i != 0) {
                    columns.append(", ");
                }
                columns.append(loadedTargetData.keyColumns.get(i));
            }
            for (int i = 0; i < loadedTargetData.targetColumns.size(); i++) {
                columns.append(", " + loadedTargetData.targetColumns.get(i));
            }
            sql = "select {{columns}} from {{table}};".replace("{{columns}}", columns.toString()).replace("{{table}}",
                    loadedTargetData.table);
        }

        // System.out.println(">>>>>>>>>>>>>>>>>>>>>.sql:" + sql);

        List<String> resultList = new ArrayList<>();
        if ("mysql".equals(loadedTargetData.type)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        try (Connection connection = DriverManager.getConnection(loadedTargetData.url, loadedTargetData.username,
                loadedTargetData.password); Statement statement = connection.createStatement();) {

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                StringBuilder keyAndtarget = new StringBuilder(Setting.NOT_YET);
                int i = 0;
                for (String keyColumn : loadedTargetData.keyColumns) {
                    if (i++ != 0) {
                        keyAndtarget.append(Setting.SEPARATE);
                    }
                    keyAndtarget.append(String.valueOf(rs.getObject(keyColumn)));
                }
                keyAndtarget.append(Setting.KV_SEPARATE); // この文字列でsplitしてkeyとtargetを判別出来るようにする

                i = 0;
                for (String targetColumn : loadedTargetData.targetColumns) {
                    if (i++ != 0) {
                        keyAndtarget.append(Setting.SEPARATE);
                    }
                    keyAndtarget.append(String.valueOf(rs.getObject(targetColumn)));
                }
                // System.out.println("keytarget=" + keyAndtarget.toString());
                resultList.add(keyAndtarget.toString());
            }
            return resultList;
        }
    }
}
