/*
 * Description: Minesweeper using swing 
 * Author: David Yu
 * Date: June 19, 2018
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class Minesweeper extends JFrame  implements ActionListener{
	public Container container = getContentPane();//content pane of the gui
	//public LayoutManager layoutMgr = new LayoutManager(); 
	
	public static int dimension, numOfMines;//public static integers storing the dimension of the board and the number of mines
	public static JPanel gamePanel;//JPanel and is where the buttons in the game are stored in
	public JPanel menuPanel;//JPanel where the reset button and JTextField will be shown
	public JButton reset = new JButton("Reset");//JButton for reset
	public JTextField timeCounter;//JTextField to display the time
	public static Cell[][] board;//Creating a 2D array of cells
	ImageIcon[] images = new ImageIcon[14];//Storing images
	static ArrayList<Players> highScore;
	
	public static int numClicks;//integer stores the number of buttons that have been revealed(used to check for win condition)

	public static int secPassed=0;//integer stores the number of seconds that have passed
	
	//Image icons
	public static ImageIcon CellBombWin;
	public static ImageIcon cellBombWin;
	public static ImageIcon CellBomb;
	public static ImageIcon cellBomb;
	public static ImageIcon CellFlagged;
	public static ImageIcon cellFlagged;
	public static ImageIcon CellDefault;
	public static ImageIcon cellDefault;
	public static ImageIcon CellRevealed0;
	public static ImageIcon CellRevealed1;
	public static ImageIcon CellRevealed2;
	public static ImageIcon CellRevealed3;
	public static ImageIcon CellRevealed4;
	public static ImageIcon CellRevealed5;
	public static ImageIcon CellRevealed6;
	public static ImageIcon CellRevealed7;
	public static ImageIcon CellRevealed8;
	public static ImageIcon cellRevealed0;
	public static ImageIcon cellRevealed1;
	public static ImageIcon cellRevealed2;
	public static ImageIcon cellRevealed3;
	public static ImageIcon cellRevealed4;
	public static ImageIcon cellRevealed5;
	public static ImageIcon cellRevealed6;
	public static ImageIcon cellRevealed7;
	public static ImageIcon cellRevealed8;
	
	
	//initialing ImageIcons and scaling them to size as they are all 600*600 pixels
	public static void initImageIcon(int dim){
		CellBomb = new ImageIcon("Images/Bomb.png");	
		cellBomb = new ImageIcon((CellBomb.getImage()).getScaledInstance(600 / dim, 600 / dim, java.awt.Image.SCALE_SMOOTH));
		CellFlagged = new ImageIcon("Images/Flag.png");	
		cellFlagged = new ImageIcon((CellFlagged.getImage()).getScaledInstance(600 / dim, 600 / dim, java.awt.Image.SCALE_SMOOTH));
		CellDefault = new ImageIcon("Images/Cell_Default.png");
		cellDefault = new ImageIcon((CellDefault.getImage()).getScaledInstance(600 / dim, 600 / dim, java.awt.Image.SCALE_SMOOTH));
		CellRevealed0 = new ImageIcon("Images/Revealed_0.png");
		cellRevealed0 = new ImageIcon((CellRevealed0.getImage()).getScaledInstance(600 / dim, 600 / dim, java.awt.Image.SCALE_SMOOTH));
		CellRevealed1 = new ImageIcon("Images/Revealed_1.png");
		cellRevealed1 = new ImageIcon((CellRevealed1.getImage()).getScaledInstance(600 / dim, 600 / dim, java.awt.Image.SCALE_SMOOTH));
		CellRevealed2 = new ImageIcon("Images/Revealed_2.png");
		cellRevealed2 = new ImageIcon((CellRevealed2.getImage()).getScaledInstance(600 / dim, 600 / dim, java.awt.Image.SCALE_SMOOTH));
		CellRevealed3 = new ImageIcon("Images/Revealed_3.png");
		cellRevealed3 = new ImageIcon((CellRevealed3.getImage()).getScaledInstance(600 / dim, 600 / dim, java.awt.Image.SCALE_SMOOTH));
		CellRevealed4 = new ImageIcon("Images/Revealed_4.png");
		cellRevealed4 = new ImageIcon((CellRevealed4.getImage()).getScaledInstance(600 / dim, 600 / dim, java.awt.Image.SCALE_SMOOTH));
		CellRevealed5 = new ImageIcon("Images/Revealed_5.png");
		cellRevealed5 = new ImageIcon((CellRevealed5.getImage()).getScaledInstance(600 / dim, 600 / dim, java.awt.Image.SCALE_SMOOTH));
		CellRevealed6 = new ImageIcon("Images/Revealed_6.png");
		cellRevealed6 = new ImageIcon((CellRevealed6.getImage()).getScaledInstance(600 / dim, 600 / dim, java.awt.Image.SCALE_SMOOTH));
		CellRevealed7 = new ImageIcon("Images/Revealed_7.png");
		cellRevealed7 = new ImageIcon((CellRevealed7.getImage()).getScaledInstance(600 / dim, 600 / dim, java.awt.Image.SCALE_SMOOTH));
		CellRevealed8 = new ImageIcon("Images/Revealed_8.png");
		cellRevealed8 = new ImageIcon((CellRevealed8.getImage()).getScaledInstance(600 / dim, 600 / dim, java.awt.Image.SCALE_SMOOTH));
	}
	
	//creating timer
	public static Timer timer = new Timer();
	TimerTask task = new TimerTask() {
        public void run() {
            secPassed++;
            timeCounter.setText("Time: "+secPassed);
            }
	};
	//main method, ask user to pick difficulty and showing the highscores for that difficulty
	public static void main(String args[]) throws IOException{
		dimension = difficulty();
		getScore();
		new Minesweeper();
	}
	
	//Constructor
	public Minesweeper() {
		//setting up the frame
		super("Minesweeper");
		setLayout(new BorderLayout());
		setSize(700, 700);  //827
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initImageIcon(dimension);//calling method to initialize all the image icons to be used later on
		
		reset.addActionListener(this);//adding an ActionListener on JButton
		
		//creating timeCounter text field to display time passed in seconds
		timeCounter = new JTextField(6);
		timeCounter.setEditable(false);
		timeCounter.setBackground(Color.BLACK);
		timeCounter.setForeground(Color.WHITE);
		
		menuPanel = new JPanel();//creating JPanel for the menu
		container.add(menuPanel,BorderLayout.NORTH);//Adding the menuPanel on the north of the BorderLayout of the container
		menuPanel.add(timeCounter,FlowLayout.LEFT);//Adding timeCounter to the menu panel on the left side of the flowlayout of the JPanel
		menuPanel.add(reset);//Adding reset button to the menu panel
		
		GridLayout gridLayout = new GridLayout(getDimension(),getDimension());//Creating the LayoutManager
		gamePanel = new JPanel(gridLayout);//making the gamePanel Layout a grid layout
		setBoard();//setting the board
		gamePanel.setBounds(200, 151, 600, 600);
		container.add(gamePanel);//adding the game panel on the container
		
		
		container.setVisible(true);
		
		setVisible(true);
		startTimer();//starting the timer
	}
	//setting difficulty by returning the dimensions and setting the number of mines 
	public static int difficulty(){
		String[] choices = { "Beginner", "Intermediate", "Expert"}; //String array choices, for the drop down selection menu
	    String input = (String) JOptionPane.showInputDialog(null, "Choose the difficulty level", "", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]); //integer input, stores the desired size of the game panel
			
	    if(input == "Beginner") //conditional statements, returns the right size of grid and set the amount of mines
	    { 
	    	numOfMines = 10;
	    	return 8;
	    }
	    else if(input == "Intermediate")
	    {
	    	numOfMines = 40;
	    	return 16;
	    }
	    else if(input == "Expert")
	    {
	    	numOfMines = 99;
	    	return 24;
	    }
	    else
		{
	    	return 0;
		}
	}
	//setting up the game board
	public static void setBoard() {
		board = new Cell[getDimension()][getDimension()];//initializing the board and the number of cells 	
		//for each coordinate
		for (int x = 0; x < getDimension(); x++)
			for (int y = 0; y < getDimension(); y++) {
				board[x][y] = new Cell(x,y,getDimension());//initializing the buttons
				board[x][y].setIcon(cellDefault);
				board[x][y].setOpaque(false);
				board[x][y].setContentAreaFilled(false);
				board[x][y].setBorderPainted(false);
				gamePanel.add(board[x][y]);//adding the JButtons all to the game panel
			}
		//placing mines
		boolean allMinesPlaced=false;//boolean checking if all the mines have been placed yet
		int counter = 0;//integer for counting the number of mines that have been placed
		while(!allMinesPlaced){
			int xPos = (int) (Math.random() * (getDimension() - 1) + 0.5);//getting random X value
			int yPos = (int) (Math.random() * (getDimension() - 1) + 0.5);//getting random Y value
			//if the coordinates do not already have a bomb, set a bomb and increase the counter by 1
			if (!board[xPos][yPos].getBomb()) {
				board[xPos][yPos].setBomb(true);
				counter++;
			}
			//once all the mines have been placed, make allMinesPlaced true and get out of while loop
			if(counter == numOfMines){
				allMinesPlaced=true;
			}
		}
		//Counting the number of bombs neighbouring each cell
		for (int x = 0; x < getDimension(); x++)
			for (int y = 0; y < getDimension(); y++)
				board[x][y].checkSurroundings();
	}
	

	
	
	  //Recursively check for blank cell around a specific cell
	
	public static void blankCellClicked(Cell cell) {
		// top left
		if ((!(cell.getXPos() - 1 == -1 || cell.getYPos() -1 == -1)) && (!Minesweeper.board[cell.getXPos() - 1][cell.getYPos() - 1].getBomb()) && !Minesweeper.board[cell.getXPos() - 1][cell.getYPos() - 1].isRevealed()) {
			Minesweeper.board[cell.getXPos() - 1][cell.getYPos() - 1].processClick();
			setCellDisplay(Minesweeper.board[cell.getXPos() - 1][cell.getYPos() - 1]);
		}
		// top middle
		if (((cell.getYPos() - 1 != -1) && (!Minesweeper.board[cell.getXPos()][cell.getYPos() - 1].getBomb())) && !Minesweeper.board[cell.getXPos()][cell.getYPos() - 1].isRevealed()) {
			Minesweeper.board[cell.getXPos()][cell.getYPos() - 1].processClick();
			setCellDisplay(Minesweeper.board[cell.getXPos()][cell.getYPos() - 1]);
		}
		// top right
		if ((!(cell.getXPos() + 1 == getDimension() || cell.getYPos() - 1 == -1)) && (!Minesweeper.board[cell.getXPos() + 1][cell.getYPos() - 1].getBomb()) && !Minesweeper.board[cell.getXPos() + 1][cell.getYPos() - 1].isRevealed()) {
			Minesweeper.board[cell.getXPos() + 1][cell.getYPos() - 1].processClick();
			setCellDisplay(Minesweeper.board[cell.getXPos() + 1][cell.getYPos() - 1]);
		}
		// middle left
		if ((cell.getXPos() - 1 != -1) && (!Minesweeper.board[cell.getXPos() - 1][cell.getYPos()].getBomb()) && !Minesweeper.board[cell.getXPos() - 1][cell.getYPos()].isRevealed()) {
			Minesweeper.board[cell.getXPos() - 1][cell.getYPos()].processClick();
			setCellDisplay(Minesweeper.board[cell.getXPos() - 1][cell.getYPos()]);
		}
		// middle right
		if ((cell.getXPos() + 1 != getDimension()) && (!Minesweeper.board[cell.getXPos() + 1][cell.getYPos()].getBomb()) && !Minesweeper.board[cell.getXPos() + 1][cell.getYPos()].isRevealed()) {
			Minesweeper.board[cell.getXPos() + 1][cell.getYPos()].processClick();
			setCellDisplay(Minesweeper.board[cell.getXPos() + 1][cell.getYPos()]);
		}
		// bottom left
		if ((!(cell.getXPos() - 1 == -1 || cell.getYPos() + 1 == getDimension())) && (!Minesweeper.board[cell.getXPos() - 1][cell.getYPos() + 1].getBomb()) && !Minesweeper.board[cell.getXPos() - 1][cell.getYPos() + 1].isRevealed()) {
			Minesweeper.board[cell.getXPos() - 1][cell.getYPos() + 1].processClick();
			setCellDisplay(Minesweeper.board[cell.getXPos() - 1][cell.getYPos() + 1]);
		}
		//bottom middle
		if ((cell.getYPos() + 1 != getDimension()) && (!Minesweeper.board[cell.getXPos()][cell.getYPos() + 1].getBomb()) && !Minesweeper.board[cell.getXPos()][cell.getYPos() + 1].isRevealed()) {
			Minesweeper.board[cell.getXPos()][cell.getYPos() + 1].processClick();
			setCellDisplay(Minesweeper.board[cell.getXPos()][cell.getYPos() + 1]);
		}
		// bottom right
		if ((!(cell.getXPos() + 1 == getDimension() || cell.getYPos() + 1 == getDimension())) && (!Minesweeper.board[cell.getXPos() + 1][cell.getYPos() + 1].getBomb()) && !Minesweeper.board[cell.getXPos() + 1][cell.getYPos() + 1].isRevealed()) {
			Minesweeper.board[cell.getXPos() + 1][cell.getYPos() + 1].processClick();	
			setCellDisplay(Minesweeper.board[cell.getXPos() + 1][cell.getYPos() + 1]);
		}
		
	}
	//Sets the icon of the button to the number of bombs neighbouring the button
	public static void setCellDisplay(Cell cell) {
		if (!cell.getBomb()) {
			switch (cell.getBombCounter()) {
			case 0 : cell.setIcon(cellRevealed0);
					 break;
			case 1 : cell.setIcon(cellRevealed1);
			 		 break;
			case 2 : cell.setIcon(cellRevealed2);
			 		 break;
			case 3 : cell.setIcon(cellRevealed3);
			 		 break;
			case 4 : cell.setIcon(cellRevealed4);
			 		 break;
			case 5 : cell.setIcon(cellRevealed5);
			 		 break;
			case 6 : cell.setIcon(cellRevealed6);
			 	 	 break;
			case 7 : cell.setIcon(cellRevealed7);
					 break;
			case 8 : cell.setIcon(cellRevealed8);
			 		 break;
			default: break;
			}
		}
		gamePanel.repaint();

	}
	
	
	//checking for if user lost by checking if they clicked on a bomb
	public static void loseGame() {
		for (int x = 0; x < getDimension(); x++)
			for (int y = 0; y < getDimension(); y++) {
				if (board[x][y].getBomb()) {
					board[x][y].setIcon(cellBomb);
					
				}
			}
		JOptionPane.showMessageDialog(Minesweeper.gamePanel, "You Lose!\nYou Hit A Bomb...");
		int s = JOptionPane.showConfirmDialog(Minesweeper.gamePanel, "Would You Like To Play Again?");//Asking if user wants to play again
		//if they click yes, reset everything and play again
		if (s == 0) {
			gamePanel.removeAll();
			board = null;
			setBoard();
			gamePanel.revalidate();
			gamePanel.repaint();
			secPassed=0;
			Cell.setNumClicks(0);
		} 
		//if they click no, terminate program
		else{
			System.exit(0);
		}
	}
	//checking for if user won by checking if the number of revealed buttons is equal to the number of buttons that do not contain mines
	public static void winGame(){
		if(getDimension()*getDimension()-numOfMines== Cell.getNumClicks()){
			int time = getSeconds();//stores seconds passed
			timer.cancel();//stops timer
			JOptionPane.showMessageDialog(Minesweeper.gamePanel, "You Win!");
			String name = JOptionPane.showInputDialog("Enter your name");
			makeHighscores(time,name);
			getScore();
			JOptionPane.showMessageDialog(Minesweeper.gamePanel, "Thanks for playing!");
			System.exit(0);
		}
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		//if user clicks reset, reset everything
		if(event.getSource().equals(reset)){
			gamePanel.removeAll();
			board = null;
			setBoard();
			gamePanel.revalidate();
			gamePanel.repaint();
			secPassed=0;
			Cell.setNumClicks(0);
		}
		
	}
	//starting the timer
	public void startTimer()
    {
        timer.scheduleAtFixedRate(task,1000,1000);//every 1000 miliseconds or 1 second, increase by 1 second
    }
	private static int getDimension(){
		return dimension;
	}
	public static int getSeconds(){
		return secPassed;
	}
	public static void getScore() //getScore method, get the high score of the difficulty for that mode
	{
		String line;//String to read the lines
		highScore = new ArrayList<Players>();//instance of an the players object, making it an array list as the array size will change depending on how many people have won
		String name;//String storing the name of the user once they win 
		int time;//time in seconds it took for user to win
		
		if(dimension == 8) //conditional statement, for the Beginner mode
		{
			String filePath = "easy.txt";
			
			try{
				IO.openInputFile(filePath);
				 line = IO.readLine();
				 //repeats until it reaches last item
				 while(line!=null){
					 int marker = line.indexOf('/');//Separates the name and the time
						name = line.substring(0, marker);//Name is from the start to the /
						time = Integer.parseInt(line.substring(marker+1));//Time is after the/
						highScore.add(new Players(name, time));//adds to arraylist
						line = IO.readLine();
				 }
				 IO.closeInputFile();
				 
				 Collections.sort(highScore);//sorts the times 
				 
				 JOptionPane.showMessageDialog(null,"HIGHSCORES\n\n1. "+ highScore.get(highScore.size()-1).toString()+"\n2. "+ highScore.get(highScore.size()-2).toString()+"\n3. "
                         + highScore.get(highScore.size()-3).toString()+"\n4. "+ highScore.get(highScore.size()-4).toString()+"\n5. "+ highScore.get(highScore.size()-5).toString());
			}catch(IOException e){}
			
		}
		else if(dimension == 16) //conditional statement, for the Intermediate mode
		{
			String filePath = "intermediate.txt";
			
			try{
				IO.openInputFile(filePath);
				 line = IO.readLine();
				 //repeats until it reaches last item
				 while(line!=null){
					 int marker = line.indexOf('/');//Separates the name and the item
						name = line.substring(0, marker);//Name is from the start to the /
						time = Integer.parseInt(line.substring(marker+1));//Time is after the /
						highScore.add(new Players(name, time));//adds to arraylist
						line = IO.readLine();
				 }
				 IO.closeInputFile();
				 
				 Collections.sort(highScore);//sorts the times
				 
				 JOptionPane.showMessageDialog(null,"HIGHSCORES\n\n1. "+ highScore.get(highScore.size()-1).toString()+"\n2. "+ highScore.get(highScore.size()-2).toString()+"\n3. "
                         + highScore.get(highScore.size()-3).toString()+"\n4. "+ highScore.get(highScore.size()-4).toString()+"\n5. "+ highScore.get(highScore.size()-5).toString());
			}catch(IOException e){}
			
		}
		else if(dimension == 24) //conditional statement, for the Expert mode
		{
			String filePath = "expert.txt";
			
			try{
				IO.openInputFile(filePath);
				 line = IO.readLine();
				 //repeats until it reaches last item
				 while(line!=null){
					 int marker = line.indexOf('/');//Separates the name and the item
						name = line.substring(0, marker);//Name is from the start to the /
						time = Integer.parseInt(line.substring(marker+1));//Time is after the /
						highScore.add(new Players(name, time));//adds to arraylist
						line = IO.readLine();
				 }
				 IO.closeInputFile();
				 
				 Collections.sort(highScore);//sorts the times
				 
				 JOptionPane.showMessageDialog(null,"HIGHSCORES\n\n1. "+ highScore.get(highScore.size()-1).toString()+"\n2. "+ highScore.get(highScore.size()-2).toString()+"\n3. "
                         + highScore.get(highScore.size()-3).toString()+"\n4. "+ highScore.get(highScore.size()-4).toString()+"\n5. "+ highScore.get(highScore.size()-5).toString());
			}catch(IOException e){}
		}
		
		
		
		
	}
	
	//Making the highscore by setting the username and the time it took
	private static void makeHighscores(int s, String n){
		if(dimension == 8){//conditional statement, for the Beginner mode
			String filePath = "easy.txt";
				try{
					IO.appendOutputFile(filePath);
					IO.openInputFile(filePath);
					IO.println(n+"/"+s);//writing in the new highscore
					IO.closeOutputFile();
				}catch(IOException e){}
        }
		if(dimension == 16){//conditional statement, for the Intermediate mode
	        String filePath = "intermediate.txt";
	            try{
	                IO.appendOutputFile(filePath);
	                IO.openInputFile(filePath);
	                IO.println(n+"/"+s);//writing in the new highscore
	                IO.closeOutputFile();
	            }catch(IOException e){}
	    }
		if(dimension == 24){//conditional statement, for the Expert mode
	        String filePath = "expert.txt";
	            try{
	                IO.appendOutputFile(filePath);
	                IO.openInputFile(filePath);
	                IO.println(n+"/"+s);//writing in the new highscore
	                IO.closeOutputFile();
	            }catch(IOException e){}
	    }
	}

}
