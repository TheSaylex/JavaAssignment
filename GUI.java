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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GUI extends JFrame implements ActionListener
{
	//Declare global attributes
	private JLabel inputSelect;
	
	private JFileChooser findPath;
	
	private JButton browse;
	private JButton editorStart;
	private JButton scannerButton;
	
	public GUI()
	{
		//set screen
		super("File Selection");
		setSize(600,150);
		setLayout(new FlowLayout());
		
		//fill screen
		this.inputSelect = new JLabel("Select a file.");
		add(inputSelect);//Label
		
		this.findPath = new JFileChooser();
		
		this.browse = new JButton("Browse For Post File");
		add(browse);//button
		this.browse.addActionListener(this);
		
		this.scannerButton = new JButton("Scan Post");
		add(scannerButton);//button
		scannerButton.addActionListener(this);
		
		this.editorStart = new JButton("Edit Curse Words");
		add(editorStart);//button
		this.editorStart.addActionListener(this);
		
		//once all items added, set objects visible.
		setVisible(true);
		
	}//End constructor

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		if (e.getSource() == browse)
		{
			// browse button - create input selector
			findPath.showOpenDialog(this); 
		}
		
		if (e.getSource() == editorStart) 
		{
			//open DataBase editing GUI
			DB_Manager databaseGUI = new DB_Manager();
		}
		
		if (e.getSource() == scannerButton)
		{
			
			//only if a path is found
			if (findPath.getSelectedFile() != null)
			{
				//look for abusive content in a post.
				contentProcessing post = new contentProcessing(findPath.getSelectedFile()); 
			}
			
			else
			{
				JOptionPane.showMessageDialog(this, "file not selected");
			}//end if file not found.
			
		}
		
	}//the end of listening to all possible actions
	
}//end Class
