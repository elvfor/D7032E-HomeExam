import java.net.Socket;

import network.Client;
import network.Server;
import java.net.ServerSocket;

public class BoomerangGame{

    public static void main(String[] args) {
        try{
            connectionSetup(args);
        } catch(Exception e) {

        }
    }
    public static void connectionSetup(String[] params) throws Exception{
        //Requirement 1, 2-4 players
		if(params.length == 2 && ((Integer.valueOf(params[0]).intValue()+Integer.valueOf(params[1]).intValue())>=2) && ((Integer.valueOf(params[0]).intValue()+Integer.valueOf(params[1]).intValue())<=4)) {
			Server server = new network.Server(2048);
            for(i=0 ; i <params[0] ; i++){
                Socket connectionSocket = server.accept();
                
            }
            //this.initGame(Integer.valueOf(params[0]).intValue(), Integer.valueOf(params[1]).intValue());
		} else if(params.length == 1) {
			Client client = new network.Client(params[0]);
		} else {
			System.out.println("This game is for a total of 2-4 players/bots");
			System.out.println("Server syntax: java BoomerangAustralia numPlayers numBots");
			System.out.println("Client syntax: IP");
		}
    }
}

