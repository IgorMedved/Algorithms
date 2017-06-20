package Tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Tournament
{
	List <Integer> mTournament;
	Stack <Integer> mPath;
	
	Tournament (List<Integer> array)
	{
		int treeSize = array.size()*2-1;
		mTournament = new ArrayList<Integer>();
		for (int i = 0; i < treeSize/2; i++)
			mTournament.add(0);
		for (int i = 0; i < array.size(); i ++)
			mTournament.add(array.get(i));
		
		
	}
	
	// this function returns the minimum value from all the leafs of the tree
	public int runTournament()
	{
		for (int i = mTournament.size()/2-1; i>= 0; i--)// run for every node
		{
			
				int right = i*2+2; // right child of current node
				int left = i*2+1; // left child of current node
			
				if (mTournament.get(left) <= mTournament.get(right))
				{
					mTournament.set(i, mTournament.get(left));
				}
				else
				{
					mTournament.set(i, mTournament.get(right));
				}
			
				
		}
		return mTournament.get(0);
	}
	
	public int getMinLeafPosition()
	{
		
		if (mTournament.get(0)== Integer.MAX_VALUE)
			return 0;
		else
		{
			int value = mTournament.get(0);
			int position = 0;
			
			while (position < mTournament.size())
			{
				int left = position*2+1;
				int right = position*2+2;
				if (left<mTournament.size() && mTournament.get(left)==value)
					position = left;
				else if (right<mTournament.size()&& mTournament.get(right)== value)
					position = right;
				else
					return position;
			}
			return position;
		}
	}
	
	public void insertNew(int position, int value)
	{
		if (position < mTournament.size() && position >=0)
		{
			mTournament.set(position, value);
			position = parent(position);
			
			while (position >= 0)
			{
				int leftValue = mTournament.get(left(position));
				int rightValue = mTournament.get(right(position));
				
				mTournament.set(position, leftValue <= rightValue? leftValue:rightValue);
				position = parent(position);
			}
		}
	}
	
	public int left(int position)
	{
		return position*2+1;
	}
			
	public int right (int position)
	{
		return position*2+2;
	}
	
	public int parent (int position)
	{
		return position/2 - (position+1)%2;
	}
			  
	public int getRoot()
	{
		return mTournament.get(0);
	}

}
