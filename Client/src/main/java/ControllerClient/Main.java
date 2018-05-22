package ControllerClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new ClientController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
