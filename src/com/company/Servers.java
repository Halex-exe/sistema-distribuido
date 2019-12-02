package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servers {

    private int porta;
    private Socket socket = null;
    private ServerSocket server = null;
    private InputStream in = null;
    private OutputStream out = null;
    static byte pt1[] = null;

    public Servers(int port) throws IOException {

        server = new ServerSocket(port);
        System.out.println("Principal esperando");
        socket = server.accept();
        System.out.println("Principal conectou");

        in = new DataInputStream(socket.getInputStream());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 /* or some other number */];
        int numRead;

        while ((numRead = in.read(buffer, 0, 1024)) > 0) {
            baos.write(buffer, 0, numRead);
        }

        byte result[] = baos.toByteArray();

        byte[] parte1 = new byte[result.length / 4]; //ficar aqui.
        pt1 = parte1;

        byte[] parte2 = new byte[result.length / 4]; //mandar para o 1020.
        byte[] parte3 = new byte[result.length / 4]; //mandar para o 1030.
        byte[] parte4 = new byte[result.length / 4]; //mandar para o 1040.

        System.arraycopy(result, 0, parte1, 0, parte1.length);
        System.arraycopy(result, parte1.length, parte2, 0, parte2.length);
        System.arraycopy(result, parte1.length + parte2.length, parte3, 0, parte3.length);
        System.arraycopy(result, parte1.length + parte1.length + parte3.length, parte4, 0, parte4.length);


        enviar(1020, parte2);
        enviar(1030, parte3);
        enviar(1040, parte4);

        esperar();
    }

    public void esperar() throws IOException {

        server = new ServerSocket(1011);
        socket = server.accept();

        //
        byte[] pt2 = baixar(1021);
        byte[] pt3 = baixar(1031);
        byte[] pt4 = baixar(1041);
        //
        byte result2[] = new byte[pt1.length + pt2.length + pt3.length + pt4.length];

        System.arraycopy(pt1, 0, result2, 0, pt1.length);
        System.arraycopy(pt2, 0, result2, pt1.length, pt2.length);
        System.arraycopy(pt3, 0, result2, pt1.length + pt2.length, pt3.length);
        System.arraycopy(pt4, 0, result2, pt1.length + pt2.length + pt3.length, pt4.length);

        out = new DataOutputStream(socket.getOutputStream());


        out.write(result2);
        out.flush();
        out.close();
        socket.close();
    }

    public void enviar(int porta, byte[] arquivo) { //enviar o arquivo para os servidores.

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

    public byte[] baixar(int porta) throws IOException {

        Socket s = new Socket("localhost", porta);
        DataInputStream inS = new DataInputStream(s.getInputStream());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024 /* or some other number */];
        int numRead;

        while ((numRead = inS.read(buffer, 0, 1024)) > 0) {
            baos.write(buffer, 0, numRead);
        }
        byte result[] = baos.toByteArray();
        return result;
    }
}
