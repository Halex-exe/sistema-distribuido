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
        System.out.println("Server started " + this.getName()+" porta "+ this.porta);

        socket = server.accept();

        System.out.println("Client accepted " + this.getName()+ " " + this.porta);

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 /* or some other number */];
        int numRead;

        while((numRead = in.read(buffer,0,1024)) > 0) {
            baos.write(buffer, 0, numRead);
        }
        this.setResult(baos.toByteArray());

        System.out.println(this.getName()+ " " + this.porta + " adicionou!");

        dowload();
    }

    public byte[] getResult() {
        return result;
    }

    public void setResult(byte[] result) {
        this.result = result;
    }

    public void dowload() throws IOException {

        System.out.println(this.getName() + " " + (this.porta) + " caiu no download" + " e passou a ser a " + (this.porta + 1));

        ServerSocket serverr = new ServerSocket(porta + 1);
        Socket sockett = serverr.accept();
        InputStream inn = new DataInputStream(sockett.getInputStream());
        OutputStream outt = new DataOutputStream(sockett.getOutputStream());


        outt.write(this.getResult());
        outt.flush();

        outt.close();
        inn.close();
        sockett.close();
    }

}
