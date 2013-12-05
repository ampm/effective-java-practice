package com.zzxhdzj.ej.item01;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/4/13
 * Time: 5:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    private static Provider DEFAULT_PROVIDER = new Provider() {
        @Override
        public Service newService() {
            return new Service() {
                @Override
                public String toString() {
                    return "Default service";
                }
            };
        }
    };
    private static Provider CUSTOM_PROVIDER = new Provider() {
        @Override
        public Service newService() {
            return new Service() {
                @Override
                public String toString() {
                    return "A custom service";
                }
            };
        }
    };
    private static Provider ANOTHER_PROVIDER = new Provider() {
        @Override
        public Service newService() {
            return new Service() {
                @Override
                public String toString() {
                    return "Another service";
                }
            };
        }
    };

    public static void main(String[] args) {
        ServiceFactory.registerProvider(DEFAULT_PROVIDER);
        ServiceFactory.registerProvider("custom", CUSTOM_PROVIDER);
        ServiceFactory.registerProvider("another", ANOTHER_PROVIDER);

        Service s0 = ServiceFactoryWithoutProvider.getService();
        Service s1 = ServiceFactory.newInstance();
        Service s2 = ServiceFactory.newInstance("custom");
        Service s3 = ServiceFactory.newInstance("another");
        System.out.printf("%s\n%s\n%s\n%s\n", s0, s1, s2, s3);
    }
}
