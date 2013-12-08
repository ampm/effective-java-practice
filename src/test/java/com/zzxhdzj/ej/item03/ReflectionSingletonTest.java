package com.zzxhdzj.ej.item03;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/8/13
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReflectionSingletonTest extends TestCase {
    private ReflectionSingleton sone = null, stwo = null;
    private static Logger logger = LoggerFactory.getLogger(ReflectionSingletonTest.class);

    public void test_reflectionSingleton() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        sone = ReflectionSingleton.getInstance();
        Class rs = Class.forName("com.zzxhdzj.ej.item03.ReflectionSingleton");
        Constructor[] constructors = rs.getDeclaredConstructors();
        Constructor constructor = constructors[0];
        constructor.setAccessible(true);
        stwo = (ReflectionSingleton) constructor.newInstance(null);
        assertNotSame(sone, stwo);
    }
}
