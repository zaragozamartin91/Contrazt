package io.github.zaragozamartin91.contrazt.main;

import io.github.zaragozamartin91.contrazt.main.pojo.PojoWithNestedPojos;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MasterContainerInspectorTest {

    @Test
    void getProperties() {
        String field0 = "some_value";
        List<Object> field1 = Arrays.asList("ls_one", "ls_two", "ls_three");
        Map<String, Object> field2 = new HashMap<String, Object>() {{
            put("map_one", "one");
            put("map_two", 2);
            put("map_three", 3L);
        }};
        PojoWithNestedPojos field3 = new PojoWithNestedPojos(
                "other_value",
                Arrays.asList("ls_four", "ls_five", "ls_six"),
                new HashMap<String, Object>() {{
                    put("map_four", "four");
                    put("map_five", 5);
                    put("map_six", 6L);
                }},
                null
        );

        PojoWithNestedPojos pojoWithNestedPojos = new PojoWithNestedPojos(field0, field1, field2, field3);

        MasterContainerInspector masterContainerInspector = new MasterContainerInspector(pojoWithNestedPojos, "base_name");

        List<ContainerProperty> properties = masterContainerInspector.getProperties();

        properties.forEach(p -> {
            System.out.println("name = " + p.getName() + " ; value = " + p.getValue());
        });
    }

    @Test
    void trueGetProperties() {
        PojoWithNestedPojos pojoWithNestedPojos = new PojoWithNestedPojos(
                "one_value",
                Arrays.asList("ls_1", "ls_2", "ls_3"),
                new HashMap<String, Object>() {{
                    put("map_1", "1");
                    put("map_2", 2);
                    put("map_3", 3L);
                }},
                new PojoWithNestedPojos(
                        "other_value",
                        Arrays.asList("ls_4", "ls_5", new PojoWithNestedPojos(
                                        "yet_another_value",
                                        Arrays.asList("ls_6", "ls_7", "ls_8"),
                                        new HashMap<String, Object>() {{
                                            put("map_4", "four");
                                            put("map_5", 5);
                                            put("map_6", 6L);
                                        }},
                                        null
                                )
                        ),
                        new HashMap<String, Object>() {{
                            put("map_7", "seven");
                            put("map_8", 8);
                            put("map_9", new PojoWithNestedPojos(
                                            "even_another_value",
                                            Arrays.asList("ls_9", "ls_10", "ls_11"),
                                            new HashMap<String, Object>() {{
                                                put("map_10", "ten");
                                                put("map_11", 11);
                                                put("map_12", 12L);
                                            }},
                                            null
                                    )
                            );
                        }},
                        null
                )
        );

        MasterContainerInspector masterContainerInspector = new MasterContainerInspector(pojoWithNestedPojos, "base_name");

        List<ContainerProperty> properties = masterContainerInspector.getProperties();

        properties.forEach(p -> {
            System.out.println("name = " + p.getName() + " ; value = " + p.getValue());
        });
    }
}