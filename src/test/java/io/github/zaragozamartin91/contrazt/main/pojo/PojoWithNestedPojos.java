package io.github.zaragozamartin91.contrazt.main.pojo;

import java.util.List;
import java.util.Map;

public class PojoWithNestedPojos {
    private final String field0;
    private final List<Object> field1;
    private final Map<String, Object> field2;
    private final PojoWithNestedPojos field3;

    public PojoWithNestedPojos(String field0, List<Object> field1, Map<String, Object> field2, PojoWithNestedPojos field3) {
        this.field0 = field0;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }
}
