package io.github.zaragozamartin91.contrazt.main;

import io.github.zaragozamartin91.contrazt.main.pojo.PojoWithNestedPojos;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MasterContainerInspectorTest {

    @Test
    void getProperties() {
        // GIVEN
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

        // WHEN
        List<ContainerProperty> result = masterContainerInspector.getProperties();
        // printing result...
        result.forEach(p -> System.out.println(ContainerProperty.asString(p)));


        // THEN
        List<ContainerProperty> expectedProperties = Arrays.asList(
                new ContainerPropertyStub("base_name.field0", "some_value"),
                new ContainerPropertyStub("base_name.field1[0]", "ls_one"),
                new ContainerPropertyStub("base_name.field1[1]", "ls_two"),
                new ContainerPropertyStub("base_name.field1[2]", "ls_three"),
                new ContainerPropertyStub("base_name.field2{map_three}", 3L),
                new ContainerPropertyStub("base_name.field2{map_one}", "one"),
                new ContainerPropertyStub("base_name.field2{map_two}", 2),
                new ContainerPropertyStub("base_name.field3.field0", "other_value"),
                new ContainerPropertyStub("base_name.field3.field1[0]", "ls_four"),
                new ContainerPropertyStub("base_name.field3.field1[1]", "ls_five"),
                new ContainerPropertyStub("base_name.field3.field1[2]", "ls_six"),
                new ContainerPropertyStub("base_name.field3.field2{map_five}", 5),
                new ContainerPropertyStub("base_name.field3.field2{map_six}", 6L),
                new ContainerPropertyStub("base_name.field3.field2{map_four}", "four"),
                new ContainerPropertyStub("base_name.field3.field3", null)
        );

        expectedProperties.forEach(ep -> {
            String expectedName = ep.getName();
            Optional<?> expectedValue = ep.getValue();

            assertTrue(result.stream().anyMatch(r -> {
                        String resultName = r.getName();
                        Optional<?> resultValue = r.getValue();

                        return expectedValue
                                .map(o -> expectedName.equals(resultName) && resultValue.isPresent() && resultValue.get().equals(o))
                                .orElseGet(() -> expectedName.equals(resultName) && !resultValue.isPresent());
                    }
            ));
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