package cn.tianjx98.jscrapy.core.annotation;

import java.lang.annotation.*;

/**
 * @author tianjx98
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Order {
    int value() default Integer.MAX_VALUE;
}
