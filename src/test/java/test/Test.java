package test;

import com.typesafe.config.Config;
import me.tianjx98.Jscrapy.utils.Setting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static final Config SETTINGS = Setting.SETTINGS;
    private int a=1;

    static void add(List<?> list, Object o) {
        Object o1 = list.get(1);
    }

    public static void main(String[] args) throws Exception {
        //ArrayList<String> strings = new ArrayList<>();
        //add(strings, Integer.valueOf(2));
        //System.out.println(strings.get(0));
    }

}
