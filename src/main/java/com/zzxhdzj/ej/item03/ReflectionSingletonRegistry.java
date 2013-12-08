package com.zzxhdzj.ej.item03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/8/13
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReflectionSingletonRegistry {
    public static ReflectionSingletonRegistry REGISTRY = new ReflectionSingletonRegistry();
    private static HashMap map = new HashMap();
    private static Logger logger = LoggerFactory.getLogger(ReflectionSingletonRegistry.class);

    protected ReflectionSingletonRegistry() {

    }

    public static synchronized Object getInstance(String className) {
        Object singleton = map.get(className);
        if (singleton != null) {
            return singleton;
        }
        try {
            singleton = Class.forName(className).newInstance();
            logger.info("created singleton:" + singleton);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        map.put(className, singleton);
        return singleton;
    }
}
