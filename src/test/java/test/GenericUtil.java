package test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import test.engine.TestItem;

/**
 * @author tianjx98
 * @date 2021/11/4 15:29
 */
public interface GenericUtil<T> {
    // 得到泛型类T
    default Class<?> getFirstGenericClass() {
        // 返回表示此 Class 所表示的实体类的 直接父类 的 Type。注意，是直接父类
        Type type = getClass().getGenericSuperclass();
        // 判断 是否泛型
        if (type instanceof ParameterizedType) {
            // 返回表示此类型实际类型参数的Type对象的数组.
            // 当有多个泛型类时，数组的长度就不是1了
            // 将第一个泛型T对应的类返回（这里只有一个）
            return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            // 若没有给定泛型，则返回Object类
            return Object.class;
        }

    }

    public static void main(String[] args) {
        final GenericUtil<TestItem> genericUtil = new GenericUtil<TestItem>() {};
        //System.out.println(genericUtil.getClass().getGenericInterfaces()[0]);
        final TestAbstractClass<TestItem> testAbstractClass = new TestAbstractClass<TestItem>() {
        };
        System.out.println(testAbstractClass.getFirstGenericClass());
    }
}
