package me.tianjx98.jscrapy.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;

import me.tianjx98.jscrapy.core.annotation.Order;

/**
 * @author tianjx98
 * @date 2021/11/3 16:39
 */
public class ClassUtil {

    private Reflections reflections;

    private ClassUtil(String classPackage) {
        this.reflections = new Reflections(classPackage);
    }

    public static ClassUtil of(String classPackage) {
        return new ClassUtil(classPackage);
    }

    public <T> List<T> getInstanceByAnnotation(Class<? extends Annotation> annotation, Class<T> cls)
                    throws InstantiationException, IllegalAccessException {
        final List<T> instance = new ArrayList<>();
        for (Class<?> clazz : reflections.getTypesAnnotatedWith(annotation)) {
            instance.add((T) clazz.newInstance());
        }
        return instance;
    }

    public <T> List<T> getInstanceByClass(Class<T> cls) throws InstantiationException, IllegalAccessException {
        final List<T> instance = new ArrayList<>();
        for (Class<?> clazz : reflections.getSubTypesOf(cls)) {
            if (Modifier.isAbstract(clazz.getModifiers())) {
                continue;
            }
            instance.add((T) clazz.newInstance());
        }
        return instance;
    }

    public <T> List<T> getInstanceByClassAndAnnotation(Class<T> cls, Class<? extends Annotation> annotation)
                    throws InstantiationException, IllegalAccessException {
        final List<T> instance = new ArrayList<>();
        final Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(cls);
        final List<Class<? extends T>> sortedSubTypes = subTypes.stream().filter(
                        clazz -> !Modifier.isAbstract(clazz.getModifiers()) && clazz.isAnnotationPresent(annotation))
                        .sorted((c1, c2) -> {
                            final Order a1 = c1.getAnnotation(Order.class);
                            return getOrder(c1) - getOrder(c2);
                        }).collect(Collectors.toList());
        for (Class<? extends T> clazz : sortedSubTypes) {
            instance.add(clazz.newInstance());
        }
        return instance;
    }

    private int getOrder(Class<?> cls) {
        final Order annotation = cls.getAnnotation(Order.class);
        return annotation == null ? Integer.MAX_VALUE : annotation.value();
    }
}
