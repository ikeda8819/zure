package zure;

import java.util.List;

public interface Executable {

    public List<String> execute(Object connection, TargetData loadedData) throws Exception;

}
