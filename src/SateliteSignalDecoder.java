import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;


public class SateliteSignalDecoder {

	public static void main(String[] args) {
//		System.out.println((String) args[0]);
		Generator g = new Generator();
		SateliteSignalDecoder d = new SateliteSignalDecoder(g,(String) args[1]);
	}
	
	Generator g;
	ArrayList<LinkedList<Integer>> chipsequences;
	LinkedList<Integer> signal = new LinkedList<>();
	
	public SateliteSignalDecoder(Generator g, String path) {
		this.g = g;
		chipsequences = new ArrayList<>(g.getChipsequences());
		String line = "";
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			try {
				line = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		if (!line.equals(""))
		{
			String[] numbers = line.split(" ");
			for (int i = 0 ; i < numbers.length; i++)
			{
				signal.addLast(Integer.parseInt(numbers[i]));
			}
			makeMagic();
			
		}
		
	}
	
	public void makeMagic()
	{
		for(int i = 0; i < chipsequences.size(); i++)
		{
			for(int j = 0; j < 1023; j++)
			{
				double result = scalarProduct(chipsequences.get(i), signal);
				shiftList(chipsequences.get(i));
				if (result >= 0.8 || result < -0.8)
				{
					String satelite = "" + (i+1);
					if (result > 0)
						result = 1;
					else
						result = 0; 
					System.out.println("Satellite " + satelite + " has sent bit " + result + " (delta = " + j + ")");
					break;
				}
			}	
		}
	}
	
	public void shiftList(LinkedList<Integer> list)
	{
		int a = list.getLast();
		list.removeLast();
		list.addFirst(a);
	}
	
	public double scalarProduct(LinkedList<Integer> sequence, LinkedList<Integer> chip)
	{
		int currentValue = 0;
		for (int i = 0; i < sequence.size(); i++) 
		{
			currentValue += sequence.get(i) * chip.get(i);
		}
		double ret = (double) currentValue * 1.0 / 1023.0;
		
		return ret;
	}

}
