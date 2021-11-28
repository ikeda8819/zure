package zure.service;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import zure.Executable;
import zure.Setting;
import zure.TargetData;
import zure.ZureDataSource;

public class RdbService implements Executable {

    @Override
    public List<String> execute(Object connection, TargetData loadedTargetData) throws Exception {
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
        String sql = "select {{columns}} from {{table}};".replace("{{columns}}", columns.toString())
                .replace("{{table}}", loadedTargetData.table);

        List<String> resultList = new ArrayList<>();

        connection = ZureDataSource.getConnection(loadedTargetData.url, loadedTargetData.username,
                loadedTargetData.password);
        Statement statement = ((Connection) connection).createStatement();
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
