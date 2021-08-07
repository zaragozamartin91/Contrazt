package io.github.zaragozamartin91.contrazt.main;

import java.util.ArrayList;
import java.util.List;

class VoidContainerInspector implements ContainerInspector {
    @Override
    public List<ContainerProperty> getProperties() {
        return new ArrayList<>();
    }
}
