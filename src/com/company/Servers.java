package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Servers {

    private int porta;
    private Socket socket = null;
    private ServerSocket server = null;
    private InputStream in = null;
    private OutputStream out = null;

    public Servers(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new DataInputStream(socket.getInputStream());
            //writes on client socket
            out = new DataOutputStream(socket.getOutputStream());

            // Receiving data from client
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            byte buffer[] = new byte[1000000];
//            baos.write(buffer, 0 , in.read(buffer));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 /* or some other number */];
            int numRead;

            while((numRead = in.read(buffer,0,1024)) > 0) {
                baos.write(buffer, 0, numRead);
            }

            byte result[] = baos.toByteArray();

            try (FileOutputStream fos = new FileOutputStream("/Users/alexandre/Desktop/Arquivo/Dowload/arquivo")) {
                fos.write(result);
            }
            System.out.println("Break");

//            String res = Arrays.toString(result);
//            System.out.println("Recieved from client : "+res);
//
//            //echoing back to client
//            out.write(result);
//
//            System.out.println("Closing connection");
//
//            // close connection
//            socket.close();
//            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
}
