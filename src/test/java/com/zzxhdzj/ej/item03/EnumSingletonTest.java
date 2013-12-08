package com.zzxhdzj.ej.item03;

import junit.framework.TestCase;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/8/13
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnumSingletonTest extends TestCase {
    private EnumSingleton sone = null, stwo = null;

    public void test_enumSingleton() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        sone = EnumSingleton.getInstance();
        Class cls = Class.forName("com.zzxhdzj.ej.item03.EnumSingleton");
        Constructor[] constructors = cls.getDeclaredConstructors();
        Constructor constructor = constructors[0];
        constructor.setAccessible(true);
        stwo = (EnumSingleton) constructor.newInstance();
        assertSame(sone,stwo);
    }

}
