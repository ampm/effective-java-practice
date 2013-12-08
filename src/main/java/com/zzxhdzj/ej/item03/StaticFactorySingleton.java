package com.zzxhdzj.ej.item03;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/8/13
 * Time: 5:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class StaticFactorySingleton {
    private static StaticFactorySingleton instance;
    protected StaticFactorySingleton(){
    }
    public static StaticFactorySingleton getInstance(){
        if(instance==null){
          instance = new StaticFactorySingleton();
        }
        return instance;
    }
}
