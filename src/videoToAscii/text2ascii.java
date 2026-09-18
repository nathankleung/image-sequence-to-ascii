package videoToAscii;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.Timer;

// Text2Ascii code from https://gist.github.com/AnimeshShaw/8e6ba43f2cfb75331f40
//BY:AnimeshShaw
public class text2ascii {

    private BufferedImage img;
    private double pixval;
    private PrintWriter prntwrt;
    private FileWriter filewrt;
    public static String artName = "frame";
    public static File folder = new File("");
    public static String fullWord = "";
    public static int delay = 0;
    public static JFrame frame = new JFrame();
    public static JTextArea label = new JTextArea("");
	public static JLabel l = new JLabel("");

    



    public text2ascii() {
        try {
            prntwrt = new PrintWriter(filewrt = new FileWriter(artName,
                    false));
        } catch (IOException ex) {
        }
    }
 
    public void convertToAscii(File imgname) {
        try {
            img = ImageIO.read((imgname));
        } catch (IOException e) {
        }

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color pixcol = new Color(img.getRGB(j, i));
                pixval = (((pixcol.getRed() * 0.30) + (pixcol.getBlue() * 0.59) + (pixcol
                        .getGreen() * 0.11)));
                print(strChar(pixval));
            }
            try {
                prntwrt.println("");
                prntwrt.flush();
                filewrt.flush();
            } catch (Exception ex) {
            }
        }
    }

    public String strChar(double g) {
        String str = " ";
        //customize ASCII output
        //I changed the strings to better suit the black and white for Bad Apple
        if (g >= 240) {
            str = " ";
        } else if (g >= 210) {
            str = " ";
        } else if (g >= 190) {
            str = " ";
        } else if (g >= 170) {
            str = ".";
        } else if (g >= 120) {
            str = "/";
        } else if (g >= 110) {
            str = "#";
        } else if (g >= 80) {
            str = "8";
        } else if (g >= 60) {
            str = "&";
        } else {
            str = "@";
        }
        return str;
    }

    public void print(String str) {
        try {
            prntwrt.print(str);
            prntwrt.flush();
            filewrt.flush();
        } catch (Exception ex) {
        }
    }
 // Text2Ascii code from https://gist.github.com/AnimeshShaw/8e6ba43f2cfb75331f40
  //BY:AnimeshShaw
    
    public static void main(String[] args) {
    	/*
    	 * This program takes images inside of a folder and converts them to ASCII text files
    	 * Then prints out those text files into the console
    	 * Can play back videos so long as they are converted to an image sequence and put into the folder(s)
    	 * Images are divided into folders to save storage space when converted to text files
    	 */
    	frame.setTitle("frame");
		frame.setLocation(800, 100);
		frame.setSize(800, 800);
		frame.setResizable(false);
		frame.setUndecorated(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 4));
		l.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 4));

		//Will need to change font size to fit within display
		//4 px fits, needs monospaced if in window

		label.setEditable(false);
		frame.setVisible(true); 
    	//Change this to whatever folder name you use
    	String folderName = "WorseApple";
    	
    	//delay between frames (in milliseconds)
    	delay = 5;

    	//run for i number of folders
    	//WorseApple1, WorseApple2,...WorseApple12
		//due to GitHub upload limits, only 1 folder is in the repo for demonstration
    	for(int i = 1; i <= 1; i++) 
    	{
    		createTxt(folderName+Integer.toString(i));
    	}
    	//Will need to change font size to fit within display
    	//4 px fits, needs monospaced font
    	
    	//Bad Apple credits
    	System.out.println("\n\n\nBad Apple!! feat.nomico\n(Shadow Animation Version)\n\n\nBad Apple!! Original Score\nZUN\n(Team Shang-hai Alice)\n\n\nShadow Animation\nあにら\n\n\nVocal\\nnomico\n\nArrange\nMasayoshi Minoshima\n(Alstroemeria Records)\n\nLyric\nHaruka\n\nRelease\n10.27.2009\n(Niconico Douga)");
    	System.exit(0);
    }//end main

public static String createTxt(String folderName) 
{
	//Original loop creates a list of files in the folder from the directory
	//Taken from StackOverflow, modified to suit needs of this program
	//By: RoflcoptrException
    folder = new File(folderName);
	File[] listOfFiles = folder.listFiles();

	//Sorts the images in the folder. Useful for MacOS
    listOfFiles = heapSort(listOfFiles);
    if(listOfFiles != null) 
    {
    	//loops based on number of files in folder
    	for (int i = 0; i < listOfFiles.length; i=i+1) {
    		
    		if (listOfFiles[i].isFile()) 
    		{
    			fullWord = "";	//resets the text for each file
    			artName = ("frame.txt"); //Changes name of text file for each frame
    			text2ascii obj = new text2ascii(); //creates the text file
    			obj.convertToAscii(listOfFiles[i]); //creates ASCII art on text file based on the image at i position in list of files
    	    	
    	    	//prints out the ASCII art text file
    			ArrayList<String> wordsOnFile = loadFileString(artName);
    			System.out.println("");

				for(String word:wordsOnFile) 
    			{
					fullWord = fullWord + word + "\n";
    			}
				System.out.println(fullWord);
    			System.out.println("");

    			createWindow(fullWord);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		} 
		else if (listOfFiles[i].isDirectory()) 
		{
			System.out.println("Directory " + listOfFiles[i].getName());
		}
    	}	
    }
	return fullWord;
}

	public static  String createWindow(String fullWord) 
	{
	
		
		label.setText(fullWord);
		frame.add(label);
		frame.pack();
		
		/*
		 * new Timer(200, (e) -> //window lifetime
		 * { 
			frame.setVisible(false); 
			frame.dispose(); 
		}).start();
		 */
		
		return null;
	}

	private static ArrayList<String> loadFileString(String dir)
	{
		File file = new File(dir);
		ArrayList<String> content = new ArrayList<String>();
		try 
		{
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) 
			{
				String word = scan.nextLine();
				content.add(word);
			}
			scan.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return content;
	}
	public static File[] heapSort(File[] arr) {
		int n = arr.length;

		// Build heap (rearrange array)
		for (int i = (n / 2) - 1; i >= 0; i--)
			heapify(arr, n, i);

		// One by one extract an element from heap
		for (int i = n - 1; i > 0; i--) {
			// Move current root to end
			File temp = arr[0];
			arr[0] = arr[i];
			arr[i] = temp;

			// call max heapify on the reduced heap
			heapify(arr, i, 0);
		}
		return arr;
	}

	// To heapify a subtree rooted with node i which is
	// an index in arr[]. n is size of heap
	public static void heapify(File[] arr, int n, int i) {
		int largest = i; // Initialize largest as root
		int left = 2 * i + 1; // left = 2*i + 1
		int right = 2 * i + 2; // right = 2*i + 2

		// If left child is larger than root
		if (left < n && arr[left].compareTo(arr[largest]) > 0)
			largest = left;

		// If right child is larger than largest so far
		if (right < n && arr[right].compareTo(arr[largest]) > 0)
			largest = right;

		// If largest is not root
		if (largest != i) {
			File swap = arr[i];
			arr[i] = arr[largest];
			arr[largest] = swap;

			// Recursively heapify the affected sub-tree
			heapify(arr, n, largest);
		}
	}
}
