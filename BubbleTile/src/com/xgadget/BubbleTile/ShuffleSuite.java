package com.xgadget.BubbleTile;
import java.util.ArrayList;
import java.util.Random;

public class ShuffleSuite {
    ArrayList<IndexObject>     m_IndexList;
    ArrayList<IndexObject>    m_TempList;
    int           m_nCount;
    
    private int getRandomIndex()
    {
        int nRet = -1;
        
    	int nCount = m_TempList.size();
    	if(0 < nCount)
    	{
    		if(nCount == 1)
    		{
    			nRet = m_TempList.get(0).GetIndex();
    			m_TempList.clear();
    			return nRet;
    		}
    		
   //         srand(time(NULL));
    		Random generator = new Random();
    		int nRand = generator.nextInt();//rand();
    		if(nRand < 0)
    			nRand *= -1;
    		
    		int nRandIndex = nRand%nCount; 
    		
    		if(0 <= nRandIndex && nRandIndex < nCount)
    		{	
    			nRet = m_TempList.get(nRandIndex).GetIndex();
    			m_TempList.remove(nRandIndex);
    		}
    	}
        
        return nRet;
    }

    private void Shuffle()
    {
    	m_TempList.clear();
    	m_IndexList.clear();
    	for(int i = 0; i < m_nCount; ++i)
    	{
    		IndexObject index = new IndexObject(i);
    		m_TempList.add(index);
    	}
    	
    	int nIndex = this.getRandomIndex();
    	while(nIndex != -1)
    	{
    		IndexObject index = new IndexObject(nIndex);
    		m_IndexList.add(index);
    		nIndex = this.getRandomIndex();
    	}
        
    }

   public ShuffleSuite(int nCount)
    {
        m_nCount = nCount;
        m_IndexList = new ArrayList<IndexObject>();
        m_TempList = new ArrayList<IndexObject>();
        this.Shuffle();
    }

    public int GetValue(int nIndex)
    {
        int nRet = -1;
        
        if(!m_IndexList.equals(null) && 0 <= nIndex && nIndex < m_IndexList.size())
        {
            nRet = m_IndexList.get(nIndex).GetIndex();
        }
        
        return nRet;
    }
}
