package com.test.optional;

import java.util.Optional;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * @author lixiaoyu
 * @since 2020/6/22
 */
public class OptionalTest {

    static Properties props;

    static {
        props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");
    }

    public static void main(String[] args) {
        assertEquals(5, readDuration(props, "a"));
        assertEquals(0, readDuration(props, "b"));
        assertEquals(0, readDuration(props, "c"));
        assertEquals(0, readDuration(props, "d"));
    }

    public static int readDuration(Properties props, String name){
        try {
            return Optional.ofNullable(props.get(name))
                    .map(element -> Integer.parseInt(element.toString()))
                    .filter(element -> element > 0)
                    .orElse(0);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
