package io.github.zaragozamartin91.contrazt.main;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MasterContainerInspector implements ContainerInspector {
    /* Inspect all properties of an object using the correct inspector */
    /* For each discovered property, try to apply the proper inspector again */
    /* On each iteration, grow the property's name */

    private final Object container;
    private final String containerName;

    public MasterContainerInspector(Object container, String containerName) {
        this.container = container;
        this.containerName = containerName;
    }

    @Override
    public List<ContainerProperty> getProperties() {
        SmartContainerInspector smartContainerInspector = new SmartContainerInspector(container, containerName);
        List<ContainerProperty> containerProperties = smartContainerInspector.getProperties();

        return containerProperties.stream().flatMap(p -> {
            if (p.isAtom()) {
                return Stream.of(p);
            } else {
                Object value = p.getValue().orElseThrow(NoSuchElementException::new);
                return new MasterContainerInspector(value, p.getName()).getProperties().stream();
            }
        }).collect(Collectors.toList());
    }
}
