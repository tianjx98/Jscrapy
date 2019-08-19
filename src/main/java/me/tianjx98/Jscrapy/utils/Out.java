package me.tianjx98.Jscrapy.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
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

    public static void println(List<?> list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }

    public static void write(String text, File file) {
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(text.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String read(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            byte[] bytes = new byte[2048];
            int len = 0;
            StringBuilder sb = new StringBuilder();
            while ((len = in.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len, "utf-8"));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
