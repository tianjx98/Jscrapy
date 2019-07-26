package test;

import com.typesafe.config.Config;
import me.tianjx98.Jscrapy.utils.Setting;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {
    public static final Config SETTINGS = Setting.SETTINGS;

    public static void main(String[] args) throws Exception {
        Path path = Paths.get("D:/test/test.txt");
        path.getParent().toFile().mkdirs();
        path.toFile().createNewFile();
        //File file = new File();
        //if (!file.exists()) {
        //    file.mkdirs();
        //}
        //System.out.println("当前类:Test.main() 第23行:file.isDirectory()值=" + file.isDirectory());
        //System.out.println("当前类:Test.main() 第24行:file.isFile()值=" + file.isFile());

    }

}
