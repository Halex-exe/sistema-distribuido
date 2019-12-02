package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Server servidor2 = new Server(1020);
        servidor2.start();
        Server servidor3 = new Server(1030);
        servidor3.start();
        Server servidor4 = new Server(1040);
        servidor4.start();

        Servers servidor1 = new Servers(1010);

    }
}
