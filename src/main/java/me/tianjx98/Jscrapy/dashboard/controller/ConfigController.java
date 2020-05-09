package me.tianjx98.Jscrapy.dashboard.controller;

import me.tianjx98.Jscrapy.dashboard.bean.ConfigObject;
import me.tianjx98.Jscrapy.utils.Setting;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Config
 * @Description TODO
 * @Author tianjx98
 * @Date 2020-04-13 11:30
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    private final Setting setting = Setting.SETTINGS;

    @GetMapping("/all")
    List<ConfigObject> getAllConfig() {
        return setting.getSettingList();
    }

    @GetMapping("/defaultHeadersAndProxy")
    List<ConfigObject> getDefaultHeadesAndProxy() {
        HashSet<String> names = new HashSet<>();
        names.add("defaultHeaders");
        names.add("proxy");

        return setting.getSettingList(names, true);
    }

    @GetMapping("/spidersAndMiddlewares")
    List<ConfigObject> getSpidersAndMiddlewares() {
        HashSet<String> names = new HashSet<>();
        names.add("spiders");
        names.add("spiderMiddlewares");
        names.add("itemPipelines");

        return setting.getSettingList(names, true);
    }

    @GetMapping("/others")
    List<ConfigObject> getOthers() {
        HashSet<String> names = new HashSet<>();
        names.add("defaultHeaders");
        names.add("proxy");
        names.add("spiders");
        names.add("spiderMiddlewares");
        names.add("itemPipelines");
        names.add("scheduler");

        return setting.getSettingList(names, false);
    }

    @GetMapping("/{name}")
    Object getConfigByName(@PathVariable("name") String name) {
        HashSet<String> names = new HashSet<>();
        names.add(name);
        return setting.getSettingList(names, true).get(0);
    }

    @PutMapping("/update")
    Object update(@RequestBody Map config) {
        setting.update(config);
        return null;
    }
}
