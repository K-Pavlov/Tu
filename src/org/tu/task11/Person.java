package org.tu.task11;

import java.io.Serializable;

public class Person implements Serializable{
    private final String name;
    private final String egn;

    public Person(String name, String egn) {
        this.name = name;
        this.egn = egn;
    }

    public String getName() {
        return this.name;
    }

    public String getEgn() {
        return this.egn;
    }
}
