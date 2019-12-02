package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    private int porta;
    private Socket socket = null;
    private ServerSocket server = null;
    private InputStream in = null;
    private OutputStream out = null;
    private byte result[] = null;


    public Server(int porta) throws IOException {
        this.porta = porta;
    }

    public void run(){
        try {
            iniciar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iniciar() throws IOException {

        server = new ServerSocket(porta);
        System.out.println("Server started");

        System.out.println("Waiting for a client ...");
        socket = server.accept();
        System.out.println("Client accepted");

        // takes input from the client socket
        in = new DataInputStream(socket.getInputStream());
        //writes on client socket
        out = new DataOutputStream(socket.getOutputStream());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 /* or some other number */];
        int numRead;

        while((numRead = in.read(buffer,0,1024)) > 0) {
            baos.write(buffer, 0, numRead);
        }

        byte result[] = baos.toByteArray();
    }

    public void dowload(){


    }

}
