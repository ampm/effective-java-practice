package com.zzxhdzj.ej.item2;

import com.sun.istack.internal.Builder;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/5/13
 * Time: 10:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersonBuilder {
    private Person person = new Person();

    public InvalidPersonBuilder withFirstName(String firstName) {
        person.setFirstName(firstName);
        return new InvalidPersonBuilder(person);
    }
}
