package me.tianjx98.jscrapy.utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.reflections.Reflections;

import me.tianjx98.jscrapy.core.annotation.Spider;

/**
 * @author tianjx98
 * @date 2021/11/3 16:39
 */
public class ClassUtil {
    public static <T> List<T> getInstanceByAnnotation(String classPackage, Class<? extends Annotation> annotation,
                    Class<T> cls) throws InstantiationException, IllegalAccessException {
        final Reflections reflections = new Reflections(classPackage);
        final List<T> instance = new ArrayList<>();
        for (Class<?> aClass : reflections.getTypesAnnotatedWith(Spider.class)) {
            instance.add((T) aClass.newInstance());
        }
        return instance;
    }
}
