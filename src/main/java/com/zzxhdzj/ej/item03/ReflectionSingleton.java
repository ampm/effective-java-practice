package com.zzxhdzj.ej.item03;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/8/13
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReflectionSingleton {
    protected ReflectionSingleton() {
    }

    public static ReflectionSingleton getInstance() {
        return (ReflectionSingleton) ReflectionSingletonRegistry.REGISTRY.getInstance("com.zzxhdzj.ej.item03.ReflectionSingleton");
    }

}
