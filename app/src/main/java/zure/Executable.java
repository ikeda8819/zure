package zure;

import java.util.List;

import zure.data.TargetData;

public interface Executable {

    public List<String> execute(TargetData loadedData) throws Exception;

}
