/*
 * Alexander Komarov
 * 10/04/2018
 * Why is Coffee and Sleep Deprivation so integral to coding?
 */
package Assignment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class contentProcessing extends JFrame implements ActionListener
{
	//Declare global attributes
		private JLabel inputSelect;
		private JFileChooser findPath;
		private JButton browse;
		private JLabel Editortext;
		private JButton Editorstart;
		private JButton ScannerButton;
	
	contentProcessing(File Infile)
	{
		System.out.println("I am a bananana");
	}//End constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}//end Class
