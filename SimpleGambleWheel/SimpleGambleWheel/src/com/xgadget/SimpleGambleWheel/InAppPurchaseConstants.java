package com.xgadget.SimpleGambleWheel;

public class InAppPurchaseConstants 
{
	public static final int GOOGLEPLAYID = 0;
	public static final int AMAZONAPPSTOREID = 1;
	
	public static final String ID0 = "1000chips";
	public static final String ID1 = "2100chips";
	public static final String ID2 = "3200chips";
	public static final String ID3 = "4400chips";
	public static final String ID4 = "5800chips";
	public static final String ID5 = "7600chips";
	public static final String ID6 = "9200chips";
	public static final String ID7 = "10400chips";
	public static final String ID8 = "11800chips";
	public static final String ID9 = "13600chips";

	public static final int BUYCHIP0 = 1000;
	public static final int BUYCHIP1 = 2100;
	public static final int BUYCHIP2 = 3200;
	public static final int BUYCHIP3 = 4400;
	public static final int BUYCHIP4 = 5800;
	public static final int BUYCHIP5 = 7600;
	public static final int BUYCHIP6 = 9200;
	public static final int BUYCHIP7 = 10400;
	public static final int BUYCHIP8 = 11800;
	public static final int BUYCHIP9 = 13600;

	public static final int BUYITEMCOUNT = 10;
	
	public static int GetBuyChipsByID(String szID)
	{
		int nChips = 0;
		
		if(ID0.equals(szID) == true)
		{
			return BUYCHIP0;
		}
		if(ID1.equals(szID) == true)
		{
			return BUYCHIP1;
		}
		if(ID2.equals(szID) == true)
		{
			return BUYCHIP2;
		}
		if(ID3.equals(szID) == true)
		{
			return BUYCHIP3;
		}
		if(ID4.equals(szID) == true)
		{
			return BUYCHIP4;
		}
		if(ID5.equals(szID) == true)
		{
			return BUYCHIP5;
		}
		if(ID6.equals(szID) == true)
		{
			return BUYCHIP6;
		}
		if(ID7.equals(szID) == true)
		{
			return BUYCHIP7;
		}
		if(ID8.equals(szID) == true)
		{
			return BUYCHIP8;
		}
		if(ID9.equals(szID) == true)
		{
			return BUYCHIP9;
		}
				
		return nChips;		
	}

	public static int GetBuyChipsByIndex(int index)
	{
		int nchip = 0;
		
		if(index == 0)
		{
			return BUYCHIP0;
		}
		if(index == 1)
		{
			return BUYCHIP1;
		}
		if(index == 2)
		{
			return BUYCHIP2;
		}
		if(index == 3)
		{
			return BUYCHIP3;
		}
		if(index == 4)
		{
			return BUYCHIP4;
		}
		if(index == 5)
		{
			return BUYCHIP5;
		}
		if(index == 6)
		{
			return BUYCHIP6;
		}
		if(index == 7)
		{
			return BUYCHIP7;
		}
		if(index == 8)
		{
			return BUYCHIP8;
		}
		if(index == 9)
		{
			return BUYCHIP9;
		}
				
		return nchip;		
	}
	
	public static String GetID(int index)
	{
		String sID = null;
		
		if(index == 0)
		{
			return ID0;
		}
		if(index == 1)
		{
			return ID1;
		}
		if(index == 2)
		{
			return ID2;
		}
		if(index == 3)
		{
			return ID3;
		}
		if(index == 4)
		{
			return ID4;
		}
		if(index == 5)
		{
			return ID5;
		}
		if(index == 6)
		{
			return ID6;
		}
		if(index == 7)
		{
			return ID7;
		}
		if(index == 8)
		{
			return ID8;
		}
		if(index == 9)
		{
			return ID9;
		}
				
		return sID;		
	}
}
