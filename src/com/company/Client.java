package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner; // 1. importando a classe Scanner

public class Client {

    static Socket s;
    static OutputStream outS;
    static InputStream inS;
    static byte[] bFile;


    public static void enviar(){
        try {
            outS.write(bFile);
            outS.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void baixar(){

    }


    public static void conectar() {
        try {
            s = new Socket("localhost", 1010);

            outS = new DataOutputStream(s.getOutputStream());
            inS = new DataInputStream(s.getInputStream());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        conectar();
        Scanner ler = new Scanner(System.in);
        int menu = 10;
        String arquivoDir = null;

        while (menu != 0){
            menu = 10;
            System.out.println("Cliente MENU");
            System.out.println("Digite 0 para sair.");
            System.out.println("Digite 1 para enviar o arquivo.");
            System.out.println("Digite 2 para baixar o arquivo.");
            menu = ler.nextInt();
            ler.nextLine();

            if (menu == 1){

                System.out.println("Digite o caminho do arquivo que deseja fazer Upload");
                arquivoDir = ler.nextLine();
                File file = new File(arquivoDir);
                bFile = new byte[(int) file.length()];
                FileInputStream fileInputStream = null;

                try {
                    fileInputStream = new FileInputStream(file);
                    fileInputStream.read(bFile);
                    fileInputStream.close();
                    enviar();

                }catch(Exception e){
                    System.out.println(e);
                }


            }else{
                if (menu == 2){

                }else{
                    System.out.println("Opção inválida");
                }
            }
        }

    }
}
