package ltu.player.communication;

import java.util.Scanner;

public class LocalPlayerCommunication implements IPlayerCommunication {
    private Scanner scanner;

    public LocalPlayerCommunication() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String receiveInput() {
        return scanner.nextLine();
    }

}
