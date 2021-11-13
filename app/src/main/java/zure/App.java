/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package zure;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>.args[0]->" + args[0] + ", args[1]->" + args[1]);

        TargetData data_A = null;
        TargetData data_B = null;
        Connection connection_A = null;
        Connection connection_B = null;
        try {

            data_A = Zure.loadDataFromFile(args[0]);
            data_B = Zure.loadDataFromFile(args[1]);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>.loadDataFromFile complete");

            connection_A = DataSource.getConnection(data_A.type, data_A.host, data_A.port, data_A.username,
                    data_A.password, data_A.schema, data_A.database);
            connection_B = DataSource.getConnection(data_B.type, data_B.host, data_B.port, data_B.username,
                    data_B.password, data_B.schema, data_B.database);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>.getConnection complete");

            List<String> dataList_A = Zure.executeSQL(connection_A,
                    Zure.buildSQL(data_A.table, data_A.keyColumns, data_A.targetColumns), data_A);
            List<String> dataList_B = Zure.executeSQL(connection_B,
                    Zure.buildSQL(data_B.table, data_B.keyColumns, data_B.targetColumns), data_B);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>.executeSQL complete");

            Map<String, String> result = initResultTemp();

            Zure.bulkCheck(dataList_A, dataList_B, result);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>.bulkCheck complete");

            // 結果出力(html or log)
            ResultTemplate.outputFile(ResultTemplate.getContent(result));
            Zure.outputResultFile(dataList_A, dataList_B);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>.outputFile complete");

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (connection_A != null) {
                    connection_A.close();
                }
                if (connection_B != null) {
                    connection_B.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private static Map<String, String> initResultTemp() {
        Map<String, String> template = new HashMap<>();
        template.put("結果", "");
        template.put("ターゲットAの件数", "");
        template.put("ターゲットBの件数", "");
        template.put("ターゲットBの件数", "");
        template.put("ターゲットBの件数", "");
        return template;
    }
}
