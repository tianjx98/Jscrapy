package me.tianjx98.Jscrapy.utils;


import java.util.Map;

public class Out {
    public static void println(Object[] objects){
        for (Object object : objects) {
            System.out.println(object);
        }
    }

    public static void println(Map<?,?> map) {
        for (Map.Entry<?,?> entry : map.entrySet()) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
}
