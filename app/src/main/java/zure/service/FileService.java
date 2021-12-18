package zure.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import zure.Executable;
import zure.data.Setting;
import zure.data.TargetData;

public class FileService implements Executable {

    @Override
    public List<String> execute(TargetData loadedData) throws Exception {

        List<String> list = new ArrayList<>();
        Files.lines(Paths.get(Setting.FILE_ROOT_PATH + loadedData.file)).forEach(e -> list.add(e));

        String delimiter = "";
        if ("csv".equals(loadedData.type)) {
            delimiter = ",";
        } else if ("tsv".equals(loadedData.type)) {
            delimiter = "   ";
        }

        // ヘッダー有の場合は、番号指定or項目名指定で可能
        boolean hasHeader = "true".equals(loadedData.header.trim());

        List<Integer> keyColumns = new ArrayList<>();
        List<Integer> targetColumns = new ArrayList<>();
        if (hasHeader) {
            String header = list.get(0);
            String[] arr = header.split(",");
            for (int j = 0; j < loadedData.keyColumns.size(); j++) {
                for (int k = 0; k < arr.length; k++) {
                    if (arr[k].equals(loadedData.keyColumns.get(j))) {
                        keyColumns.add(k);
                    }
                }
            }
            for (int j = 0; j < loadedData.targetColumns.size(); j++) {
                for (int k = 0; k < arr.length; k++) {
                    if (arr[k].equals(loadedData.targetColumns.get(j))) {
                        targetColumns.add(k);
                    }
                }
            }
        } else {
            keyColumns = loadedData.keyColumns.stream().map(Integer::parseInt).collect(Collectors.toList());
            targetColumns = loadedData.targetColumns.stream().map(Integer::parseInt).collect(Collectors.toList());
        }

        List<String> returnList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (i == 0 && hasHeader) {
                continue;
            }
            StringBuilder keyAndtarget = new StringBuilder(Setting.NOT_YET);
            String[] dataArr = list.get(i).split(delimiter);

            for (int j = 0; j < keyColumns.size(); j++) {
                if (j != 0) {
                    keyAndtarget.append(Setting.SEPARATE);
                }
                keyAndtarget.append(dataArr[keyColumns.get(j)]);
            }

            keyAndtarget.append(Setting.KV_SEPARATE);

            for (int j = 0; j < targetColumns.size(); j++) {
                if (j != 0) {
                    keyAndtarget.append(Setting.SEPARATE);
                }
                keyAndtarget.append(dataArr[targetColumns.get(j)]);
            }

            returnList.add(keyAndtarget.toString());
        }

        System.out.println("returnListreturnListreturnListreturnListreturnList--->>" + returnList);
        return returnList;
    }

}
