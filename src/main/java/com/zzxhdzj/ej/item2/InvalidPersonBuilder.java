package com.zzxhdzj.ej.item2;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/5/13
 * Time: 10:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvalidPersonBuilder{
    private Person person;

    public InvalidPersonBuilder(Person person) {
        this.person = person;
    }
    public InvalidPersonBuilder withFirstName(String firstName){
        person.setFirstName(firstName);
        return this;
    }
    public ValidPersonBuilder withLastName(String lastName){
        person.setLastName(lastName);
        return new ValidPersonBuilder(person);
    }

}