package com.example.demo;



import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;

public class Komunijacija extends Thread{

    Socket s;

    Server server;

    TextArea tx;
    BufferedReader in;
    PrintWriter out;

    public Komunijacija(Socket s,Server server, TextArea tx){

        this.server=server;
        this.s=s;
        this.tx=tx;

        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        start();
    }
    public  boolean isPalindrome(String text) {
        String cleanText = text.replaceAll("[^a-zA-Z]", "").toLowerCase();
        return cleanText.equals(new StringBuilder(cleanText).reverse().toString());
    }

    @Override
    public void run() {


        try {
            String dobitak="";
            dobitak = in.readLine();

            if(isPalindrome(dobitak)){
                tx.appendText("Klijent je poslao: ["+ dobitak + "] to JESTE Palindrom \n");
                out.println(dobitak);
            }
            else
            {
                tx.appendText("Klijent je poslao: ["+ dobitak + "] to NIJE Palindrom \n");
                out.println("Nisi poslao palindrom \n");

            }

            in.close();
            out.close();




        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}


