package com.zzxhdzj.ej.item03;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/8/13
 * Time: 5:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class StaticFieldSingletonTest extends TestCase {
    private StaticFieldSingleton sone = null, stwo = null;
    private static Logger logger = LoggerFactory.getLogger(StaticFieldSingletonTest.class);

    public StaticFieldSingletonTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        sone = StaticFieldSingleton.INSTANCE;
        stwo = StaticFieldSingleton.INSTANCE;
    }

    public void test_serialize() {
        logger.info("testing StaticFieldSingleton serialization...");
        writeSingleTon();
        StaticFieldSingleton s1 = readSingleton();
        StaticFieldSingleton s2 = readSingleton();
        assertEquals(true, s1 == s2);
    }

    public void test_unique() {
        logger.info("testing singleton uniqueness...");
        StaticFieldSingleton another = new StaticFieldSingleton();
        logger.info("checking singletons for equality...");
        assertEquals(true, sone == stwo);

    }

    private StaticFieldSingleton readSingleton() {
        StaticFieldSingleton s = null;
        try {
            FileInputStream fis = new FileInputStream("serializedSingleton");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s = (StaticFieldSingleton) ois.readObject();
        } catch (IOException iox) {
            logger.error("IO Exception: " + iox.getMessage());
        } catch (ClassNotFoundException cnf) {
            logger.error("Class Not Found Exception: " + cnf.getMessage());
        }
        return s;
    }

    private void writeSingleTon() {
        try {
            FileOutputStream fos = new FileOutputStream("serializedSingleton");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            StaticFieldSingleton s = StaticFieldSingleton.INSTANCE;
            oos.writeObject(s);
            oos.flush();
        } catch (IOException iox) {
            logger.error("IO Exception: " + iox.getMessage());
        }
    }

}
