/*
 * Alexander Komarov
 * 10/04/2018
 * I learned a vital lesson today, when linking eclipse files
 * directly to github, then deleting the repository
 * the entire file in eclipse is affected.
 */
package Assignment;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class DB_Manager extends JFrame implements ActionListener
{
	//Declare global attributes
	private JLabel changeText;
	private JLabel submitText;
	
	private JTextField textToChange;
	private JScrollPane scrollList;
	
	private JButton add;
	private JButton remove;
	private JButton saveToFile;
	
	//String array holding all the Blasphemies
	String cursewords[];
	
	JList DB_Content;
	
	public DB_Manager()
	{
		//set screen
		super("Edit Database");
		setSize(550,250);
		setLayout(new FlowLayout());
		
		//Read Text File
		FileManager cursewordfile = new FileManager("cursewords.txt");
		cursewordfile.connectToFile();
		this.cursewords = cursewordfile.readFile();
		
		//fill screen
		changeText = new JLabel("Add Word : ");
		add(changeText);
		
		textToChange = new JTextField(15);
		add(textToChange);
		
		add = new JButton("Add");
		add(add);
		add.addActionListener(this);
		
		remove = new JButton("Remove");
		add(remove);
		remove.addActionListener(this);
		
		//View Database in a list
		DB_Content = new JList(this.displayDB(this.cursewords));
		add(DB_Content);
		
		//Add scroll bar to Database DisplayWords
		scrollList = new JScrollPane(DB_Content);
		add(scrollList);
		
		submitText = new JLabel("Save Changes to Swear Database : ");
		add(submitText);
		
		saveToFile = new JButton("Submit");
		add(saveToFile);
		saveToFile.addActionListener(this);
		
		//once all items added, set objects visible.
		setVisible(true);
	}//End constructor
	
	public void actionPerformed(ActionEvent e) 
	{
		
		if (e.getSource() == add)
		{
			//Get the String from the JTextField
			String newEntry = textToChange.getText();
			
			//Check if entry is empty or Null
			if((newEntry != "") && (newEntry != null))
			{
				//Add entry to array
				this.cursewords = this.addEntry(newEntry.toLowerCase());
				//Refresh DisplayWords
				DB_Content.setListData(this.displayDB(this.cursewords));
				
				//Refresh Text Field and give feedback to user
				textToChange.setText("");
				JOptionPane.showMessageDialog(this, "Curse Word Added");
				
			}
			else
			{
				
				JOptionPane.showMessageDialog(this, "Cannot Add; Field Empty.");
				
			}
			
		}//End Add Button
		
		if (e.getSource() == remove)
		{
			//Get the String from the JTextField
			String removeEntry = textToChange.getText();
			
			//Check if entry is empty or Null
			if((removeEntry != "") && (removeEntry != null))
			{
				//Delete the entry you wish to remove
				cursewords = deleteEntry(removeEntry.toLowerCase());
				//Refresh DisplayWords
				DB_Content.setListData(this.displayDB(this.cursewords));
				
				//Refresh Text Field and give feedback to user
				textToChange.setText("");
				JOptionPane.showMessageDialog(this, "Curse Word Removed");
				
			}
			else
			{
				
				JOptionPane.showMessageDialog(this, "Cannot remove; Field Empty.");
				
			}
			
		}//End Remove Button
		
		if(e.getSource() == saveToFile) //Submit Button Clicked 
		{
			//connect to file, write to file, then inform user.
			FileManager curseWordsFile = new FileManager("cursewords.txt");
			curseWordsFile.connectToFile();
			curseWordsFile.writeString(cursewords); // write changes
			
			JOptionPane.showMessageDialog(this, "Changes Saved");
			
		}//End Submit Button
		
	}//End of Action Listening
	
	public String[] addEntry(String Adding)
	{
		
		String input[] = this.cursewords;
		String output[] = new String[input.length+1]; // new string one element bigger
		
		//Initialize variables to use
		int i =0, j = 0;
		boolean replace = false; 
		
		//Make sure not to run into end of file.
		while(input[i] != null)
		{
			 //Find place for new entry alphabetically
			if ((input[i].compareTo(Adding) > 0) && (replace == false))
			{
				//ignore case since VAR sent in to lower case
				output[j] = Adding;
				j++;
				replace = true;
				
			}
			output[j] = input[i];
			i++;
			j++;
			
		}
		if(replace == false)
		{
			//If replace still not occurred, add to bottom.
			output[j] = Adding.toLowerCase();
			
		}
		
		return output;
		
	}//End Add String Entry
	
	public String[] deleteEntry(String deleted)
	{
		
		String input[] = this.cursewords;
		String output[] = new String[input.length-1];
		
		int i = 0;
		int j = 0;
		//Loop through array, copying everything but the offending item.
		while(input[i] != null)
		{
			//If it is an exact match
			if((input[i].compareTo(deleted) == 0))
			{
				//skip (not?) offending line
				i++;
				
			}
			//place the input item into output.
			output[j] = input[i];
				
			i++;
			j++;

		}
		
		return output;
		
	}//End Delete an Entry
	
	//Display String array on screen
	public String[] displayDB(String input[])
	{
		
		int length=0;
		
		//Calculate array length
		while (input[length] != null)
		{
			
			length++;
			
		}
		
		//New string with length of used elements
		String output[] = new String[length];
		for (int i = 0; i < length; i++)
		{
			//Populate new string with used elements of old
			output[i] = input[i]; 
			
		}
		
		return output;
	}//End DisplayWords
	
}//end Class
