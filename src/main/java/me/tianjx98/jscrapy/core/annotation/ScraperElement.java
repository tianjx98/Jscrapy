package me.tianjx98.jscrapy.core.annotation;

import java.lang.annotation.*;

/**
 * 爬虫元素, 在类上保护此注解的类才会被爬虫引擎加载
 * @author tianjx98
 * @date 2021/11/4 15:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ScraperElement {
}
