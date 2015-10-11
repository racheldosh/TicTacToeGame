//TicTacToe.java
//CS201 Final Project
//Rachel Collins
//Jen Johnson

import java.applet.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")

public class TicTacToe extends Applet implements ActionListener, ItemListener{
	
	// instance variables
	TicTacToeCanvas c;
	Button resetButton, newGameButton;
	Label titleLabel, statusLabel, pointsLabel;
	AudioClip applause, boo;
	Choice mode;
	
	// sets the layout of applet
	public void init(){
		
		applause = getAudioClip(getCodeBase(), "Applause.au");
		boo = getAudioClip(getCodeBase(), "Boo.au");
			// imports sounds
		
		setLayout(new BorderLayout());
		
		resetButton=new Button("Reset");
		resetButton.setFont(new Font("Helvetica", Font.PLAIN, 14));
		Panel bottom = new Panel();
		Color purple = new Color(120, 81, 169);
			// creates a new pretty color based on RGB values
		bottom.setBackground(purple);
		bottom.setLayout(new GridLayout(1, 3));
		newGameButton = new Button("New Game");
		newGameButton.setFont(new Font("Helvetica", Font.PLAIN, 14));
		bottom.add(newGameButton);
		newGameButton.addActionListener(this);
		bottom.add(resetButton);
		resetButton.addActionListener(this);
		mode = new Choice();
			// three choice drop-down menu created
		mode.add("2 Player");
		mode.add("vsComp EASY :)");
		mode.add("vsComp HARD >:o");
		mode.addItemListener(this);
		bottom.add(mode);
		add("South", bottom);
		
		titleLabel=new Label();
		titleLabel.setFont(new Font("Helvetica", Font.BOLD, 50));
		titleLabel.setAlignment(Label.CENTER);
		titleLabel.setText("Tic Tac Toe");
		statusLabel=new Label("Place an X");
		statusLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		statusLabel.setAlignment(Label.RIGHT);
		pointsLabel = new Label("Player1: 0 pts    Player2: 0 pts");
		pointsLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		pointsLabel.setAlignment(Label.LEFT);
		
		Panel p = new Panel();
		p.setBackground(purple);
		p.setLayout(new BorderLayout());
		Panel southP = new Panel();
		southP.setLayout(new GridLayout(1, 2));
		southP.add(pointsLabel);
		southP.add(statusLabel);
		p.add("South", southP);
		p.add("Center", titleLabel);
		add("North", p);
		c = new TicTacToeCanvas(this);
		c.setBackground(Color.pink);
		c.addMouseListener(c);
		add("Center", c);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// responds to pushing reset button
		if (e.getSource()==resetButton){
			c.reset();
		}		
		if (e.getSource() == newGameButton) {
			c.newGame();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// responds to drop-down menu choice
		Choice mode = (Choice) e.getSource();
		String selection = mode.getSelectedItem();
		if (selection.equals("vsComp EASY :)")) {
			c.mode = 1;
		}
		else if (selection.equals("2 Player")) {
			c.mode = 0;
		}
		else {
			c.mode = 2;
		}
		
	}
}
