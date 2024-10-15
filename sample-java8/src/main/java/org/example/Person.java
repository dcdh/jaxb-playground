package org.example;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
public class Person implements Serializable {

    private String name;
    private int age;

    public Person() {
    }

    public Person(final String name, final Integer age) {
        this.name = Objects.requireNonNull(name);
        this.age = Objects.requireNonNull(age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}