package org.tu.task11;

import java.io.*;
import java.net.Socket;

public class Client implements AutoCloseable{
    private final String server;
    private final int port;
    private final Socket clientSocket;

    public Client(final String server, final int port) throws IOException{
        this.server = server;
        this.port = port;
        this.clientSocket = new Socket(server, port);
    }

    public void close() throws IOException {
        this.getClientSocket().close();
    }

    public int getAgeFromServer(final Person person) throws IOException {
        final OutputStream os = this.getClientSocket().getOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(person);
        oos.flush();

        final InputStream is = this.getClientSocket().getInputStream();
        final ObjectInputStream ois = new ObjectInputStream(is);
        final int age = ois.readInt();
        return age;
    }

    protected Socket getClientSocket() {
        return this.clientSocket;
    }

    protected String getServer() {
        return this.server;
    }

    protected int getPort() {
        return this.port;
    }
}
