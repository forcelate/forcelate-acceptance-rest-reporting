package com.jedivision.acceptance.rest.utils.test;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings({ "rawtypes", "unchecked" })
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JediRandomUtils {
    private static Random rnd = ThreadLocalRandom.current();

    static final String FREEMARKER_MAP_KEY = "randomKey";
    static final String FREEMARKER_MAP_VALUE = "randomValue";
    static final String EMAIL_DOMEN = "@jedivision.com";
    static final int UNIQUE_VALUE = 1;

    public static <T extends Enum> T randomEnum(Class<?> enumClazz) {
        Object[] values = enumClazz.getEnumConstants();
        return (T) values[rnd.nextInt(values.length)];
    }

    public static Map<String, Object> randomFreemarkerMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(FREEMARKER_MAP_KEY, FREEMARKER_MAP_VALUE);
        return map;
    }

    public static boolean randomBoolean() {
        return rnd.nextBoolean();
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static String randomEmail() {
        return UUID.randomUUID().toString() + EMAIL_DOMEN;
    }

    public static int randomUnique() {
        return UNIQUE_VALUE;
    }

    public static Integer randomSmallInteger() {
        return rnd.nextInt(4) + 1;
    }

    public static Integer randomIntegerByBound(int bound) { return rnd.nextInt(bound); }

    public static Integer randomInteger() {
        return rnd.nextInt();
    }

    public static Long randomLong() {
        return rnd.nextLong();
    }

    public static Double randomDouble() {
        return rnd.nextDouble();
    }

    public static Short randomShort() {
        return (short) rnd.nextInt(Short.MAX_VALUE + 1);
    }
}
