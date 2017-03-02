import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*
Carlos Mestre 1335543
COP 4338
The Matching Game  
*/

/*
Tester class which retrieves the letters from a txt file, creates an array in 
order to shuffle the letters, creates a linked list with the array, and passes 
it to the GUI to play the game.
 */
public class LLTester 
{
    public static void main(String[ ] args) throws IOException
    {
        LinkedListData list = new LinkedListData() ;
        Scanner letters = new Scanner( new File ("letters.txt")) ; 
 
        while(letters.hasNext())
        {
            String l = letters.next() ; 
            char ch = l.charAt(0) ;  
            list.addNode(ch) ;            
        }       
        //list.printList() ;
        //list.printSize() ; 
        char[] tester = list.returnCharArray(list) ;     
        LinkedListData newList = new LinkedListData() ; 
        
        for(int i = 0 ; i < 36 ; i++)
            newList.addNode(tester[i]) ;
         
        GameGUI test = new GameGUI(newList) ;
        //newList.printList() ;
        //newList.printSize() ;      
    }       
}
