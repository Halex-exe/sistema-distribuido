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
    static byte pt1[] = null;

    public Servers(int port) throws IOException {
        // starts server and waits for a connection

            server = new ServerSocket(port);
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

            byte[] parte1 = new byte[result.length / 4]; //ficar aqui.
            //pt1 = parte1;

            byte[] parte2 = new byte[result.length / 4]; //mandar para o 1020.
            byte[] parte3 = new byte[result.length / 4]; //mandar para o 1030.
            byte[] parte4 = new byte[result.length / 4]; //mandar para o 1040.

            System.arraycopy(result, 0, parte1, 0, parte1.length);
            System.arraycopy(result, parte1.length, parte2, 0, parte2.length);
            System.arraycopy(result, parte1.length + parte2.length, parte3, 0, parte3.length);
            System.arraycopy(result, parte1.length + parte1.length + parte3.length, parte4, 0, parte4.length);


            enviar(1020, parte4);
            enviar(1030, parte3);
            enviar(1040, parte4);



            byte result2[] = new byte[parte1.length + parte2.length + parte3.length + parte4.length];

            pt1 = result2;

            System.arraycopy(parte1, 0, result2, 0, parte1.length);
            System.arraycopy(parte2, 0, result2, parte1.length, parte2.length);
            System.arraycopy(parte3, 0, result2, parte1.length + parte2.length, parte3.length);
            System.arraycopy(parte4, 0, result2, parte1.length + parte2.length + parte3.length, parte4.length);


            esperar();
    }
    public void esperar() throws IOException {

        server = new ServerSocket(1111);
        socket = server.accept();

        // takes input from the client socket
        in = new DataInputStream(socket.getInputStream());
        //writes on client socket
        out = new DataOutputStream(socket.getOutputStream());

        out.write(pt1);
        out.flush();
        in.close();
        out.close();
        socket.close();
    }

    public void enviar (int porta, byte[] arquivo){ //enviar o arquivo para os servidores.

        Socket s = null;
        try {
            s = new Socket("localhost", porta);
            OutputStream outS = new DataOutputStream(s.getOutputStream());
            outS.write(arquivo);
            outS.flush();
            outS.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
