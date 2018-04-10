/*
 * Alexander Komarov
 * 10/04/2018
 * Thankfully I had Week 7 solution
 * and my own code to create this from.
 */

package Assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager 
{
	//Declare global attributes
	String fileName;
	File myFile;
	Scanner myScanner;
    PrintWriter myInput;
	
	// Constructor
	FileManager (String fileName)
	{
		this.fileName = fileName;
	}
	
	
	//Connect to file
	void connectToFile()
	{
		myFile = new File(this.fileName);
	}

   	
	//Read a file, return a string of lines
	String[] readFile()
	{
		//create a variable to store lines.
		String[] values = new String[1000];
	    
		try
		{
			//Initialize array counter
	    	int i = 0;
	    	
	    	//get a scanner object
	    	myScanner = new Scanner(myFile);
	    	
	    	//Iterate through lines of a file
			while (myScanner.hasNextLine())
	        {
				//fill line into array
				values[i] = myScanner.nextLine();
 		        i++;
	        }
			
			return values;
		}//End Try
		catch (FileNotFoundException e)
		{
			System.out.println("FileReading; File Not Found.\n" + e.getMessage());
		}//End Catch
		
		return values;
		
     }//End File Reading
    
	//Create a Print Writer object
	void getFileWriter()
    {
		try
    	{
			myInput = new PrintWriter(myFile);
    	}
       	catch (FileNotFoundException e)
       	{
       		System.out.println("PrintWriter; File Not Found.\n"  + e.getMessage());
       	}
    	
    }//End PrintWriter.	
      
	//Write an array of Strings to file 
    void writeString(String input[])
    {
    	try
    	{
    		getFileWriter();
    		myInput = new PrintWriter(myFile);
    		int i=0;
    		
    		//Until array is written to file.
    		while (input[i] !=null)
    		{
    			myInput.println(input[i]);
    			i++;
    		}
    		//close print writer
    		myInput.close();
    	}
    	catch (FileNotFoundException e)
      	{
    		System.out.println("WritingToFile; File Not Found.\n" + e.getMessage());
      	}
    	 
    }//End Writing to String
     
          
}//end Class