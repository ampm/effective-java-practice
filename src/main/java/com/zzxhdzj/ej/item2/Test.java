package com.zzxhdzj.ej.item2;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/5/13
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {
        Person person1 = new PersonBuilder().withFirstName("John").withLastName("Doe").withTitle("Mr").build();
        Person person2 = new PersonBuilder().withFirstName("John").withTitle("Mr").build();
    }
}
