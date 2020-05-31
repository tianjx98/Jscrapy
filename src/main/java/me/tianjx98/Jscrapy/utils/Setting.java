package me.tianjx98.Jscrapy.utils;

import me.tianjx98.Jscrapy.dashboard.bean.ConfigObject;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

/**
 * 配置文件的单例类
 *
 * @ClassName Setting
 * @Description 配置文件的单例类
 * @Author tian
 * @Date 2019/7/20 21:05
 * @Version 1.0
 */
public class Setting {
    private static final Yaml yaml;
    //public static final Setting DEFAULT_SETTINGS;
    static {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setPrettyFlow(true);
        yaml = new Yaml(dumperOptions);
        Map map = yaml.load(Setting.class.getResourceAsStream("/" + "default-settings-description.yml"));
        SETTINGS_DESCRIPTION = new Setting(map);
    }
    private static final String USER_SETTING_PATH = "/Users/tianjx98/IdeaProjects/Jscrapy/user-settings.yml";

    //public static final Config SETTINGS = ConfigFactory.load("settings.conf")//读取用户配置文件,会覆盖默认配置文件
    //        .withFallback(ConfigFactory.load("default-settings.conf"));//读取默认配置文件
    private static final Setting SETTINGS_DESCRIPTION;
    private static Setting USER_SETTINGS = load(USER_SETTING_PATH);
    public static final Setting SETTINGS = merge(load("default-settings.yml"), USER_SETTINGS);



    private Map settingsMap;
    private List<ConfigObject> settingList = null;

    private Setting() {
    }

    private Setting(Map settingsMap) {
        this.settingsMap = settingsMap;
    }

    public static LinkedList<ConfigObject> getSettingList(Setting setting, Setting description) {
        LinkedList<ConfigObject> list = new LinkedList<>();
        for (Object o : setting.settingsMap.entrySet()) {
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) o;
            Object k = entry.getKey();
            Object v = entry.getValue();
            ConfigObject configObject = new ConfigObject();
            configObject.setName((String) description.getValueOrDefault(k + "|name", ""));
            configObject.setDescription((String) description.getValueOrDefault(k + "|description", ""));
            configObject.setKey((String) k);
            if (v instanceof Map) {
                configObject.setValue(getSettingList(setting.getSetting((String) k), description.getSetting((String) k)));
            } else {
                configObject.setValue(v);
            }
            list.add(configObject);
        }
        return list;
    }

    /**
     * 从路径中加载配置文件
     *
     * @param path 配置文件路径
     * @return 返回配置文件
     */
    private static Setting load(String path) {
        InputStream inputStream;
        if (!path.startsWith("/")) {//配置文件为相对路径
            inputStream = Setting.class.getResourceAsStream("/" + path);
        } else {
            try {
                inputStream = new FileInputStream(new File(path));
            } catch (FileNotFoundException e) {
                System.err.println("配置文件加载失败!");
                e.printStackTrace();
                return new Setting();
            }
        }
        return new Setting(yaml.load(inputStream));
    }

    private static Setting merge(Setting setting1, Setting setting2) {
        merge(setting1.settingsMap, setting2.settingsMap);
        return new Setting(setting1.settingsMap);
    }

    /**
     * 合并两个配置文件,后面的配置文件会覆盖前面的配置文件
     *
     * @param yml1 配置文件1
     * @param yml2 配置文件2
     */
    private static void merge(Map yml1, Map yml2) {
        for (Object obj : yml2.entrySet()) {
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (yml1.containsKey(key)) {// 如果默认配置存在,就用用户配置覆盖默认配置
                Object yml1Val = yml1.get(key);
                if (yml1Val instanceof Map) {
                    Map yml1ValMap = (Map) yml1Val;
                    yml1ValMap.clear();
                    merge(yml1ValMap, (Map) value);
                } else {
                    yml1.put(key, value);
                }
            } else {
                yml1.put(key, value);
            }
        }
    }

    /**
     * 获取子配置文件
     *
     * @param name 配置文件名称
     * @return 子配置文件对象
     */
    public Setting getSetting(String name) {
        Object value = settingsMap.get(name);
        if (value == null) {
            return new Setting(new HashMap());
        }
        Setting subSetting;
        if (value instanceof Map) {
            subSetting = new Setting((Map) settingsMap.get(name));
        } else {
            Map map = new LinkedHashMap();
            map.put(name, value);
            subSetting = new Setting(map);
        }
        return subSetting;
    }

    public Object getValue(String name) {
        Map settingsMap = this.settingsMap;
        String[] names = name.split("\\|");
        for (int i = 0; i < names.length; i++) {
            if (settingsMap == null) return null;
            if (i == names.length - 1) {
                return settingsMap.get(names[i]);
            } else settingsMap = (Map) settingsMap.get(names[i]);
        }
        return null;
    }

    public Object getValueOrDefault(String name, Object defaultValue) {
        Object value = getValue(name);
        return value == null ? defaultValue : value;
    }

    public boolean containsValue(String name) {
        return getValue(name) != null;
    }

    public int getInt(String name) {
        return (int) getValue(name);
    }

    public double getDouble(String name) {
        return (double) getValue(name);
    }

    public boolean getBoolean(String name) {
        return (boolean) getValue(name);
    }

    public String getString(String name) {
        return (String) getValue(name);
    }

    public List<ConfigObject> getSettingList() {
        if (settingList == null) {
            settingList = getSettingList(SETTINGS, SETTINGS_DESCRIPTION);
        }
        return settingList;
    }

    public List<ConfigObject> getSettingList(Set names, boolean in) {
        getSettingList();
        LinkedList<ConfigObject> list = new LinkedList<>();
        for (ConfigObject object : settingList) {
            if (in == names.contains(object.getKey())) {
                list.add(object);
            }
        }
        return list;
    }

    /**
     * 更新配置文件
     *
     * @param configMap 更新的配置文件map
     */
    public void update(Map configMap) {
        Object config = reformatMap(configMap.get("config"));
        Map userSettingsMap = USER_SETTINGS.settingsMap;
        merge(userSettingsMap, (Map) config);
        try {// 将新的用户配置文件写入到文件
            FileWriter fileWriter = new FileWriter(new File(USER_SETTING_PATH));
            fileWriter.write(yaml.dump(userSettingsMap));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加载更新后的用户配置文件
        USER_SETTINGS = load(USER_SETTING_PATH);
        Map settingsMap = merge(load("default-settings.yml"), USER_SETTINGS).settingsMap;
        SETTINGS.settingsMap = settingsMap;
        // 清除缓存
        settingList = null;
    }

    public Map getSettingsMap() {
        return settingsMap;
    }

    private Object reformatMap(Object config) {
        LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
        if (config instanceof List) {
            List<Map> configList = (List<Map>) config;
            for (Map map1 : configList) {
                map.put(map1.get("key"), reformatMap(map1.get("value")));
            }
            return map;
        }
        if (config instanceof Map) {
            Map configMap = (Map) config;
            map.put(configMap.get("key"), reformatMap(configMap.get("value")));
            return map;
        }
        return config;
    }

    @Override
    public String toString() {
        return settingsMap.toString();
    }
}
