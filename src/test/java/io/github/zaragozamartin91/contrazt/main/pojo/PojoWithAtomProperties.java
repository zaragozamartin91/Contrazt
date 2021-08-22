package io.github.zaragozamartin91.contrazt.main.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class PojoWithAtomProperties {
    private static final String STATIC_FIELD = "static field";

    private final String field0;
    private final int field1;
    private final long field2;
    private final BigDecimal field3;
    private final float field4;
    private final double field5;
    private final LocalDateTime field6;
    private final Calendar field7;
    private final Date field8;

    public PojoWithAtomProperties(String field0,
                                  int field1,
                                  long field2,
                                  BigDecimal field3,
                                  float field4,
                                  double field5,
                                  LocalDateTime field6,
                                  Calendar field7,
                                  Date field8) {
        this.field0 = field0;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.field6 = field6;
        this.field7 = field7;
        this.field8 = field8;
    }
}
