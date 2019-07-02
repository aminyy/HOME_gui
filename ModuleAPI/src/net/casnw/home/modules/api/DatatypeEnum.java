//@DECLARE@
package net.casnw.home.modules.api;

/**
 * 变量数据类型枚举
 *
 * @author 敏玉芳
 * @since 2013-12-01
 * @version 1.0
 */
public enum DatatypeEnum {

    PoolInteger {
                public String getString() {
                    return "PoolInteger";
                }
            },
    PoolFloat {
                public String getString() {
                    return "PoolFloat";
                }
            },
    PoolDouble {
                public String getString() {
                    return "PoolDouble";
                }
            },
    PoolLong {
                public String getString() {
                    return "PoolLong";
                }
            },
    PoolString {
                public String getString() {
                    return "PoolString";
                }
            },
    PoolIntegerArray {
                public String getString() {
                    return "PoolIntegerArray";
                }
            },
    PoolFloatArray {
                public String getString() {
                    return "PoolFloatArray";
                }
            },
    PoolDoubleArray {
                public String getString() {
                    return "PoolDoubleArray";
                }
            },
    PoolBooleanArray {
                public String getString() {
                    return "PoolBooleanArray";
                }
            },
    PoolLongArray {
                public String getString() {
                    return "PoolLongArray";
                }
            },
    PoolStringArray {
                public String getString() {
                    return "PoolStringArray";
                }
            },
    PoolInteger2DArray {
                public String getString() {
                    return "PoolInteger2DArray";
                }
            },
    PoolFloat2DArray {
                public String getString() {
                    return "PoolFloat2DArray";
                }
            },
    PoolDouble2DArray {
                public String getString() {
                    return "PoolDouble2DArray";
                }
            },
    PoolBoolear2DArray {
                public String getString() {
                    return "PoolBoolear2DArray";
                }
            },
    PoolLong2DArray {
                public String getString() {
                    return "PoolLong2DArray";
                }
            },
    PoolString2DArray {
                public String getString() {
                    return "PoolString2DArray";
                }
            },
    PoolInteger3DArray {
                public String getString() {
                    return "PoolInteger3DArray";
                }
            },
    PoolDouble3DArray {
                public String getString() {
                    return "PoolDouble3DArray";
                }
            },
    PoolDouble4DArray {
                public String getString() {
                    return "PoolDouble4DArray";
                }
            },
    PoolObject {
                public String getString() {
                    return "PoolObject";
                }
            },
    PoolDate {
                public String getString() {
                    return "PoolDate";
                }
            },
    PoolCalendar {
                public String getString() {
                    return "PoolCalendar";
                }
            },
    PoolObjectArray {
                public String getString() {
                    return "PoolObjectArray";
                }
            };

    public abstract String getString();//这里是很重要的
}
