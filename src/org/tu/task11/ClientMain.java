package org.tu.task11;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {
        final String server = "127.0.0.1";
        final int port = 90;
        try {
            try (Client client = new Client(server, port)) {
                final Person stamat = new Person("Stamat","9102038776");
                int age = client.getAgeFromServer(stamat);
                System.out.println("Person " + stamat.getName() + " is " + age + " years old");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
