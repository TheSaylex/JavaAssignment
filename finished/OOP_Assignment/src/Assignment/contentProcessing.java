/*
 * Alexander Komarov
 * 11/04/2018
 * This Class goes through an input file and process it;
 * checking to see if it contains curse words or multiple over-capitalized letters
 * in a single word.
 */
package Assignment;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class contentProcessing extends JFrame implements ActionListener
{
	//Declare global attributes
	private JButton next;
	private JButton prev;
	private JButton scanPost;
	
	public JLabel position;
	public JLabel checkedValues;
	
	private JTextArea postContent;
	JScrollPane scrollList;
	
	public String allContent[];
	public String previewPost[];
	
	public int capsLock = 0;
	public int curseWords = 0;
	public int length = 0;
	public int currentPost = 1;
	public int totalPosts = 1;
	
	contentProcessing(File findFile)
	{
		//set screen
		super("Select Screen");
		setSize(600, 500);
		setLayout(new FlowLayout());
		
		//Read Text File
		allContent = readStringFile(findFile.getAbsolutePath());
		
		//set buttons like video/music player
		prev = new JButton("Prev Post");
		add(prev);
		prev.addActionListener(this);
		
		scanPost = new JButton("Check Post");
		add(scanPost);
		scanPost.addActionListener(this);
		
		next = new JButton("Next Post");
		add(next);
		next.addActionListener(this);

		
		//Show Post in a TextArea.
		postContent = new JTextArea("Test", 20, 25);
		add(postContent);
		scrollList = new JScrollPane(postContent);
		add(scrollList);
		
		//initialize Labels
		checkedValues = new JLabel("CurseWords = 0 / 0  \tOvercapitalized Words = 0 / 0 ");
		add(checkedValues);
		
		
		position = new JLabel("Post X / X");
		add(position);
		
		//Read in the Post Size
		int postnum = 0;
		int i = 0;
		
		//while it has not reached end of file.
		while (allContent[i] != null)
		{
			//if there is an entry
			if (allContent[i].contains("<Entry>")) 
			{
				//There exists a post.
				postnum++;
				
			}
			i++;

		}//Reaches end of file.
		
		
		//If User reaches null without an <Entry> in the file, then file is not compatible.
		if (postnum == 0)
		{
			//Close the GUI for content Processing and inform user.
			JOptionPane.showMessageDialog(this, "File Has no Posts");
			return;
			
		}
		
		//Set Total, then Update Post Number
		this.totalPosts = postnum;
		UpdateDisplay();
				
		//once all processing done, set objects visible.
		setVisible(true);
				
	}//End constructor

	
	public void actionPerformed(ActionEvent e) 
	{
		
		if (e.getSource() == next)
		{
			
			//cannot exceed total posts
			if (this.currentPost < this.totalPosts) 
			{
				
				//Increase current post number
				this.currentPost++;
				
				//Update Displays
				UpdateDisplay();
				
			}
		}//end next press
		
		if (e.getSource() == prev)
		{
			
			//cannot go below 1
			if (this.currentPost != 1 )
			{
				//Lower post num
				this.currentPost--; 
				
				//Update displays
				UpdateDisplay();
				
				
			}
		}//End previous button
		
		if (e.getSource() == scanPost)
		{
			//Check the Content for cursewords and capslock
			this.checkPost();
			this.checkedValues.setText("CurseWords = " +  Integer.toString(this.curseWords) + " / " + Integer.toString(this.length) + "  "
			+ "Overcapitalized Words = " + Integer.toString(this.capsLock) + " / " + Integer.toString(this.length)); //update results
			
		}//End Check pressed
		
	}//end action listener
	
	
	public void UpdateDisplay()
	{
		//update the information on screen.
		previewPost = this.movePost(this.allContent);
		this.postContent.setText(Arrays.toString(previewPost));
		this.checkedValues.setText("Post has not been scanned yet");
		this.position.setText("Post Number " + Integer.toString(this.currentPost) + " / "+ Integer.toString(this.totalPosts) );
		
	}
	
	public String[] readStringFile(String filename)
	{
		//open file, read the String array, return array output.
		String output[];
		
		FileManager fileMan = new FileManager(filename);
		fileMan.connectToFile();
		output = fileMan.readFile();
		
		return output;
		
	}
	
	public String[] movePost(String[] allPosts)
	{
		//Initialize local variables
		int i = 0;
		String authorName;
		int startContent;
		int endContent;
		int postlength;
		int postnum;
		String output[];
		
		//current post being read
		postnum = 0; 
		boolean found = false;
		
		//Go through the Length of all posts and Find the preview Post.
		while ((i < allPosts.length) && (found == false))
		{
			//if an Author tag is found
			if (allPosts[i].contains("<Author>"))
			{
				//Increase the current post.
				postnum++;
				//is this post the one we want to preview
				if (postnum == currentPost)
				{
					//once on current post, exit while loop
					found = true;
					
				}
			}//find
			i++;
			
		}//Found the preview Post
		
		//After <Author> Authors name
		authorName = allPosts[i];
		//Skip author name and <Entry> to Start of Content
		i = i + 2;
		startContent = i;
		
		while (allPosts[i].contains("</Entry>") == false) //find end of post
		{
			i++;
			
		}
		
		//the content ends before the end entry tag </entry>, the length is between the start and the end.
		endContent = i;
		postlength = endContent - startContent;
		
		//Output length is content + Author name
		output = new String[postlength+1];
		output[0] = authorName;
		i = startContent;
		
		for (int j = 1; j < postlength+1;j++) //Populate Output with author name + Post Content  
		{
			//Put Preview content into the output.
			output[j] = allPosts[i];
			i++;
		}
		
		return output;
		
	}//end select post
	
	//Check for Curse Words & Over-Capitalization.
	public void checkPost()
	{
		//Initialize local variables
		int overCapitalization = 0;
		int capitals = 0;
		int curseWords = 0;
		int wordCount = 0;
		int k;
		char[] word;
		String Line[];
		String CurseWords[];
		
		//Read file for string array of curse words.
		CurseWords = readStringFile("cursewords.txt");
		
		//Break each Line into words and check each word for curse words in post we are currently previewing.
		for (int i = 0; i < this.previewPost.length; i++)
		{
			//Break Line into individual words
			Line = this.previewPost[i].split("\\s+");
			
			for (int j = 0; j < Line.length; j++) // for each word
			{
				//Strip off these punctuation
				Line[j] = Line[j].replace("\\", "");
				Line[j] = Line[j].replace("?", "");
				Line[j] = Line[j].replace("-", "");
				Line[j] = Line[j].replace("!", "");
				Line[j] = Line[j].replace("'", "");
				Line[j] = Line[j].replace(":", "");
				Line[j] = Line[j].replace(".", "");
				Line[j] = Line[j].replace(";", "");
				
				k = 0;
				wordCount++;
				
				//Go through Array of Curse Words
				while (CurseWords[k] != null)
				{
				
					//Does Curse Word match word in line comparison
					if (CurseWords[k].toLowerCase().equals(Line[j].toLowerCase())) 
					{
						//if yes, increment curseWords
						curseWords++;
						
					}
					k++;
					
				}
				
				k=0;
				
				// split into individual letters
				word = Line[j].toCharArray();
				
				for (int l = 0; l < word.length; l++) // for each letter
				{
					//Check how many capitals in a word
					if( word[l] == Character.toUpperCase(word[l]))
					{
						
						capitals++;
						
					}
					
				}
				//More than 1 Capital in a word could be shouting.
				if (capitals > 1)
				{
					
					overCapitalization++;
					
				}
				//reset for next word.
				capitals = 0;
				
			}//End for each Word.
						
		}//end for post length
		
		//update attributes
		this.capsLock = overCapitalization;
		this.curseWords = curseWords;
		this.length = wordCount;
		
	}//end check post
	
}//end Class
