import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class Cell extends JButton implements MouseListener {
	
	private int xPos, yPos, dimension, bombCounter;//private integers for the x position, y position, dimension and the bomb counter around a specific cell
	private static int numClick;//private static integer storing the number of Revealed buttons
	private boolean isBomb, flagged, revealed;//private boolean for if the cell is a bomb, if it has been flagged, and if it has been revealed
	
	//Constructor with parameters for the x position of the cell, the y position of the cell, and the dimensions
	public Cell(int x, int y, int d) {
		addMouseListener(this);//adding a mouse listener for each cell
		xPos = x;
		yPos = y;
		dimension = d;
		//each cell starts off as a default with everything as false and will later be changed
		setBomb(false);
		setFlagged(false);
		setRevealed(false);
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public boolean getBomb() {
		return isBomb;
	}
	
	public void setBomb(boolean b) {
		isBomb = b;
	}
	
	public int getBombCounter() {
		return bombCounter;
	}
	
	public boolean isRevealed() {
		return revealed;
	}
	
	public void setRevealed(boolean r) {
		revealed = r;
	}
	
	public boolean isFlagged() {
		return flagged;
	}
	
	public void setFlagged(boolean f) {
		flagged = f;
	}
	public static int getNumClicks(){
		return numClick;
	}
	public static void setNumClicks(int n){
		numClick=n;
	}
	
	//when a button has been pressed
	public void processClick() {
		//making sure that the button clicked hasn't been clicked
		if(isRevealed()==false){
			setRevealed(true);//set the cell to be revealed
			numClick++;//increase number of cells revealed by 1

			//if it was a bomb, set the cell icon to a bomb and call the loseGame method in the Minesweeper class
			if (getBomb()){
				setIcon(Minesweeper.cellBomb);
				Minesweeper.loseGame();
				repaint();
			}
			//if it wasn't a bomb, display the number of bombs around it and if there was none, call the blankCellClcked method in Minesweeper class in order to clear all blank spaces and then check for if user has won
			else{
				Minesweeper.setCellDisplay(Minesweeper.board[xPos][yPos]);
				if (getBombCounter() == 0){
					Minesweeper.blankCellClicked(Minesweeper.board[xPos][yPos]);		
				}
				Minesweeper.winGame();
			}
		}
	}
	//checking for the number of bombs around a cell
	public void checkSurroundings() {
		if (!getBomb()) {
			bombCounter = 0;
			if (!(xPos - 1 == -1 || yPos -1 == -1))
				if (Minesweeper.board[xPos - 1][yPos - 1].getBomb())
					bombCounter++;
			if (yPos - 1 != -1)
				if (Minesweeper.board[xPos][yPos - 1].getBomb())
					bombCounter++;
			if (!(xPos + 1 == dimension || yPos - 1 == -1))
				if (Minesweeper.board[xPos + 1][yPos - 1].getBomb())
					bombCounter++;
			if (xPos - 1 != -1)
				if (Minesweeper.board[xPos - 1][yPos].getBomb())
					bombCounter++;
			if (xPos + 1 != dimension)
				if (Minesweeper.board[xPos + 1][yPos].getBomb())
					bombCounter++;
			if (!(xPos - 1 == -1 || yPos + 1 == dimension))
				if (Minesweeper.board[xPos - 1][yPos + 1].getBomb())
					bombCounter++;
			if (yPos + 1 != dimension)
				if (Minesweeper.board[xPos][yPos + 1].getBomb())
					bombCounter++;
			if (!(xPos + 1 == dimension || yPos + 1 == dimension))
				if (Minesweeper.board[xPos + 1][yPos + 1].getBomb())
					bombCounter++;
		} 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//if user right clicks, check to see if its flagged already. If it isn't, flag it. If it is, unflag it
		if (e.getButton() == MouseEvent.BUTTON3 && !isRevealed()) {
			if (!isFlagged()) {
				setIcon(Minesweeper.cellFlagged);
				setFlagged(true);
			} else {
				setIcon(Minesweeper.cellDefault);
				setFlagged(false);
			}
			repaint();
		} 
		//if user did not right click on a cell, call theh processClick method in order to process the left click
		else {
			processClick();
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
