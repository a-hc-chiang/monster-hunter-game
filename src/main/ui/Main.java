package ui;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        try {
            new ConsoleHandlerGui();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
