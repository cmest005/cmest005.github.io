import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class MatchGameClient 
{
    public static void main(String[] args) throws IOException 
    {
    
        try (
            Socket gameSocket = new Socket("localhost", 4444);
            PrintWriter out = new PrintWriter(gameSocket.getOutputStream(), true);
            
                BufferedReader in = new BufferedReader(
                new InputStreamReader(gameSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            
            String fromServer;
            String fromUser;

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Coordinates from P2 " + fromServer);
                if (fromServer.equals("Bye."))
                    break;
                
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("My coordinates " + fromUser);
                    out.println(fromUser);
                }
            }
       
        } catch (IOException e) {
            System.err.println("Cannot connect to " +
                "localhost");
            System.exit(1);
        }
    
    
    }
}
