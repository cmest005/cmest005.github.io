import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
Carlos Mestre 1335543
COP 4338
The Matching Game  
*/

/*
This class is a LinkedList which is set up for use with the card matching game.
*/
public class LinkedListData 
{
    private Node head ;
    private int size ; 
    private Node pointer  ;    
    private Node tempHead  ;
    private int byteCount = 0 ; 
    private int byteReturnValue = 0 ;
    /*
    Create an empty linked list.    
    */
    public LinkedListData()
    {
        head = null ;
        size = 0 ; 
    }        
    
    /*
    Constructor to create a Linked list with an initial node    
    */    
    public LinkedListData(Node h)
    {
        head = h ; 
        size = 1 ; 
    }        
 
    /*
    Method to test if its true or false that the LinkedList is empty.    
    */    
    public boolean isEmpty()
    {
        if(size==0)
            return true ;
        else return false ; 
    }        
    
    /*
    Get method which returns the size of the LinkedList.    
    */    
    public int getSize()
    {
        return size ; 
    }
    
    /*
    Method which adds a node with the letter passed through as a parameter.   
    */    
    public void addNode(char c)
    {        
        Node temp = new Node(c) ;
        
        if(isEmpty())
        {
            head = temp ; 
            pointer = head ; 
        }
        else
        {            
            if(size == 6)
            {                
                head.setBottom(temp) ;
                pointer = temp ;                
                tempHead = pointer ;                               
            }
            else if(size %6 == 0)
            {
                tempHead.setBottom(temp);
                pointer = temp ; 
                tempHead = pointer ; 
            }    
            else 
            {                   
                while(pointer.getNext()!=null)
                {
                    pointer = pointer.getNext() ;
                }                   
                pointer.setNext(temp) ;            
            }                 
        }
        size++ ;
    }        
    
    /*
    Method which prints the linked list.  Used it to test the linked list was 
    working and properly connected.
    */
    public void printList()
    {
        Node first = head ; 
        Node tempPrint = new Node() ; 
        int count = 0 ; 
        System.out.println("List of letters: ") ;
        for(int i = 0 ; i < getSize() ; i++)
        {
            System.out.print(first.getLetter()+ "   ") ;
            first = first.getNext() ;
            count++ ; 
            if(count == 6)
            {
                first = head.getBottom() ;
                tempPrint = first ; 
                System.out.println() ;
            }    
            
            else if(count %6 == 0)
            {
                first = tempPrint.getBottom() ; 
                tempPrint = first ;
                System.out.println() ;
            }    
        }           
    }
    
    /*
    Method which prints the byte of each node.  Used for testing to make sure 
    the bytes were correct for the logic in the game.
    */     
    public void printBytes()
    {
        Node first = head ; 
        Node tempPrint = new Node() ; 
        int count = 0 ; 
        System.out.println("List of bytes: ") ;
        for(int i = 0 ; i < getSize() ; i++)
        {
            System.out.print(first.byte1+ "   ") ;
            first = first.getNext() ;
            count++ ; 
            if(count == 6)
            {
                first = head.getBottom() ;
                tempPrint = first ; 
                System.out.println() ;
            }    
            
            else if(count %6 == 0)
            {
                first = tempPrint.getBottom() ; 
                tempPrint = first ;
                System.out.println() ;
            }    
        }          
    }
    
    /*
    This method takes a linked list as a parameter and returns an array of the 
    letters in said linked lists shuffled.
    */
    public char[] returnCharArray(LinkedListData list)
    {
        Character[] listOfLetters = new Character[getSize()] ; 
        Node first = list.head ; 
        Node tempPrint = new Node() ; 
        int count = 0 ; 
        
        for(int i = 0 ; i < getSize() ; i++)
        {
            listOfLetters[i] = first.getLetter() ;
            first = first.getNext() ;
            count++ ; 
            if(count == 6)
            {
                first = head.getBottom() ;
                tempPrint = first ;                 
            }    
            
            else if(count %6 == 0)
            {
                first = tempPrint.getBottom() ; 
                tempPrint = first ;                
            }    
        }    
        
        List<Character> list1 = Arrays.asList(listOfLetters) ;
        Collections.shuffle(list1) ; 
        Object randomList[] = list1.toArray() ;
        
        char[] randomList1 = new char[36] ; 
        
        for(int i = 0 ; i < getSize() ; i++) 
        {
            char ch = randomList[i].toString().charAt(0) ; 
            randomList1[i] = ch ; 
        }   
        return randomList1 ; 
    }        
    
    /*
    This method takes a linked list as a parameter and uses the char array 
    shuffler to return a linked list that has been shuffled and ready for use.
    */
    public LinkedListData shuffledList (LinkedListData imported)
    {
        LinkedListData newList = new LinkedListData() ;
        char[] newListChar = returnCharArray(imported) ; 
        
        for(int i = 0 ; i < getSize() ; i++)
            newList.addNode(newListChar[i]) ; 
        
        return newList ; 
    }        
    
    /*
    Method which takes the card number, aka the position in the linked list, and
    returns the letter from that Node.
    */
    public char returnLetter(int cardNumber) 
    {
        Node first = head ; 
        Node tempPrint = new Node() ; 
        int count = 0 ; 
        char returningLetter = ' ' ; 
        
        for(int i = 0 ; i < getSize() ; i++)
        {
            if(count == cardNumber)
            {
                returningLetter = first.getLetter() ;
            }    
            
            first = first.getNext() ;
            count++ ; 
            if(count == 6)
            {
                first = head.getBottom() ;
                tempPrint = first ;                 
            }    
            
            else if(count %6 == 0)
            {
                first = tempPrint.getBottom() ; 
                tempPrint = first ;                
            } 
        }
        return returningLetter ;
    }
    
    /*
    Method which takes the card number as a parameter and changes that card's
    byte to 1.
    */
    public void changeByteTo1(int cardNumber)
    {
        Node first = head ; 
        Node tempPrint = new Node() ; 
        int count = 0 ; 
                
        for(int i = 0 ; i < getSize() ; i++)
        {
            if(count == cardNumber)
            {
                first.byte1 = 1 ;
            }    
            
            first = first.getNext() ;
            count++ ; 
            if(count == 6)
            {
                first = head.getBottom() ;
                tempPrint = first ;                 
            }    
            
            else if(count %6 == 0)
            {
                first = tempPrint.getBottom() ; 
                tempPrint = first ;                
            } 
        }    
    }        
    
    /*
    Method which takes the card number as a parameter and changes that card's
    byte to 2.
    */
    public void changeByteTo2(int cardNumber)
    {
        Node first = head ; 
        Node tempPrint = new Node() ; 
        int count = 0 ; 
                
        for(int i = 0 ; i < getSize() ; i++)
        {
            if(count == cardNumber)
            {
                first.byte1 = 2 ;
            }    
            
            first = first.getNext() ;
            count++ ; 
            if(count == 6)
            {
                first = head.getBottom() ;
                tempPrint = first ;                 
            }    
            
            else if(count %6 == 0)
            {
                first = tempPrint.getBottom() ; 
                tempPrint = first ;                
            } 
        }    
    }    
    
    /*
    Method which takes the card number as its parameter and returns the Node 
    which is at that position.
    */    
    public Node returnNode(int cardNumber)
    {
        Node first = head ; 
        Node tempPrint = new Node() ; 
        
        Node returnNode = new Node() ; 
        int count = 0 ; 
                
        for(int i = 0 ; i < getSize() ; i++)
        {
            if(count == cardNumber)
            {
                returnNode = first ; 
            }    
            
            first = first.getNext() ;
            count++ ; 
            if(count == 6)
            {
                first = head.getBottom() ;
                tempPrint = first ;                 
            }    
            
            else if(count %6 == 0)
            {
                first = tempPrint.getBottom() ; 
                tempPrint = first ;                
            } 
        }    
        return returnNode ; 
    }
    
    /*
    Recursive method which returns the amount of matched cards in the game.
    */          
    public int countMatchedPairs(Node temp)
    {
        if(temp == null)
        {
              throw new IllegalArgumentException("Empty List") ;
        }        
        
        if(temp.getByte() == 2)
        {
            byteCount++ ; 
        }    
        
        if(temp == returnNode(5))   
                temp = returnNode(6) ;
            else if(temp == returnNode(11))
                temp = returnNode(12) ; 
            else if(temp == returnNode(17))   
                temp = returnNode(18) ;
            else if(temp == returnNode(23))
                temp = returnNode(24) ;
            else if(temp == returnNode(29))   
                temp = returnNode(30) ;        
            else temp=temp.getNext() ; 

        if(temp!=returnNode(35))           
            countMatchedPairs(temp) ;

        return byteCount ;  
    }        
   
    public void changeByteCount()
    {
        byteCount = 0 ;
    }
    
    /*
    Get methid to return the head of the list.
    */
    public Node getHead()
    {
        return head ; 
    }        
    
    /*
    Method which returns the byte of the card that is given by the card's 
    number position.
    */
    public int getByte(int cardNumber)
    {
        Node first = head ; 
        Node tempPrint = new Node() ; 
        int count = 0 ; 
        int byteNum = 0 ;         
        for(int i = 0 ; i < getSize() ; i++)
        {
            if(count == cardNumber)
            {
                byteNum = first.byte1 ;
            }    
            
            first = first.getNext() ;
            count++ ; 
            if(count == 6)
            {
                first = head.getBottom() ;
                tempPrint = first ;                 
            }    
            
            else if(count %6 == 0)
            {
                first = tempPrint.getBottom() ; 
                tempPrint = first ;                
            } 
        }    
        return byteNum ; 
    }
    
    /*
    Print the size o the linked list.
    */
    public void printSize()
    {
        System.out.println("\n\nSize:  " + size) ;
    }        
     
    /*
    Node class
    */
    private class Node
    {
        private char letter ; 
        private int byte1 ; 
        private Node bottom ;
        private Node next ; 
        
        /*
        Create an empty node
        */
        Node()
        {
        }      
        
        /*
        Create a node with the letter passed as the parameter.
        */
        Node(char l)
        {
            letter = l ;             
            byte1 = 0 ;         
        }
        
        /*
        Create a node with the letter and the pointer to the next node.
        */
        Node(char l, Node n)
        {
            letter = l ; 
            byte1 = 0 ;  
            next = n ; 
        }  
        
        /*
        Get method to return the next node.
        */        
        public Node getNext()
        {
            return next ; 
        }        
        
        /*
        Set method to point to the next node.
        */
        public void setNext(Node n)
        {
            next = n ; 
        }        
        
        /*
        Get method which returns the letter of the node.
        */
        public char getLetter()
        {
            return letter ; 
        }      
        
        /*
        Set method which points to the bottom of the card.
        */
        public void setBottom(Node n)
        {
            bottom = n ; 
        }
        
        /*
        Get method which returns the node that is the bottom of he current node.
        */
        
        public Node getBottom()
        {
            return bottom ; 
        }
        
        /*
        Get method which returns the head.
        */
        public Node getHead()
        {
            return head ; 
        }     
        
        /*
        Get method which returns the byte of the node.
        */
        public int getByte()
        {
            return byte1 ; 
        }        
    }        
}
