package com.zzxhdzj.ej.item01;

import com.zzxhdzj.ej.item01.Service;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/4/13
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceFactoryWithoutProvider {
    public static Service getService() {
        try {
            return (Service) Class.forName("com.zzxhdzj.ej.item01.MyServiceImpl").newInstance();
        } catch (Throwable throwable) {
            throw new Error(throwable);
        }
    }
}
