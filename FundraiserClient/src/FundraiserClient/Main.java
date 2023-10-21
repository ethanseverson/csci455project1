package FundraiserClient;
import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws Exception {
        String sentence;
        String response;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 6900); // Replace with your server's address and port

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        System.out.println("Connecting to server...");
        System.out.println("Type exit or quit at any time to disconect.");

        while (true) {
            response = inFromServer.readLine();
            if (response == null) {
                break; // Server disconnected
            }
            
            if ("<<READY>>".equals(response)) {
                System.out.print(">> ");
                sentence = inFromUser.readLine();
                if (sentence.equalsIgnoreCase("exit") || sentence.equalsIgnoreCase("quit")) {
                    System.out.println("Disconnected from server.");
                    break;
                }
                outToServer.writeBytes(sentence + '\n');
                outToServer.flush();
            } else {
                System.out.println(response);
            }
        }

        clientSocket.close();
    }
}
