package com.e_gadget.MindFire;

import java.util.Random;
import java.util.Vector;

import android.os.Bundle;

public class CDeck 
{
	public static final String KEY_DECK = "Deck_Key";
	public static final String KEY_DECK_PREFIX = "Deck_";

	private Vector<CCardBase> m_CardList;
	private Vector<CCardBase> m_TempCardList;
	
	public CDeck()
	{
		m_CardList = new Vector<CCardBase>();
		m_TempCardList = new Vector<CCardBase>();
		
		m_CardList.clear();
		Shuffle();
	}
	
	public boolean IsEmpty()
	{
		boolean bRet = (m_CardList.size() == 0);
		
		return bRet;
	}

	public void Shuffle ()
	{
		m_TempCardList.clear();
		m_CardList.clear();
		for(int i = 0; i < 52; ++i)
		{
			m_TempCardList.add(new CCardBase(i));
		}
		
		int nIndex = GetRandomIndex();
		while(nIndex != -1)
		{
			m_CardList.add(new CCardBase(nIndex));
			nIndex = GetRandomIndex();
		}
	}
	
	public int GetCardCount()
	{
		int nRet = m_CardList.size();
		return nRet;
	}
	
	public int Size()
	{
		return m_CardList.size();
	}

	public int DealCount()
	{
		return m_CardList.size()/4;
	}
	
	public CDeal Deal()
	{
		CDeal deal = null;
		
		if(0 < m_CardList.size())
		{	
			deal = new CDeal();
			
			for(int i = 0; i < 4; ++i)
			{	
				if(0 < m_CardList.size())
				{	
					deal.AddCard(m_CardList.get(0));
					m_CardList.remove(0);
				}					
			}				
		}
		
		return deal;
	}
	
	public int PopupCard()
	{
		int nRet = -1;

		if(0 < m_CardList.size())
		{	
			nRet = m_CardList.get(0).GetIndex();
			m_CardList.remove(0);
		}
		
		return nRet;
	}

	public String toString()
	{
		String sText = new String();

    	int nCount = m_CardList.size();
    	if(0 < nCount)
    	{	
    		int i, j;
    		for(i = 0; i < nCount;)
    		{
    		
    			for(j = 0; j < 4; ++j)
    			{
    				if(i < nCount)
    				{	
    					sText += m_CardList.elementAt(i).toString();  
    					sText += "__";  
    					++i;
    				}    					
    			}
    			sText += "\n";  
    		}
    	}
    	else
    	{
    		sText = "?";
    	}
    	return sText;
	}
	
	private int GetRandomIndex()
	{
		int nIndex = -1;
		
		int nCount = m_TempCardList.size();
		if(0 < nCount)
		{
			if(nCount == 1)
			{
				nIndex = m_TempCardList.elementAt(0).GetIndex();
				m_TempCardList.clear();
				return nIndex;
			}

			Random rand = new Random();

			int nRand = rand.nextInt();
			if(nRand < 0)
				nRand *= -1;
			
			int nRandIndex = nRand%nCount; 

			if(0 <= nRandIndex && nRandIndex < nCount)
			{	
				nIndex = m_TempCardList.elementAt(nRandIndex).GetIndex();
				m_TempCardList.remove(nRandIndex);
			}				
		}
		
		return nIndex;
	}
	
	public void SaveInstanceState(Bundle outState)
	{
		int nCount = m_CardList.size();
		outState.putInt(KEY_DECK, nCount);
		if(0 < nCount)
		{
			String strKey = new String("");
			for(int i = 0; i < nCount; ++i)
			{
				Integer indexKey = new Integer(i);
				strKey = KEY_DECK_PREFIX + indexKey.toString();
				outState.putInt(strKey, m_CardList.elementAt(i).GetIndex());
			}
		}
	}
	
	public void Clear()
	{
		m_TempCardList.clear();
		m_CardList.clear();
	}
	
	public void AddCard(int nIndex)
	{
		m_CardList.add(new CCardBase(nIndex));
	}
	
	public void LoadRestoreData(Bundle saveState)
	{
		Clear();
		int nCount =  saveState.getInt(KEY_DECK);
		if(0 < nCount)
		{
			String strKey = new String("");
			for(int i = 0; i < nCount; ++i)
			{
				Integer indexKey = new Integer(i);
				strKey = KEY_DECK_PREFIX + indexKey.toString();
				int index = saveState.getInt(strKey);
				AddCard(index);
			}
		}
	}
}
