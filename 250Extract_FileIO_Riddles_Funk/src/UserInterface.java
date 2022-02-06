import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;
public class UserInterface {
	//Data type responsible for the entire interface
		private JFrame mainFrame;
		//Data type responsible for the text over button field
		private JLabel riddleLabel;
		//Data type responsible for text over answer field
		private JLabel answerLabel;
		//Data type responsible for the action buttons
		private JPanel buttonsPanel;
		//Data type responsible for the actual text field displaying the riddle
		private JTextArea riddleText;
		
		private LinkedList<RiddleAnswer> list;
		private String riddle;
		private int riddleIndex;
		private int riddleTextLength;
		private String answer;
		private int listLength;
		
		
		public UserInterface(LinkedList<RiddleAnswer> list)
		{
			this.list = list;
			this.riddleIndex = 0;
			this.riddleTextLength = 0;

			this.listLength = list.size();
			
			// Go initialize the GUI.
			prepareGUI();
		}


		private void prepareGUI(){

			//Creates a new frame that's initially invisible
			mainFrame = new JFrame("Riddle and Answers");
			//Determines the dimensions of the frame
			mainFrame.setSize(900,500);
			//Creates a grid layout within frame,with specified rows and columns(in that order)
			mainFrame.setLayout(new GridLayout(4, 1));

			//Creates a JLabel instance with the specified text and horizontal alignment
			riddleLabel = new JLabel("RIDDLE",JLabel.LEFT );
			//Sets the foreground color of this component
			riddleLabel.setForeground(Color.BLUE);
			//Creates a new Font from the specified name, style and point size
			riddleLabel.setFont(new Font("Arial", Font.BOLD,24));
			//Sets the alignment of the label's contents along the Y axis.
			riddleLabel.setVerticalAlignment(JLabel.CENTER);
			
			//Essentially the same as riddle label
			answerLabel = new JLabel("Answer?",JLabel.LEFT);        
			answerLabel.setForeground(Color.RED);
			answerLabel.setFont(new Font("Arial", Font.BOLD,24));
			answerLabel.setVerticalAlignment(JLabel.CENTER);
			
			//Calls the linked list holding the riddles and stores them into a parameter
			riddle = list.get(riddleIndex).GetRiddle();
			//Stores an int length of the riddle string into a parameter
			riddleTextLength = riddle.length();
			//Constructs a new TextArea with the specified text displayed.
			riddleText = new JTextArea(riddle);
			
			//Sets the line-wrapping policy of the text area(must be a boolean).
			riddleText.setLineWrap(true);
			//Sets the style of wrapping used if the text area is wrapping lines(must be a boolean).
			riddleText.setWrapStyleWord(true);
			//Creates a new Font from the specified name, style and point size
			riddleText.setFont(new Font("Arial", Font.BOLD,20));

			//Adds the specified window listener to receive window events from this window. 
			//WindowAdapter: abstract class for receiving window events
			mainFrame.addWindowListener(new WindowAdapter()
			{
			//	Invoked when the user attempts to close the window from the window's system menu.
				public void windowClosing(WindowEvent windowEvent)
				{
					System.out.println("Ending Riddle/Answer Program");
					System.exit(0);
				}        
			} );
			
			// Define the existence of the buttons panel.
			buttonsPanel = new JPanel();
			//Constructs a new FlowLayout with a centered alignment and a default 5-unit horizontal and vertical gap.
			FlowLayout flowLayout = new FlowLayout();  
			//Sets the vertical gap between components and between the components and the borders of the Container.
		    flowLayout.setVgap(30);
		    
			buttonsPanel.setLayout(flowLayout);
			
			//Adding all the components to the mainFrame
			mainFrame.add(riddleLabel);
			mainFrame.add(riddleText);
			mainFrame.add(buttonsPanel);
			mainFrame.add(answerLabel);
			//Initially these parameters are set to be invisible, setVisible method changes that
			mainFrame.setVisible(true);
			
			//Creates a button with text. Although able to pass through different parameters
			JButton seeButton = new JButton("See Answer");
			JButton nextButton = new JButton("Next Riddle");
			JButton randomButton = new JButton("Random Riddle");
			JButton closeButton = new JButton("Close");
			
			//Creates a response to when a button is clicked and passes that through as an action
			seeButton.addActionListener(new ButtonClickListener()); 
			//Binds the specified action to a string
			seeButton.setActionCommand("SEE");
			nextButton.addActionListener(new ButtonClickListener()); 
			nextButton.setActionCommand("NEXT");
			randomButton.addActionListener(new ButtonClickListener()); 
			randomButton.setActionCommand("RANDOM");
			closeButton.addActionListener(new ButtonClickListener()); 
			closeButton.setActionCommand("CLOSE");

			//Adds the buttons to the button panel
			buttonsPanel.add(seeButton);
			buttonsPanel.add(nextButton);
			buttonsPanel.add(randomButton);
			buttonsPanel.add(closeButton);
			//Sets parameters to be visible(because initially they're not)
			mainFrame.setVisible(true);  
		}
		//button action listener class

		private class ButtonClickListener implements ActionListener {
			
			//button action invoked when an action occurs
			public void actionPerformed(ActionEvent e) {

				//retrieves the action done and stores it into a string
				//Line 125 is a reference to the relevant action which is represented as a string
				String command = e.getActionCommand();
				// System.out.print(e.toString()+"/\n");
				
				//if the action command returned was string "SEE"
				if (command.equals("SEE"))
				{
					//get current riddle index answer and set it to parameter answer
					answer = list.get(riddleIndex).GetAnswer();
					//replace the current answer label with the index that was just returned
					answerLabel.setText(answer);
				} 
				//instead, if action command returned was string "NEXT"
				else if (command.equals("NEXT"))
				{
					//increment the riddle index
					++riddleIndex;
		//if the riddle index surpasses the bounds of the LinkedList, set riddle index to 0. Represents the 0th index in the linked list
					if (riddleIndex >= listLength) riddleIndex = 0;
					//get current riddle index after the increment and set it to parameter riddle
					riddle = list.get(riddleIndex).GetRiddle();
					
					//Replaces text from the indicated start to end position with the new text specified
					//	replaceRange(String str, int start, int end)
					//str = text used as replacement; start = start position; end = end position
					riddleText.replaceRange("", 0, riddleTextLength);
					//Inserts the specified text at the specified position.
					riddleText.insert(riddle, 0);
					//sets the length of the new current riddle into riddleTextLength as an int
					riddleTextLength = riddle.length();
					
					//sets the previous answer label to the specified text
					answerLabel.setText("Answer?");
				} 
				//if action returned was string "RANDOM"
				else if (command.equals("RANDOM"))
				{
					//create an integer
					int newRandom = 0;
					do
					{
				//sets the largest casted integer to be the product of a double between 0 and 1 with the length of the LinkedList
						newRandom = (int)Math.floor(Math.random() * (listLength-1));
						
						
					} while (newRandom == riddleIndex);
					//set the new random to be the current riddle index
					riddleIndex = newRandom;
					
					//get that current riddle index in the linked list and set it to riddle in order to represent it as a string
					riddle = list.get(riddleIndex).GetRiddle();

					//Replaces text from the indicated start to end position with the new text specified
					//	replaceRange(String str, int start, int end)
					//str = text used as replacement; start = start position; end = end position
					riddleText.replaceRange("", 0, riddleTextLength);
					//Inserts the specified text at the specified position.
					riddleText.insert(riddle, 0);
					//sets the length of the new current riddle into riddleTextLength as an int
					riddleTextLength = riddle.length();
					//sets the previous answer label to the specified text

					answerLabel.setText("Answer?");
				}
				//if none of those other actions were performed exit the program
				else
				{
					System.out.println("Ending Program via Close button");
					System.exit(0);
				}
			}
		}
	}

	
	
	

