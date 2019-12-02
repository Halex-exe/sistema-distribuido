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

            byte[] parte1 = new byte[result.length / 4];
            byte[] parte2 = new byte[result.length / 4]; //mandar para o 1020.
            byte[] parte3 = new byte[result.length / 4]; //mandar para o 1030.
            byte[] parte4 = new byte[result.length / 4]; //mandar para o 1040.

            System.arraycopy(result, 0, parte1, 0, parte1.length);
            System.arraycopy(result, parte1.length, parte2, 0, parte2.length);
            System.arraycopy(result, parte1.length + parte2.length, parte3, 0, parte3.length);
            System.arraycopy(result, parte1.length + parte1.length + parte3.length, parte4, 0, parte4.length);

            byte result2[] = new byte[parte1.length + parte2.length + parte3.length + parte4.length];

            System.arraycopy(parte1, 0, result2, 0, parte1.length);
            System.arraycopy(parte2, 0, result2, parte1.length, parte2.length);
            System.arraycopy(parte3, 0, result2, parte1.length + parte2.length, parte3.length);
            System.arraycopy(parte4, 0, result2, parte1.length + parte2.length + parte3.length, parte4.length);



            try (FileOutputStream fos = new FileOutputStream("/Users/alexandre/Desktop/Arquivo/Dowload/arquivo")) {
                fos.write(result2);
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
