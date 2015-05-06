import java.util.ArrayList;
import java.util.LinkedList;


public class Generator {
	
	LinkedList<Integer> base1 = new LinkedList<>();
	LinkedList<Integer> base2 = new LinkedList<>();
	ArrayList<LinkedList<Integer>> chipsequences = new ArrayList<>();

	public Generator()
	{
		resetBases();
		generateChipSequences();
	}
	
	public ArrayList<LinkedList<Integer>> getChipsequences()
	{
		return chipsequences;
	}
	
	private void generateChipSequences()
	{
		int[] x = {1,2,3,4,0,1,0,1,2,1,2,4,5,6,7,8,0,1,2,3,4,5,0,3};
		int[] y = {5,6,7,8,8,9,7,8,9,2,3,5,6,7,8,9,3,4,5,6,7,8,2,5};
		for(int i = 0; i < 24; i++)
		{
			resetBases();
			chipsequences.add(getChipSequence(x[i], y[i]));
		}		
	}
	
	private LinkedList<Integer> getChipSequence(int x,int y)
	{
		LinkedList<Integer> sequence = new LinkedList<>();
		for (int i = 0; i < 1023; i++)
		{
			int a = nextBase(x,y);
			if (a == 0)
			{
				sequence.addLast(-1);
			}
			else
			{
				sequence.addLast(a);
			}
		}
		return sequence;
	}
	
	private int nextBase(int x, int y)
	{
		int var1 = base1.get(2) ^ base1.get(9);
		int var2 = base2.get(x) ^ base2.get(y);
		int base1_9 = base1.get(9);
		
		base1.removeLast();
		base1.addFirst(var1);
		
		base2.addFirst(base2.get(1) ^ base2.get(2) ^ base2.get(5) ^ base2.get(7) ^ base2.get(8) ^ base2.get(9));
		base2.removeLast();
		
		return base1_9 ^ var2;
	}
	
	private void resetBases()
	{
		for (int i = 0; i < 10; i++) {
			base1.add(i, 1);
			base2.add(i, 1);
		}
	}
	

}
