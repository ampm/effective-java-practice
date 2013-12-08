package com.zzxhdzj.ej.item03;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/8/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class StaticFieldSingleton implements Serializable{
    public static StaticFieldSingleton INSTANCE = new StaticFieldSingleton();

    protected StaticFieldSingleton() {
    }
    private Object readResolve(){
        return INSTANCE;
    }
}
