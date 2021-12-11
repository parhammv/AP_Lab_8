package com.company;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    private ServerSocket server;
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            this.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("server is waiting for the client...");
            try {
                clientSocket = server.accept();
                System.out.println("new client has been connected!");
                in = new DataInputStream(
                        new BufferedInputStream(clientSocket.getInputStream()));
                out = new DataOutputStream(clientSocket.getOutputStream());
                String line = "";
                String res = "";
                while (!line.equals("over")) {
                    try {
                        line = in.readUTF();
                        System.out.println(line);
                        res += line + " ";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                out.writeUTF(res);
                clientSocket.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
