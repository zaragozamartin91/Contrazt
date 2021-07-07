package io.github.zaragozamartin91.contrazt.usecase;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetAllFieldNames {
    private static final Class<?>[] WRAPPER_TYPES = {
            Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Character.TYPE, Boolean.TYPE
    };
    private static final Set<Class<?>> WRAPPER_TYPE_SET =
            Arrays.stream(WRAPPER_TYPES).collect(Collectors.toSet());

    private final boolean keepStatic;
    private final boolean skipWrapperTypes;
    private final boolean skipCharSequence;

    public GetAllFieldNames(boolean keepStatic,
                            boolean skipWrapperTypes,
                            boolean skipCharSequence) {
        this.keepStatic = keepStatic;
        this.skipWrapperTypes = skipWrapperTypes;
        this.skipCharSequence = skipCharSequence;
    }

    public List<String> apply(Object object) {
        Class<?> mainType = object.getClass();
        return apply(mainType);
    }

    public List<String> apply(Class<?> type) {
        return getFieldNames(type, new ArrayList<>());
    }

    private List<String> getFieldNames(Class<?> currType, List<String> fieldPath) {
        if (acceptType(currType)) {
            Field[] declaredFields = currType.getDeclaredFields();
            List<Field> okFields = Arrays.stream(declaredFields).filter(this::acceptField).collect(Collectors.toList());
            if (okFields.isEmpty()) {
                // type has no more fields, build field name and pop stack
                return getFieldNames(null, fieldPath);
            } else {
                // type has more fields, need to go deeper
                return okFields.stream().map(field -> {
                    String fieldName = field.getName();
                    List<String> nextPath = Stream.concat(fieldPath.stream(), Stream.of(fieldName)).collect(Collectors.toList());
                    Class<?> fieldType = field.getType();
                    return getFieldNames(fieldType, nextPath);
                }).flatMap(Collection::stream).collect(Collectors.toList());
            }
        } else {
            // type is primitive or wrapper, build field name and pop stack
            String fullFieldName = String.join(".", fieldPath);
            return Collections.singletonList(fullFieldName);
        }
    }

    private boolean acceptType(Class<?> currType) {
        return !skipType(currType);
    }

    private boolean skipType(Class<?> currType) {
        return currType == null
                || currType.isPrimitive()
                || (skipWrapperTypes && isWrapperType(currType))
                || (skipCharSequence && CharSequence.class.isAssignableFrom(currType));
    }

    private boolean isWrapperType(Class<?> type) {
        return WRAPPER_TYPE_SET.contains(type);
    }

    private boolean acceptField(Field field) {
        return !skipField(field);
    }

    private boolean skipField(Field field) {
        return !keepStatic && isStatic(field);
    }

    private boolean isStatic(Field df) {
        return Modifier.isStatic(df.getModifiers());
    }

    /*byte	java.lang.Byte
short	java.lang.Short
int	java.lang.Integer
long	java.lang.Long
float	java.lang.Float
double	java.lang.Double
char	java.lang.Character
boolean	java.lang.Boolean */
}
