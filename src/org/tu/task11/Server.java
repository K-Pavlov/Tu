package org.tu.task11;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Server implements AutoCloseable {
    private final ServerSocket serverSocket;
    private final int serverPort;
    private final ExecutorService pool;
    private boolean isRunning = false;

    public Server(final int serverPort, final int poolSize) throws IOException {
        if(serverPort < 0){
            throw new IllegalArgumentException(
                    "Port must be a positive number");
        }

        this.serverPort = serverPort;

        if(poolSize < 0){
            throw new IllegalArgumentException("Pool size must be a positive number");
        }

        this.pool = Executors.newFixedThreadPool(poolSize);
        this.serverSocket = new ServerSocket(this.serverPort);
    }

    public void close() throws IOException {
        this.getServerSocket().close();
        this.shutDownPool();
        this.setIsRunning(false);
    }

    public void startServer(final Consumer<Socket> clientHandler) throws IOException {
        this.setIsRunning(true);

        while(this.getIsRunning()){
            Socket clientSocket = this.getServerSocket().accept();
            System.out.println("Client connected! Host name: " + clientSocket.getInetAddress().getHostName());
            this.getPool().execute(() -> clientHandler.accept(clientSocket));
        }
    }

    public void stopServer() {
        this.setIsRunning(false);
    }

    protected ExecutorService getPool() {
        return this.pool;
    }

    protected ServerSocket getServerSocket() {
        return serverSocket;
    }

    protected void shutDownPool() {
        this.pool.shutdown();
        try {
            if (!this.pool.awaitTermination(60, TimeUnit.SECONDS)) {
                this.pool.shutdownNow();

                if (!this.pool.awaitTermination(60, TimeUnit.SECONDS)){
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            this.pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    protected int getServerPort() {
        return this.serverPort;
    }

    protected boolean getIsRunning() {
        return this.isRunning;
    }

    protected void setIsRunning(final boolean isRunning) {
        this.isRunning = isRunning;
    }
}

