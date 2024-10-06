package org.andrieiew.plex.params;

import java.util.Scanner;

public class Dialog {
    private static Dialog instance;

    private Dialog() {
    }

    public static Dialog getInstance() {
        if (instance == null) {
            instance = new Dialog();
        }
        return instance;
    }
    public void show(String text) {
        System.out.println(text);
    }

    public String getString(String text) {
        System.out.println(text);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public int getInt(String text) {
        System.out.println(text);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
