package com.company;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
    private Socket socket;
    private DataInputStream input;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(String host , int port) {
        try {
            socket = new Socket(host , port);
            input = new DataInputStream(System.in);
            this.run();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        String line = "";
        try {
            out = new DataOutputStream(
                    socket.getOutputStream());
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!line.equals("over"))
        {
            try
            {
                line = input.readLine();
                out.writeUTF(line);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
        try
        {
            String res = in.readUTF();
            System.out.println(res);
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
}
