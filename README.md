# Contrazt
Java library to decompose objects

---

## Use

Add the library as a maven dependency

```xml
<!-- https://mvnrepository.com/artifact/io.github.zaragozamartin91/contrazt -->
<dependency>
    <groupId>io.github.zaragozamartin91</groupId>
    <artifactId>contrazt</artifactId>
    <version>0.0.6</version>
</dependency>
```

or as a gradle one:

```groovy
// https://mvnrepository.com/artifact/io.github.zaragozamartin91/contrazt
implementation group: 'io.github.zaragozamartin91', name: 'contrazt', version: '0.0.6'
```

## Decomposing

Provides methods to flatten an object into its fundamental atoms / atomic fields

An atomic field is one which represents a "pure value" which should not be decomposed, that is to say,
holds no relevant nested fields.

The default atom types are defined in the `AtomType` enum class:

```java
{
    Byte.class,
    Short.class,
    Integer.class,
    Long.class,
    Float.class,
    Double.class,
    Character.class,
    Boolean.class
} // wrapeprs

{
    CharSequence.class,
    Number.class,
    Date.class,
    TemporalAccessor.class,
    Calendar.class,
    Timestamp.class
} // "atomic types"
```

E.G:

Given these types:

```java
    static class Nested1 {
        private String foo; // atomic field
        private Nested2 bar; // nested field

        Nested1(String f, long b, int z) {
            foo = f;
            bar = new Nested2();
            bar.baz = b;
            bar.bash = new Nested2.Nested3();
            bar.bash.zort = z;
        }

        static class Nested2 {
            private long baz; // atomic field
            private Nested3 bash; // nested field

            static class Nested3 {
                private int zort; // atomic field
            }
        }
    }

```

...when calling...

```java
Contrazt.of(new Nested1("one", 2L, 3)).flatten()
```

the `Nested1` instance will be decomposed and flatten into key value pairs
where the keys are the atomic fields' names (prepending `"&ROOT"` as prefix) and the values will
be the nested atomic fields' values

...thus the result will be:

```java
[{"&ROOT.foo"= one}, {"&ROOT.bar.baz"= 2}, {"&ROOT.bar.bash.zort"= 3}]
```

The root prefix can be removed by calling `SingleContrazt#removeRoot`


---

The same decomposition logic works with Maps and lists as well

Given this class:

```java
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
```

...and this instance:

```java
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
```

Calling

```java
SingleContrazt.of(pojoWithNestedPojos).flatten();
```

Will generate the following key value pairs...

```java
{"&ROOT.field0"= some_value}
{"&ROOT.field1[0]"= ls_one}
{"&ROOT.field1[1]"= ls_two}
{"&ROOT.field1[2]"= ls_three}
{"&ROOT.field2{map_three}"= 3}
{"&ROOT.field2{map_one}"= one}
{"&ROOT.field2{map_two}"= 2}
{"&ROOT.field3.field0"= other_value}
{"&ROOT.field3.field1[0]"= ls_four}
{"&ROOT.field3.field1[1]"= ls_five}
{"&ROOT.field3.field1[2]"= ls_six}
{"&ROOT.field3.field2{map_five}"= 5}
{"&ROOT.field3.field2{map_six}"= 6}
{"&ROOT.field3.field2{map_four}"= four}
{"&ROOT.field3.field3"= null}
```

---

## Comparing

The library can also be used to compare the atomic fields of two objects of different types conveniently.

E.G.

Having these two classes:

```java
static class One {
    private final String foo;
    private final long bar;
    private final int baz;

    One(String foo, long bar, int baz) {
        this.foo = foo;
        this.bar = bar;
        this.baz = baz;
    }
}

static class Two {
    private final long bar;
    private final int baz;
    private final BigDecimal bash;

    Two(long bar, int baz, BigDecimal bash) {
        this.bar = bar;
        this.baz = baz;
        this.bash = bash;
    }
}
```

And these instances:

```java
One one = new One("one", 2L, 3);
Two two = new Two(2L, 4, BigDecimal.TEN);
```

By doing:

```java
DoubleContrazt contrazt = Contrazt.of(one).and(two);
List<FieldDiff> result = contrazt.contrastAllFields();
```

All atomic fields on the first object will be compared with all the atomic fields from the second object.
The result is a list of `FieldDiff` instances where each instance holds the qualified name of the compared field, 
the values on each compared object and the result of the comparison.

In this example, the result would be:

```java
{"foo" , MISSING_SECOND}
{"bar" , EQUAL}
{"baz" , VALUE_MISMATCH}
{"bash" , MISSING_FIRST}
```

The possible comparison results are specified in the `FieldDiffStatus` class

## Custom atomic types

Extra atomic types can be configured by adding a text file named `atomic-types.txt` to the classpath

Atomic types must be specified by their fully qualified name and separated by line breaks `\n` like so:

```txt
my.custom.atomic.Type1
my.custom.atomic.Type2

my.custom.atomic.Type3
```
