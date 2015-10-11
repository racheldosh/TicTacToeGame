//TicTacToeCanvas.java
//CS201 Final Project
//Rachel Collins
//Jen Johnson

import java.applet.*;
import java.awt.*;
import java.awt.Event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")

public class TicTacToeCanvas extends Canvas implements MouseListener {

	//instance variables
	int border=20;
	int [][] grid=new int[3][3];
		// creates internal grid
	int x;
	int y;
		// coordinates of mouse click
	boolean player = true;
		// when player is true, X's turn
		// when player is false, O's turn (computer)
	int status=0;
		// if status is 0, no one has won yet, continue playing
		// if status is 1, X has won, do not allow further moves
		// if status is 2, O has won, do not allow further moves
		// if status is 3, No one wins, do not allow further moves
	TicTacToe parent;
	int mode = 0;
		// if mode is 0, it's in 2 Player mode
		// if mode is 1, it's in computer mode (EASY)
		// if mode is 2, it's in computer mode (HARD)
	int pOnePts = 0;
	int pTwoPts = 0;
		// keeps track of each player's points
	
	
	public TicTacToeCanvas(TicTacToe t) {
		// constructor; allows access
		parent = t;
		addMouseListener(this);
	}
	
	
	public void paint(Graphics g){
		// draws the board after each mouse click
		Dimension d=getSize();
		int width=(d.width-border*2)/3;
		int height=(d.height-border*2)/3;
		
		g.drawLine(width+border, border, width+border, d.height-border);
		g.drawLine(2*width+border, border, 2*width+border, d.height-border);
		g.drawLine(border, height+border, d.width-border, height+border);
		g.drawLine(border, d.height-border-height, 
					d.width-border, d.height-border-height);
			// draws the grid
		
		if (status==1){
			g.setFont(new Font("TimesRoman", Font.BOLD, 2*width/3));
			g.drawString("X wins!", width / 2, d.height/2);
			parent.applause.play();
			pOnePts += 5;
			parent.pointsLabel.setText("Player1: " + pOnePts + " pts" + player2statusString() + pTwoPts + " pts");
				// plays sounds when X or O wins
		}
		else if (status==2) {
			g.setFont(new Font("TimesRoman", Font.BOLD, 2*width/3));
			g.drawString("O wins!", width / 2, d.height/2);
			if (mode == 0) {
				// doesn't play applause if computer wins
				parent.applause.play();
			}
			else {
				// boos if computer wins
				parent.boo.play();
			}
			pTwoPts += 5;
			parent.pointsLabel.setText("Player1: " + pOnePts + player2statusString() + pTwoPts);
			
		}
		else if (status == 3) {
			g.setFont(new Font("TimesRoman", Font.BOLD, width/4));
			g.drawString("No one wins! Play again", d.width/8, 
											d.height/2+(border*2));
		}
	
		g.setFont(new Font("TimesRoman", Font.PLAIN, width/5));
		for (int i=0; i<3; i++){
			for (int j=0; j<3; j++){
				// draws Xs and Os depending on the integer stored in internal array
				if (grid[i][j]==1){
					g.drawString("X", (i+1)*(width)-(width/2)+border, 
										(j+1)*(height)-(height/2)+border);
				}else if (grid[i][j]==2){
					g.drawString("O", (i+1)*(width)-(width/2)+border, 
										(j+1)*(height)-(height/2)+border);
				}
			}
		}
	}
	
	public String playerString(){
		// changes the upper right label depending on whose turn it is
		if (mode == 1 || mode == 2) {
			// in computer modes, says "It's your turn!"
			return "It's your turn!";
		}
		else {
			// otherwise, it says whether it's Xs or Os next
			if (player) {
				return "Place an X";
			}
			else {
				return "Place an O";
			}
		}
	}
	
	public String player2statusString(){
		if (mode ==1 || mode == 2) {
			return "    Computer: ";		
		}
		else
			return "    Player2: ";
	}
	
	
	@Override
	public void mouseClicked(MouseEvent event) {
		// responds to mouse click
		Dimension d=getSize();
		int width=(d.width-border*2)/3;
		int height=(d.height-border*2)/3;
		
		if (status == 0) {
			// as long as no one has won the game yet...
			if (mode != 0 && player == false) {
				// if in computer mode and it's the computer's turn
				getMove();
				status=checkWin();
					// checks for win after turn, changes status if necessary
			}
			else {
				// if in 2 Player mode, or computer mode and it's the player's turn
				// takes the coordinates of click, changes appropriate cell of 
				// 		the internal array (which in turn changes the board)
				Point p=event.getPoint();
				for (int j=0; j<3; j++){
					if (p.x>width*j+border*j && p.x<border+width*(j+1)){
						for(int i=0; i<3; i++){
							if (p.y>height*i+border*i && p.y<border+height*(i+1)){
								if (grid[j][i] == 0) {
									if (player){
										grid[j][i]=1;
									}
									else{
										grid[j][i]=2;
									}
									player=!player;
										// switches players after move
								}		
							}
						}
					}
					status=checkWin();
						// checks for win, changes status if necessary
				}
				parent.statusLabel.setText(playerString());	
					// changes text of upper right status label
				repaint();
			}
		}
	}
	

	public void getMove() {
		// runs the computer's turn
		boolean moved = false;
		while(!moved) {
			// while the computer has not yet made its move
			if (mode == 2) {
				// if in HARD mode
				outerloop:
				for (int i=0; i<3; i++){
					// checks vertical
					if (grid[i][0] == grid[i][1] && grid[i][0] !=0) {
						if (grid[i][2] == 0) {
							grid[i][2] = 2;
							moved = true;
							break outerloop;
						}
					}
					else if (grid[i][0] == grid[i][2] && grid[i][0] !=0){
						if (grid[i][1] == 0) {
							grid[i][1] = 2;
							moved = true;
							break outerloop;
						}
					}
					else if (grid[i][1] == grid[i][2] && grid[i][1] !=0) {
						if (grid[i][0] == 0) {	
							grid[i][0] = 2;
							moved = true;
							break outerloop;
						}
					}
					// checks horizontal
					else if (grid[0][i] == grid[1][i] && grid[0][i] !=0) {
						if (grid[2][i] == 0) {	
							grid[2][i] = 2;
							moved = true;
							break outerloop;
						}
					}
					else if (grid[0][i] == grid[2][i] && grid[0][i] !=0){
						if (grid[1][i] == 0) {	
							grid[1][i] = 2;
							moved = true;
							break outerloop;
						}
					}
					else if (grid[1][i] == grid[2][i] && grid[0][i] !=0) {
						if (grid[0][i] == 0) {	
							grid[0][i] = 2;
							moved = true;
							break outerloop;
						}
					}
					// checks horizontal
					else if (grid[0][0] == grid[1][1] && grid[0][0] != 0) {
						if (grid[2][2] == 0) {	
							grid[2][2] = 2;
							moved = true;
							break outerloop;
						}
					}
					else if (grid[0][0] == grid[2][2] && grid[0][0] != 0) {
						if (grid[1][1] == 0) {	
							grid[1][1] = 2;
							moved = true;
							break outerloop;
						}
					}
					else if (grid[0][2] == grid[2][0] && grid[2][0] != 0) {
						if (grid[1][1] == 0) {	
							grid[1][1] = 2;
							moved = true;
							break outerloop;
						}
					}
					else if (grid[0][2] == grid[1][1] && grid[0][2] != 0) {
						if (grid[2][0] == 0) {	
							grid[2][0] = 2;
							moved = true;
							break outerloop;
						}
					}
					else if (grid[2][0] == grid[1][1] && grid[1][1] != 0) {
						if (grid[0][2] == 0) {	
							grid[0][2] = 2;
							moved = true;
							break outerloop;
						}
					}
				}
			}
			if (!moved) {
				// if the computer still hasn't made a move, or if
				// in EASY mode, places an O in the next empty cell, moving
				// in a vertical, left to right order
				outerloop:
				for(int i = 0; i<3; i++){
					for(int j = 0; j<3; j++){
						if (grid[i][j] == 0) {
							grid[i][j]=2;
							moved = true;
							break outerloop;
						}
					}
				}
			}	
		}
		player = !player;
			// changes player
	}

	public void newGame() {
		pOnePts = 0;
		pTwoPts = 0;
		parent.pointsLabel.setText("Player1: 0 pts" + player2statusString() + "0 pts");
		reset();
	}
	
	public void reset(){
		// clears the board, resets all status and modes
		grid=new int[3][3];
		status = 0;
		player = true;
		parent.statusLabel.setText("Place an X");
		repaint();
	}
	
	
	public int checkWin(){
		// Checks if X or O has won, or if the board is full and it's a tie
		for (int i=0; i<3; i++){
			// checks vertical
			if (grid[i][0]==1 && grid[i][0]==grid[i][1] && grid[i][1]==grid[i][2]){
				System.out.println("X wins");
				return 1;
			}
			if (grid[i][0]==2 && grid[i][0]==grid[i][1] && grid[i][1]==grid[i][2]){
				System.out.println("O wins");
				return 2;
			}
		}
		for (int j=0; j<3; j++){
			// checks horizontal
			if (grid[0][j]==1 && grid[0][j]==grid[1][j] && grid[1][j]==grid[2][j]){
				System.out.println("X wins");
				return 1;
			}
			if (grid[0][j]==2 && grid[0][j]==grid[1][j] && grid[1][j]==grid[2][j]){
				System.out.println("O wins");
				return 2;
			}
		}
		// checks diagonals
		if (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[0][0] == 1) {
			System.out.println("X wins");
			return 1;
		}
		if (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[0][0] == 2) {
			System.out.println("O wins");
			return 2;
		}
		if (grid[2][0] == grid[1][1] && grid[1][1] == grid[0][2] && grid[2][0] == 1) {
			System.out.println("X wins");
			return 1;
		}
		if (grid[2][0] == grid[1][1] && grid[1][1] == grid[0][2] && grid[2][0] == 2) {
			System.out.println("O wins");
			return 2;
		}
		
		// checks if board is full using the sum (which is always 13, since X always
		// goes first)
		int sum = 0;
		for (int i=0; i<3; i++){
			for (int j=0; j<3; j++){
				sum += grid[i][j];
				if (sum == 14 || sum == 13) {
					return 3;
				}
			}	
		}
		return 0;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseReleased(MouseEvent e) {
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
}
