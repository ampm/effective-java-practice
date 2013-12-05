package com.zzxhdzj.ej.item2;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/5/13
 * Time: 10:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidPersonBuilder {
    private Person person;

    public ValidPersonBuilder(Person person) {
        this.person = person;
    }
    public ValidPersonBuilder withFirstName(String firstName){
        person.setFirstName(firstName);
        return this;
    }
    public ValidPersonBuilder withLastName(String lastName){
        person.setLastName(lastName);
        return this;
    }
    public ValidPersonBuilder withTitle(String title) {
        person.setTitle(title);
        return this;
    }
    public Person build(){
        return person;
    }
}
