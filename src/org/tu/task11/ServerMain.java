package org.tu.task11;

import java.io.*;
import java.time.Year;

public class ServerMain {
    public static void main(String[] args) {
        final int port = 90;
        final int poolSize = Runtime.getRuntime().availableProcessors() + 1; // optimal
        try {
            try (Server server = new Server(90, poolSize)) {
                server.start(socket -> {
                    try {
                        InputStream is = socket.getInputStream();
                        ObjectInputStream ois = new ObjectInputStream(is);
                        Person person = (Person) ois.readObject();

                        int yearBorn = Integer.parseInt("19" + person.getEgn().substring(0, 2));
                        int age = Year.now().getValue() - yearBorn;
                        OutputStream os = socket.getOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(os);
                        oos.writeInt(age);
                        oos.flush();
                    } catch (IOException|ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
