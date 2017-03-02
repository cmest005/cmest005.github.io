
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andy
 */
public class MatchGameServer 
{
    public static void main(String[] args) throws IOException {
        


        try ( 
            ServerSocket serverSocket = new ServerSocket(4444);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
        
            String inputLine, outputLine;
            
            // Initiate conversation with client
           String answer = new String();
            outputLine = answer;
            out.println(outputLine);
            
            answer = in.readLine() ; 
            
            inputLine = answer ; 
            out.write(inputLine);
            
            
/*
            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
 */       } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + 4444 + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
    
    
    
}
