package eightqueens.algorithm;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteSolutionToFile extends Thread{
	
	public static boolean destroy(String fileName) {
		File f = new File(fileName);
		if (f.exists())
			f.delete();
		return true;
	}
	
	public static boolean writeToFile(String fileName, int[] solution, int index) {
		int N = solution.length;
		File f = new File(fileName);
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		try(FileWriter fw = new FileWriter(fileName, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw)) {
			out.println();
			String line = "********* Loi giai thu " + index + "**********";
			out.println(line);
			line = "   +";
			for (int i = 0; i < N; i++)
				line += "---+";
			out.println(line);
			
			line = "   |";
			for (int i = 0; i < N; i++)
				line += " " + (char)(65+i) + " |";
			out.println(line);
			
			line = "   +";
			for (int i = 0; i < N; i++)
				line += "---+";
			out.println(line);
			
			for (int i = 0; i < N; i++) {
				if ((N-i) >= 10)
					line = (N-i) + " |";
				else line = " " + (N-i) + " |";
				for (int j = 0; j < N; j++) {
					if (solution[i] == j)
						line += " x |";
					else line += "   |";
				}
				out.println(line);
				line = "   +";
				for (int k = 0; k < N; k++)
					line += "---+";
				out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		int[] arr = {7, 5, 3, 1, 2, 4, 6, 0};
		writeToFile("sol.txt", arr, 1);
		try {
			Desktop.getDesktop().open(new File("sol.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
