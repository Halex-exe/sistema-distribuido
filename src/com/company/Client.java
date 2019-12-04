package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner; // 1. importando a classe Scanner

public class Client {

    static Socket s;
    static OutputStream outS;
    static InputStream inS;
    static byte[] bFile;
    static int tamanhoMax = 4194304;


    public static void enviar() {
        try {
            conectar(1010);
            outS.write(bFile);
            outS.flush();

            outS.close();
            inS.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void baixar() { //ler
        try {
            conectar(1011);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 /* or some other number */];
            int numRead;

            while ((numRead = inS.read(buffer, 0, 1024)) > 0) {
                baos.write(buffer, 0, numRead);
            }
            byte result[] = baos.toByteArray();

            try (FileOutputStream fos = new FileOutputStream("/Users/alexandre/Desktop/Arquivo/Dowload/arquivo")) {
                fos.write(result);
            }
            System.out.println("Break");
            //
            outS.close();
            inS.close(); //
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void conectar(int porta) {
        try {

            s = new Socket("localhost", porta);
            outS = new DataOutputStream(s.getOutputStream());
            inS = new DataInputStream(s.getInputStream());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        Scanner ler = new Scanner(System.in);
        int menu = 10;
        String arquivoDir = null;

        while (menu != 0) {
            menu = 10;
            System.out.println("Cliente MENU");
            System.out.println("Digite 0 para sair.");
            System.out.println("Digite 1 para enviar o arquivo.");
            System.out.println("Digite 2 para baixar o arquivo.");
            menu = ler.nextInt();
            ler.nextLine();

            if (menu == 1) {

                System.out.println("Digite o caminho do arquivo que deseja fazer Upload");
                arquivoDir = ler.nextLine();
                File file = new File(arquivoDir);
                bFile = new byte[(int) file.length()];
                FileInputStream fileInputStream = null;

                try {
                    fileInputStream = new FileInputStream(file);
                    fileInputStream.read(bFile);
                    fileInputStream.close();

                    //System.out.println(bFile.length);
                    if (bFile.length <= tamanhoMax){
                        enviar();
                    }else{
                        System.out.println("Arquivo é maior que 4MB");
                        menu = 0;
                    }


                } catch (Exception e) {
                    System.out.println(e);
                }


            } else {
                if (menu == 2) {
                    System.out.println("Baixando arquivo!");
                    baixar();
                    menu = 0;
                    System.out.println("Concluido!");
                } else {
                    System.out.println("Opção inválida");
                }
            }
        }

    }
}
