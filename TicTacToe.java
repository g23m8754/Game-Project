import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener{ //implements an interface
	
	//initialising a frame, panel, and label
	Random random = new Random();
	JFrame frame = new JFrame();
	JPanel title_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JLabel text = new JLabel();
	JButton[] buttons = new JButton[9];
	boolean P1;  //if P1 is false then it will be P2's turn 
	
	
	TicTacToe(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		text.setBackground(new Color(25,25,25));
		text.setForeground(new Color(57,255,20));
		text.setFont(new Font("Ink Free", Font.BOLD,75));
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setText("Tic Tac Toe!");
		text.setOpaque(true);
		
		title_panel.setLayout(new BorderLayout());
		title_panel.setBounds(0,0,800,100);
		
		button_panel.setLayout(new GridLayout(3,3));
		button_panel.setBackground(new Color(150,150,150));
		
		//creates buttons and styles them
		for (int x = 0; x < 9; x++){
			buttons[x] = new JButton();
			button_panel.add(buttons[x]);
			buttons[x].setFont(new Font ("MV Boli", Font.BOLD,120));
			buttons[x].setFocusable(false); //buttons cannot gain focus
			buttons[x].addActionListener(this);
		}
		
		
		title_panel.add(text);
		frame.add(title_panel,BorderLayout.NORTH); //sticks title panel to the top of the border
		frame.add(button_panel);
		
		fTurn();
	}
	
	@Override
	public void actionPerformed(ActionEvent e){ //Determines which player just put their sign down then hands over the turn to the other player
		for (int y = 0; y < 9; y++){
			if (e.getSource() == buttons[y]){
				if (P1){
					if(buttons[y].getText() == ""){
						buttons[y].setForeground(new Color(77,77,255));
						buttons[y].setText("X");
						P1 = false;
						text.setText("O's turn"); 
						check();
					}
				} else{
					if(buttons[y].getText() == ""){
						buttons[y].setForeground(new Color(254,1,154));
						buttons[y].setText("O");
						P1 = true;
						text.setText("X's turn");
						check();
				}
			}
		}
	}
}
	 
	public void fTurn(){  //determines which player starts first
		if(random.nextInt(2) == 0) {
			P1 = true;
			text.setText("X's turn");
		} else {
			P1 = false;
			text.setText("O's turn");
		}
	}
	
	public void check(){//checks to see which player has the winning combination
		if((buttons[0].getText() == "X") &&  //checks if the first row has matching X's
		   (buttons[1].getText() == "X") && 
		   (buttons[2].getText() == "X")){
			   xWins(0,1,2);//winning combo
			   }
			   
		if((buttons[3].getText() == "X") &&  //checks if the second row has matching X's
		   (buttons[4].getText() == "X") && 
		   (buttons[5].getText() == "X")){
				xWins(3,4,5);//winning combo
				}
				
		if((buttons[6].getText() == "X") &&  //checks if the third row has matching X's
		   (buttons[7].getText() == "X") && 
		   (buttons[8].getText() == "X")){
			   xWins(6,7,8);//winning combo
			   }
		
		if((buttons[1].getText() == "X") && 
		   (buttons[4].getText() == "X") && 
		   (buttons[7].getText() == "X")){
			   xWins(1,4,7);//winning combo
			   }
		
		if((buttons[0].getText() == "X") &&  
		   (buttons[3].getText() == "X") && 
		   (buttons[6].getText() == "X")){
			   xWins(1,3,6);//winning combo
			   }
			   
		if((buttons[2].getText() == "X") &&  
		   (buttons[5].getText() == "X") && 
		   (buttons[8].getText() == "X")){
			   xWins(2,5,8);//winning combo
			   }
		
		
		if((buttons[0].getText() == "X") &&  
		   (buttons[4].getText() == "X") && 
		   (buttons[8].getText() == "X")){
			   xWins(0,4,8);//winning combo
			   }
			   
		if((buttons[2].getText() == "X") &&  //checks if the third row has matching X's
		   (buttons[4].getText() == "X") && 
		   (buttons[6].getText() == "X")){
			   xWins(2,4,6);//winning combo
			   }
			   
        if((buttons[0].getText() == "O") &&  //checks if the first row has matching X's
		   (buttons[1].getText() == "O") && 
		   (buttons[2].getText() == "O")){
			   oWins(0,1,2);//winning combo
			   }
			   
		if((buttons[3].getText() == "O") &&  //checks if the second row has matching X's
		   (buttons[4].getText() == "O") && 
		   (buttons[5].getText() == "O")){
				oWins(3,4,5);//winning combo
				}
				
		if((buttons[6].getText() == "O") &&  //checks if the third row has matching X's
		   (buttons[7].getText() == "O") && 
		   (buttons[8].getText() == "O")){
			   oWins(6,7,8);//winning combo
			   }
		
		if((buttons[1].getText() == "O") && 
		   (buttons[4].getText() == "O") && 
		   (buttons[7].getText() == "O")){
			   oWins(1,4,7);//winning combo
			   }
		
		if((buttons[0].getText() == "O") &&  
		   (buttons[3].getText() == "O") && 
		   (buttons[6].getText() == "O")){
			   oWins(1,3,6);//winning combo
			   }
			   
		if((buttons[2].getText() == "O") &&  
		   (buttons[5].getText() == "O") && 
		   (buttons[8].getText() == "O")){
			   oWins(2,5,8);//winning combo
			   }
		
		
		if((buttons[0].getText() == "O") &&  
		   (buttons[4].getText() == "O") && 
		   (buttons[8].getText() == "O")){
			   oWins(0,4,8);//winning combo
			   }
			   
		if((buttons[2].getText() == "O") &&  //checks if the third row has matching X's
		   (buttons[4].getText() == "O") && 
		   (buttons[6].getText() == "O")){
			   oWins(2,4,6);//winning combo
			   }
			    
		
	}
	
	public void xWins(int x, int y, int z){
		buttons[x].setBackground(Color.BLUE);
		buttons[y].setBackground(Color.BLUE);
		buttons[z].setBackground(Color.BLUE);
		
		for (int a = 0; a < 9; a++){
			buttons[a].setEnabled(false);
		}
		text.setText("X Wins!");
	}
	
	
	
	public void oWins(int x, int y, int z){
		buttons[x].setBackground(Color.BLUE);
		buttons[y].setBackground(Color.BLUE);
		buttons[z].setBackground(Color.BLUE);
		
		for (int a = 0; a < 9; a++){
			buttons[a].setEnabled(false);
		}
		text.setText("O Wins!");
	}
	 
}
