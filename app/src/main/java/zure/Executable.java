package zure;

import java.util.List;

import zure.data.TargetData;

public interface Executable {

    public List<String> execute(Object connection, TargetData loadedData) throws Exception;

}
