package com.zzxhdzj.ej.item01;

import com.zzxhdzj.ej.item01.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/4/13
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceFactory {

    private static final Map<String,Provider> providers = new ConcurrentHashMap<String, Provider>();
    public static final String DEFAULT_PROVIDER_NAME = "<def>";

    public static void registerProvider(String name, Provider provider){
        providers.put(name,provider);
    }

    public static void registerProvider(Provider provider){
        registerProvider(DEFAULT_PROVIDER_NAME,provider);
    }

    public static Service newInstance(){
        return newInstance(DEFAULT_PROVIDER_NAME);
    }

    public static Service newInstance(String name) {
        Provider p = providers.get(name);
        if(p==null){
           throw new IllegalArgumentException("No provider registered with name:"+name);
        }
        return p.newService();
    }
}
