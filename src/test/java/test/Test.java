package test;

import com.typesafe.config.Config;
import me.tianjx98.Jscrapy.utils.Setting;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {

    public static final Config SETTINGS = Setting.SETTINGS;

    public static void test(Set<?> set) {
        //Objects.requireNonNull(set, "set");
        System.out.println(set);
    }

    public static void main(String[] args) throws Exception {
        String s = "1" + "2";
        System.out.println(s + 3 + "4" + s + 5);
    }

}