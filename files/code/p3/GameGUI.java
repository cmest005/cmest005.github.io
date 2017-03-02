import java.awt.* ; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
        
/*
Carlos Mestre 1335543
COP 4338
The Matching Game  
*/

/*
The Matching Game GUI.  This is a card game between two players.  The cards are 
initially face down and scrambled.  Your turn consists of flipping two cards 
to see if they match.  If they do, you receive a point, and then the next player
goes.  
*/
public class GameGUI implements ActionListener
{
    private JFrame window = new JFrame();
    
    private JPanel topPanel = new JPanel() ; 
    private JPanel bottomPanel = new JPanel() ;
    private JPanel gamePanel = new JPanel() ;
    
    private JButton startButton ;
    private JButton matchingPairs ;
    private JButton exitButton ;    
    private JButton[][] gameButtons ; 
 
    private LinkedListData list ; 

    private JLabel player1 = new JLabel(":Player 1") ; 
    private JLabel player2 = new JLabel("Player 2:") ;
    private JLabel score1 = new JLabel() ;
    private JLabel score2 = new JLabel() ;
    private JLabel turn = new JLabel("               Player's turn = ") ;
    private JLabel turnNum = new JLabel() ;
    
    private int b1Ti = 0 ; 
    private int b1Tj = 0 ;
    private int b2Ti = 0 ;
    private int b2Tj = 0 ;
    private int cardCounter = 0 ;
    private int player1score = 0 ; 
    private int player2score = 0 ; 
    private int counter ;
    private int counterCard1 = 0 ; 
    private int counterCard2 = 0 ; 
    private int gamePairCounter = 0 ; 
    
    private char first = ' ' ;
    private char second = ' ' ;
    
    private boolean scoreCounter1 ;
    private boolean gameStarted = false ; 

    /*
    The GameGUI is a class which creates the board which you will play on.  It 
    uses a linked list of nodes consisting of letters to represent cards.
    */
    public GameGUI(LinkedListData listLL)
    {                 
        list = listLL ; 
        randomStart() ; 
                
        window.setTitle("Welcome to the Matching Game") ;         
        window.setSize(1000,900) ; 
        
        topPanel(); 
        bottomPanel();
             
        window.add(topPanel,BorderLayout.NORTH) ;        
        window.add(bottomPanel,BorderLayout.SOUTH) ; 
        window.add(gamePanel,BorderLayout.CENTER) ;       
        
        window.setVisible(true) ;
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;     
    }

    /*
    This is the top panel of the game.  It has a bottons to start, exit, and 
    give the amount of matching pairs which have been clicked.
    */
    private void topPanel()
    {
        topPanel.setSize(1000, 100) ;
        topPanel.setLayout(new FlowLayout()) ;
        topPanel.setBorder(BorderFactory.createTitledBorder("Game Menu")) ;
        
        startButton = new JButton("Start") ;
        startButton.addActionListener(this) ;
        matchingPairs = new JButton("Matching Pairs") ;
        matchingPairs.addActionListener(this) ;
        exitButton = new JButton("Exit") ; 
        exitButton.addActionListener(this) ;
 
        topPanel.add(score1) ;
        topPanel.add(player1) ;
        topPanel.add(startButton) ; 
        topPanel.add(matchingPairs) ; 
        topPanel.add(exitButton) ; 
        topPanel.add(player2) ;
        topPanel.add(score2) ;
        topPanel.add(turn) ; 
        topPanel.add(turnNum) ;
    }

    /*
    A bottom panel which is used to give the game creator's name and ID #.
    */
    private void bottomPanel() 
    {
        bottomPanel.setSize(1000, 100) ;
        bottomPanel.setLayout(new FlowLayout()) ; 
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Carlos "
                + "Mestre 1335543")) ; 
    }

    /*
    The game panel which consists of the deck of cards turned over.
    */
    private void gamePanel() 
    {
        gamePanel.setSize(800,800) ;
        gamePanel.setBackground(Color.black) ;
        GridLayout grid = new GridLayout(6,6,6,6) ; 
        gamePanel.setLayout(grid) ;        
        gameButtons = new JButton[6][8] ; 
              
        for (int i=0; i<6; i++)
            for (int j=0; j<6; j++)
            {   
                gameButtons[i][j] = new JButton("UNMATCHED") ;
                gamePanel.add(gameButtons[i][j]) ;                
                gameButtons[i][j].addActionListener(this) ;    
            }        
    }      
    
    /*
    Coin flip to decide which player goes first.
    */
    private void randomStart()
    {
        Random random = new Random() ; 
        int number = random.nextInt(2) ;
        
        if(number == 0)
        {
            scoreCounter1 = true ;
            turnNum.setText("1") ;
        }    
        else 
        {
            scoreCounter1 = false ;
            turnNum.setText("2") ;
        } 
    }        
    
    /*
    This is where the result of clicking the buttons takes place.  The game's 
    logic is here.
    */
    public void actionPerformed(ActionEvent e)
    {
        if(exitButton == e.getSource())
        {
            System.exit(0) ;
        }   
        
        if(matchingPairs == e.getSource())
        {
            if(gameStarted)
            {        
                LinkedListData testBytes = list ; 
                System.out.println("Matching Pairs = " 
                    + testBytes.countMatchedPairs(testBytes.getHead())/2) ;
                list.changeByteCount() ;
            }
            else 
                JOptionPane.showMessageDialog(null, "Game has not started") ;
        }    
        
        if(startButton == e.getSource())
        {
            if(gameStarted == false)
            {    gamePanel() ; 
                window.revalidate() ;
                window.validate() ;
                gameStarted = true ; 
            }    
        }    
       
        counter = 0; 
  
        for (int i=0; i<6; i++)
            for (int j=0; j<6; j++)
            {
                if(gameButtons[i][j] == e.getSource())
                {
                    cardCounter++ ;
                    gameButtons[i][j].setText(""+list.returnLetter(counter)) ;
                    
                    if(cardCounter == 1)
                    {    
                        first = list.returnLetter(counter) ;
                        list.changeByteTo1(counter) ;
                        counterCard1 = counter ;
                        b1Ti = i ;
                        b1Tj = j ;
                        System.out.println("first card: "+ first) ;              
                    }
                    
                    if(cardCounter == 2)
                    {
                        second = list.returnLetter(counter) ;
                        list.changeByteTo1(counter) ;
                        b2Ti = i ; 
                        b2Tj = j ;
                        System.out.println("second card: " + second + "\n") ; 
                        cardCounter++ ;     
                        counterCard2 = counter ; 
                        
                        if(b1Ti == b2Ti && b1Tj==b2Tj)
                        {
                            cardCounter = 0 ;
                            JOptionPane.showMessageDialog(null, "Nice try, "
                                    + "cant click same card twice ") ; 
                            gameButtons[b1Ti][b1Tj].setText("UNMATCHED") ;
                        }    
                        
                        if(scoreCounter1==true)
                            turnNum.setText("2") ; 
                        else
                            turnNum.setText("1") ;
                        
                        scoreCounter1 = !scoreCounter1 ; 
                    }      
                    
                    if(cardCounter == 3)
                    {
                        if(first==second)
                        {                            
                            gameButtons[b1Ti][b1Tj].setEnabled(false) ;
                            gameButtons[b2Ti][b2Tj].setEnabled(false) ;
                            gameButtons[b1Ti][b1Tj].setText("Matching!") ;
                            gameButtons[b2Ti][b2Tj].setText("Matching!") ;
                            System.out.println("matched\n") ;    
                            list.changeByteTo2(counterCard1) ;
                            list.changeByteTo2(counterCard2) ;
                            gamePairCounter++ ; 
                               
                            if(scoreCounter1==false)    
                            {   
                                player1score++ ;
                                score1.setText(Integer.toString(player1score)) ;
                            }
                            
                            if(scoreCounter1==true)   
                            {
                                player2score++ ;
                                score2.setText(Integer.toString(player2score)) ;
                            }
                            
                            if(gamePairCounter == 18)
                            {
                                if(player1score > player2score)                             
                                    JOptionPane.showMessageDialog(null, "The "
                                            + "Winner is:  Player 1") ;  
                                else if(player1score < player2score)
                                    JOptionPane.showMessageDialog(null, "The "
                                            + "Winner is:  Player 2") ;
                                else 
                                    JOptionPane.showMessageDialog(null, "The "
                                            + "Winner is:  a tie!") ;
                            } 
                        }    
                        else
                        {
                            gameButtons[b1Ti][b1Tj].setEnabled(true) ;
                            gameButtons[b2Ti][b2Tj].setEnabled(true) ;
                            gameButtons[b1Ti][b1Tj].setText("UNMATCHED") ;
                            gameButtons[b2Ti][b2Tj].setText("UNMATCHED") ;
                        }                   
                    cardCounter = 0 ; 
                    }        
                }       
                counter++ ;                 
            }               
    }   
}
