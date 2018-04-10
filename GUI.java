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

public class GUI extends JFrame implements ActionListener
{
	//Declare global attributes
	private JLabel inputSelect;
	private JFileChooser findPath;
	private JButton browse;
	private JLabel Editortext;
	private JButton Editorstart;
	private JButton ScannerButton;
	
	public GUI()
	{
		//set screen
		super("Select Screen");
		setSize(600,150);
		setLayout(new FlowLayout());
		
		//fill screen
		this.inputSelect = new JLabel("File Selection");
		add(inputSelect);//Label
		
		this.findPath = new JFileChooser();
		
		this.browse = new JButton("Browse For Post File");
		add(browse);//button
		this.browse.addActionListener(this);
		
		this.ScannerButton = new JButton("Scan Post");
		add(ScannerButton);//button
		ScannerButton.addActionListener(this);
		
		this.Editorstart = new JButton("Edit Curse Words");
		add(Editorstart);//button
		this.Editorstart.addActionListener(this);
		
		//once all items added, set objects visible.
		setVisible(true);
		
	}//End constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == browse)
		{
			// browse button - create input selector
			findPath.showOpenDialog(this); 
		}
		
		if (e.getSource() == Editorstart) 
		{
			//open DataBase editing GUI
			DB_Manager de1 = new DB_Manager();
		}
		
		if (e.getSource() == ScannerButton) // scan file
		{
			//only if a path is found
			if (findPath.getSelectedFile() != null)
			{
				//look for abusive content in a post.
				contentProcessing post = new contentProcessing(findPath.getSelectedFile()); 
			}
		}
		
	}//the end of listening to all possible actions
	
}//end Class
