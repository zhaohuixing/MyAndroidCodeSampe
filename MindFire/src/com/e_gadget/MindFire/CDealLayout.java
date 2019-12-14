package com.e_gadget.MindFire;

import android.graphics.Rect;

public class CDealLayout 
{
	public static final int m_n_HVGAP_SX = 320;  //The standard HVGA portrait mode client area width;
	public static final int m_n_HVGAP_SY = 455;  //The standard HVGA portrait mode client area height;
	public static final int m_n_HVGAL_SX = 480;  //The standard HVGA landscape mode client area width;
	public static final int m_n_HVGAL_SY = 295;  //The standard HVGA landscape mode client area height;
	public static final int m_n_QVGAL_SX = 320;  //The standard QVGA landscape mode client area width;
	public static final int m_n_QVGAL_SY = 215;  //The standard QVGA landscape mode client area height;
	public static final int m_n_QVGAP_SX = 240;  //The standard QVGA portrait mode client area width;
	public static final int m_n_QVGAP_SY = 295;  //The standard QVGA portrait mode client area height;

	public static final int m_n_HVGA_OUTLINE = 3;  //The standard HVGA outline width;
	public static final int m_n_HVGAP_STEP = 4;  //The standard HVGA outline width;
	public static final int m_n_HVGAL_STEP = 4;  //The standard HVGA outline width;
	public static final int m_n_QVGA_OUTLINE = 2;  //The standard HVGA outline width;
	public static final int m_n_QVGA_STEP = 1;  //The standard HVGA outline width;
	
	public static int m_nClinetX;  	//The client area width;
	public static int m_nClinetY;  	//The client area height;
	
	public static int m_HLWidth;	//High light outline width
	
	public static int m_CardWidth;	//The card width
	public static int m_CardHeight;	//The card width

	public static int m_nCardLotWidth;  //The card lot width: = m_CardWidth + m_HLWidth*2; 
	public static int m_nCardLotHeight; //The card lot height: = m_CardHeight + m_HLWidth*2; 

	public static int m_SignWidth;	//The "+" "-" "*" "/" "=" sign button width
	public static int m_SignHeight;	//The "+" "-" "*" "/" "=" sign button height

	public static int m_SignLotWidth;	//The "+" "-" "*" "/" "=" sign button area width: = m_SignWidth + m_HLWidth*2;    
	public static int m_SignLotHeight;	//The "+" "-" "*" "/" "=" sign button area height: = m_SignHeight + m_HLWidth*2;    

	public static int m_DealWidth;	//The "Deal" button width
	public static int m_DealHeight;	//The "Deal" button height

	public static int m_DealLotWidth;	//The "Deal" button area width: = m_DealWidth + m_HLWidth*2;     
	public static int m_DealLotHeight;	//The "Deal" button area height: = m_DealHeight + m_HLWidth*2;     
	
	public static int m_BCardStartX;	//The basic cards area left position 
	public static int m_BCardStartY;	//The basic cards area top position

	public static int m_TCardStartX;	//The temporary cards area left position
	public static int m_TCardStartY;	//The temporary cards area top position

	public static int m_OperandX1;		//The first operand card area left position
	public static int m_OperandY1;		//The first operand card area top position

	public static int m_OperandX2;		//The second operand card area left position
	public static int m_OperandY2;		//The second operand card area top position

	public static int m_OperatorX;		//The operator area left position
	public static int m_OperatorY;		//The operator area top position

	public static int m_CalculateX;		//The "=" button left position
	public static int m_CalculateY;		//The "=" button top position

	public static int m_ResultX;		//The result card left position
	public static int m_ResultY;		//The result card top position

	public static int m_SignsBarStartX;	//The sign button group area left position 
	public static int m_SignsBarStartY;	//The sign button group area top position

	public static int m_CancelStartX;	//The "Cancel" button area left position 
	public static int m_CancelStartY;	//The "Cancel" button area top position
	
	public static int m_DealStartX;		//The "Deal" button area left position 
	public static int m_DealStartY;		//The "Deal" button area top position

	public static int m_OuterR;			//The outer radius values for the phantom card drawing.
	public static int m_InnerR;			//The inner radius values for the phantom card drawing.
	public static int m_Inset;			//The inset values for the phantom card drawing.

	public static int m_DealSpriteWidth;		//The desktop animation image width 
	public static int m_DealSpriteHeight;		//The desktop animation image height
	public static Rect m_DealSpriteRect;		//The desktop animation image area 

	public static int m_ScoreWidth;				//The star animation image width 
	public static int m_ScoreHeight;				//The star animation image height
	public static int m_ScoreX1;				//The first star left 
	public static int m_ScoreY1;				//The first star top 
	public static int m_ScoreMargin;				//The distance between stars 
	
	private static int m_nStarWidth;  	
	private static int m_nStarHeight;
	
	private static boolean m_bInitialized = false;
	
	public static boolean IsInitialized()
	{
		return m_bInitialized;
	}
	
	public static boolean IsVerticalScreen()
	{
		boolean bRet = (m_nClinetX < m_nClinetY);
		return bRet;
	}
	
	public static boolean IsLargeScreen()
	{
		boolean bRet = (400 <= m_nClinetX || (320 <= m_nClinetX && 400 <= m_nClinetY));
		//boolean bRet = false; //(400 <= m_nClinetX || (320 <= m_nClinetX && 400 <= m_nClinetY));
		return bRet;
	}
	
	public static void Initialize(int nClientX, int nClientY)
	{
		m_nClinetX = nClientX;
		m_nClinetY = nClientY;
    	m_nStarWidth = CGameHelper.GetStarImageWidth();//*nBase/m_n_STD_UNIT;  	
    	m_nStarHeight = CGameHelper.GetStarImageHeight();//*nBase/m_n_STD_UNIT;
		
   	
		if(m_nClinetX == m_n_HVGAP_SX && 400 < m_nClinetY) // == m_n_HVGAP_SY)
		{
			CGameHelper.SetScreenMode(CGameHelper.m_SMode_HVQA_P);
			Initialize_HVGAP();
			CResultLayout.Initialize_HVGAP();
		}
		else if (m_nClinetX == m_n_HVGAL_SX) //(m_nClinetX == m_n_HVGAL_SX && m_nClinetY == m_n_HVGAL_SY)
		{
			if(m_nClinetY < m_nClinetX )
			{	
				CGameHelper.SetScreenMode(CGameHelper.m_SMode_HVQA_L);
			//Initialize_HVGAL();
				Initialize_HVGAP();
				CResultLayout.Initialize_HVGAL();
			}
			else
			{
				CGameHelper.SetScreenMode(CGameHelper.m_SMode_VQA_LARGE);
				Initialize_LARGE();
				//CGameHelper.SetScreenMode(CGameHelper.m_SMode_HVQA_P);
				//Initialize_HVGAP();
				CResultLayout.Initialize_HVGAP();
			}
		}
		else if(m_nClinetX == m_n_QVGAL_SX && m_nClinetY < 300)
		{
			CGameHelper.SetScreenMode(CGameHelper.m_SMode_QVQA_L);
			Initialize_QVGA();
			CResultLayout.Initialize_QVGAP();
		}
		else if(m_nClinetX == m_n_QVGAP_SX)
		{
			CGameHelper.SetScreenMode(CGameHelper.m_SMode_QVQA_P);
			Initialize_QVGA();
			CResultLayout.Initialize_QVGAL();
		}
		else if(360 <= m_nClinetX)
		{
			CGameHelper.SetScreenMode(CGameHelper.m_SMode_VQA_LARGE);
			Initialize_LARGE();
			CResultLayout.Initialize_HVGAP();
		}

		m_bInitialized = true;
	}

	private static void Initialize_LARGE()
	{
		m_HLWidth = m_n_HVGA_OUTLINE-1;
		m_CardWidth = CGameHelper.GetRealCardWidth();
		m_CardHeight = CGameHelper.GetRealCardHeight();
		m_nCardLotWidth = m_CardWidth + m_HLWidth*2; 
		m_nCardLotHeight = m_CardHeight + m_HLWidth*2; 

		m_SignWidth = CGameHelper.GetRealSignWidth();
		m_SignHeight = CGameHelper.GetRealSignHeight();

		m_SignLotWidth = m_SignWidth + m_HLWidth*2;    
		m_SignLotHeight = m_SignHeight + m_HLWidth*2;    

		m_DealWidth = CGameHelper.GetRealDealWidth();
		m_DealHeight = CGameHelper.GetRealDealHeight();

		m_DealLotWidth = m_DealWidth + m_HLWidth*2;     
		m_DealLotHeight = m_DealHeight + m_HLWidth*2;     

		m_BCardStartX = (m_nClinetX - m_nCardLotWidth*4)/2;
		m_BCardStartY = 0;

		m_TCardStartX = m_BCardStartX;
		if(IsVerticalScreen() == false)
			m_TCardStartY = m_nCardLotHeight + m_HLWidth;
		else
			m_TCardStartY = m_nCardLotHeight + m_HLWidth*2;

		m_SignsBarStartX = m_HLWidth;
		m_SignsBarStartY = m_nClinetY - (m_SignLotHeight+m_HLWidth);
		if(IsVerticalScreen() == false)
			m_SignsBarStartY += 2;

		m_CancelStartX = m_SignsBarStartX + 4*m_SignLotWidth + m_HLWidth*6; 
		m_CancelStartY = m_SignsBarStartY;
		
		m_DealStartX = m_CancelStartX + m_SignLotWidth + m_HLWidth*6;
		m_DealStartY = m_SignsBarStartY;

		int nTemp = m_n_HVGAP_STEP;
		m_OperandX1 = m_SignsBarStartX;
		m_OperandY1 = m_SignsBarStartY - (m_nCardLotHeight + nTemp);
		if(IsVerticalScreen() == false)
			m_OperandY1 += 4;

		m_OperatorX = m_OperandX1 + m_nCardLotWidth + nTemp;		
		m_OperatorY = m_OperandY1 + (m_nCardLotHeight - m_SignLotHeight)/2;	

		m_OperandX2 = m_OperatorX + m_SignLotWidth + nTemp;
		m_OperandY2 = m_OperandY1;

		m_CalculateX = m_OperandX2 + m_nCardLotWidth + nTemp;
		m_CalculateY = m_OperatorY;

		m_ResultX = m_CalculateX + m_SignLotWidth + nTemp;
		m_ResultY = m_OperandY1;

		m_OuterR = 2;
		m_InnerR =2;
		m_Inset = 1;

		m_DealSpriteWidth = 50;		//The desktop animation image width 
		m_DealSpriteHeight = 50;		//The desktop animation image height
		m_DealSpriteRect = new Rect();		//The desktop animation image area
		m_DealSpriteRect.left = (m_nClinetX - m_DealSpriteWidth)/2;
		m_DealSpriteRect.top = (m_nClinetY - m_DealSpriteHeight)/2;
		m_DealSpriteRect.bottom = m_DealSpriteRect.top + m_DealSpriteHeight;
		m_DealSpriteRect.right = m_DealSpriteRect.left + m_DealSpriteWidth;
		
		m_ScoreMargin = m_n_HVGA_OUTLINE-1;
		m_ScoreWidth = 24; 
		m_ScoreHeight = 24;
		m_ScoreX1 = m_nClinetX-(m_ScoreWidth + m_n_HVGA_OUTLINE); 
		m_ScoreY1 = m_ResultY - (m_ScoreHeight+m_ScoreMargin)*4-4; 
	}

	private static void Initialize_HVGAP()
	{
		m_HLWidth = m_n_HVGA_OUTLINE-1;
		m_CardWidth = CGameHelper.GetRealCardWidth();
		m_CardHeight = CGameHelper.GetRealCardHeight();
		m_nCardLotWidth = m_CardWidth + m_HLWidth*2; 
		m_nCardLotHeight = m_CardHeight + m_HLWidth*2; 

		m_SignWidth = CGameHelper.GetRealSignWidth();
		m_SignHeight = CGameHelper.GetRealSignHeight();

		m_SignLotWidth = m_SignWidth + m_HLWidth*2;    
		m_SignLotHeight = m_SignHeight + m_HLWidth*2;    

		m_DealWidth = CGameHelper.GetRealDealWidth();
		m_DealHeight = CGameHelper.GetRealDealHeight();

		m_DealLotWidth = m_DealWidth + m_HLWidth*2;     
		m_DealLotHeight = m_DealHeight + m_HLWidth*2;     

		m_BCardStartX = (m_nClinetX - m_nCardLotWidth*4 + m_HLWidth*3)/2;
		m_BCardStartY = 0;

		m_TCardStartX = m_BCardStartX;
		m_TCardStartY = m_nCardLotHeight + m_HLWidth*2;

		m_SignsBarStartX = m_HLWidth;
		m_SignsBarStartY = m_nClinetY - (m_SignLotHeight+m_HLWidth);

		m_CancelStartX = m_SignsBarStartX + 4*m_SignLotWidth + m_HLWidth*6; 
		m_CancelStartY = m_SignsBarStartY;
		
		m_DealStartX = m_CancelStartX + m_SignLotWidth + m_HLWidth*6;
		m_DealStartY = m_SignsBarStartY;

		int nTemp = m_n_HVGAP_STEP;
		m_OperandX1 = m_SignsBarStartX;
		m_OperandY1 = m_SignsBarStartY - (m_nCardLotHeight + nTemp);

		m_OperatorX = m_OperandX1 + m_nCardLotWidth + nTemp;		
		m_OperatorY = m_OperandY1 + (m_nCardLotHeight - m_SignLotHeight)/2;	

		m_OperandX2 = m_OperatorX + m_SignLotWidth + nTemp;
		m_OperandY2 = m_OperandY1;

		m_CalculateX = m_OperandX2 + m_nCardLotWidth + nTemp;
		m_CalculateY = m_OperatorY;

		m_ResultX = m_CalculateX + m_SignLotWidth + nTemp;
		m_ResultY = m_OperandY1;

		m_OuterR = 2;
		m_InnerR =2;
		m_Inset = 1;

		m_DealSpriteWidth = 50;		//The desktop animation image width 
		m_DealSpriteHeight = 50;		//The desktop animation image height
		m_DealSpriteRect = new Rect();		//The desktop animation image area
		m_DealSpriteRect.left = (m_nClinetX - m_DealSpriteWidth)/2;
		m_DealSpriteRect.top = (m_nClinetY - m_DealSpriteHeight)/2;
		m_DealSpriteRect.bottom = m_DealSpriteRect.top + m_DealSpriteHeight;
		m_DealSpriteRect.right = m_DealSpriteRect.left + m_DealSpriteWidth;
		
		m_ScoreMargin = m_n_HVGA_OUTLINE-1;
		m_ScoreWidth = 24; 
		m_ScoreHeight = 24;
		m_ScoreX1 = m_nClinetX-(m_ScoreWidth + m_n_HVGA_OUTLINE); 
		m_ScoreY1 = m_ResultY - (m_ScoreHeight+m_ScoreMargin)*4-4; 
	}
	
	private static void Initialize_HVGAL()
	{
		m_HLWidth = m_n_HVGA_OUTLINE-1;
		m_CardWidth = CGameHelper.GetRealCardWidth();
		m_CardHeight = CGameHelper.GetRealCardHeight();
		m_nCardLotWidth = m_CardWidth + m_HLWidth*2; 
		m_nCardLotHeight = m_CardHeight + m_HLWidth*2; 

		m_SignWidth = CGameHelper.GetRealSignWidth();
		m_SignHeight = CGameHelper.GetRealSignHeight();

		m_SignLotWidth = m_SignWidth + m_HLWidth*2;    
		m_SignLotHeight = m_SignHeight + m_HLWidth*2;    

		m_DealWidth = CGameHelper.GetRealDealWidth();
		m_DealHeight = CGameHelper.GetRealDealHeight();

		m_DealLotWidth = m_DealWidth + m_HLWidth*2;     
		m_DealLotHeight = m_DealHeight + m_HLWidth*2;     
		
		
		m_BCardStartX = 0; 
		m_BCardStartY = 0;//(m_nClinetY - m_nCardLotHeight*4 + m_HLWidth*3)/2;

		m_TCardStartX = m_nCardLotWidth + m_HLWidth*2;
		m_TCardStartY = m_BCardStartY; 

		m_SignsBarStartX = m_nClinetX - (m_SignLotWidth+m_HLWidth);
		m_SignsBarStartY = m_HLWidth;

		m_CancelStartX = m_SignsBarStartX; 
		m_CancelStartY = m_SignsBarStartY + 4*m_SignLotHeight + m_HLWidth*6;
		
		m_DealStartX = m_SignsBarStartX; 
		m_DealStartY = m_CancelStartY + m_SignLotHeight + m_HLWidth*6;
		
		int nTemp = m_n_HVGAL_STEP;
		m_OperandX1 = m_SignsBarStartX - (m_nCardLotWidth + nTemp);
		m_OperandY1 = m_SignsBarStartY;

		m_OperatorX = m_OperandX1 + (m_nCardLotWidth - m_SignLotWidth)/2;		
		m_OperatorY = m_OperandY1 + m_nCardLotHeight;// + nTemp;	

		m_OperandX2 = m_OperandX1;
		m_OperandY2 = m_OperatorY + m_SignLotHeight;// + nTemp;

		m_CalculateX = m_OperatorX;
		m_CalculateY = m_OperandY2 + m_nCardLotHeight;// + nTemp;

		m_ResultX = m_OperandX1;  
		m_ResultY =	m_CalculateY + m_SignLotHeight;// + nTemp;

		m_OuterR = 2;
		m_InnerR =2;
		m_Inset = 1;

		m_DealSpriteWidth = 50;		//The desktop animation image width 
		m_DealSpriteHeight = 50;		//The desktop animation image height
		m_DealSpriteRect = new Rect();		//The desktop animation image area
		m_DealSpriteRect.left = (m_nClinetX - m_DealSpriteWidth)/2;
		m_DealSpriteRect.top = (m_nClinetY - m_DealSpriteHeight)/2;
		m_DealSpriteRect.bottom = m_DealSpriteRect.top + m_DealSpriteHeight;
		m_DealSpriteRect.right = m_DealSpriteRect.left + m_DealSpriteWidth;

		m_ScoreMargin = m_n_HVGA_OUTLINE-1;
		m_ScoreWidth = 24; 
		m_ScoreHeight = 24;
		m_ScoreX1 = m_nClinetX-(m_ScoreWidth + m_n_HVGA_OUTLINE); 
		m_ScoreY1 = m_ResultY - (m_ScoreHeight+m_ScoreMargin)*4; 
		
	}

	private static void Initialize_QVGA()
	{
		m_HLWidth = m_n_QVGA_OUTLINE;
		m_CardWidth = CGameHelper.GetRealCardWidth();
		m_CardHeight = CGameHelper.GetRealCardHeight();
		m_nCardLotWidth = m_CardWidth + m_HLWidth*2; 
		m_nCardLotHeight = m_CardHeight + m_HLWidth*2; 

		m_SignWidth = CGameHelper.GetRealSignWidth();
		m_SignHeight = CGameHelper.GetRealSignHeight();

		m_SignLotWidth = m_SignWidth + m_HLWidth*2;    
		m_SignLotHeight = m_SignHeight + m_HLWidth*2;    

		m_DealWidth = CGameHelper.GetRealDealWidth();
		m_DealHeight = CGameHelper.GetRealDealHeight();

		m_DealLotWidth = m_DealWidth + m_HLWidth*2;     
		m_DealLotHeight = m_DealHeight + m_HLWidth*2;     

		m_BCardStartX = (m_nClinetX - m_nCardLotWidth*4 + m_HLWidth*3)/2;
		m_BCardStartY = 0;

		m_TCardStartX = m_BCardStartX;
		m_TCardStartY = m_nCardLotHeight + m_HLWidth*2;

		m_SignsBarStartX = m_HLWidth;
		m_SignsBarStartY = m_nClinetY - (m_SignLotHeight+m_HLWidth);

		m_CancelStartX = m_SignsBarStartX + 4*m_SignLotWidth + m_HLWidth*6; 
		m_CancelStartY = m_SignsBarStartY;
		
		m_DealStartX = m_CancelStartX + m_SignLotWidth + m_HLWidth*6;
		m_DealStartY = m_SignsBarStartY;

		int nTemp = m_n_QVGA_STEP;
		m_OperandX1 = m_SignsBarStartX;
		m_OperandY1 = m_SignsBarStartY - (m_nCardLotHeight + nTemp);

		m_OperatorX = m_OperandX1 + m_nCardLotWidth + nTemp;		
		m_OperatorY = m_OperandY1 + (m_nCardLotHeight - m_SignLotHeight)/2;	

		m_OperandX2 = m_OperatorX + m_SignLotWidth + nTemp;
		m_OperandY2 = m_OperandY1;

		m_CalculateX = m_OperandX2 + m_nCardLotWidth + nTemp;
		m_CalculateY = m_OperatorY;

		m_ResultX = m_CalculateX + m_SignLotWidth + nTemp;
		m_ResultY = m_OperandY1;

		m_OuterR = 2;
		m_InnerR =2;
		m_Inset = 1;

		m_DealSpriteWidth = 40;		//The desktop animation image width 
		m_DealSpriteHeight = 40;		//The desktop animation image height
		m_DealSpriteRect = new Rect();		//The desktop animation image area
		m_DealSpriteRect.left = (m_nClinetX - m_DealSpriteWidth)/2;
		m_DealSpriteRect.top = (m_nClinetY - m_DealSpriteHeight)/2;
		m_DealSpriteRect.bottom = m_DealSpriteRect.top + m_DealSpriteHeight;
		m_DealSpriteRect.right = m_DealSpriteRect.left + m_DealSpriteWidth;
		
		m_ScoreMargin = m_n_QVGA_OUTLINE-1;
		m_ScoreWidth = 16; 
		m_ScoreHeight = 16;
		m_ScoreX1 = m_nClinetX-(m_ScoreWidth + m_n_QVGA_OUTLINE-1); 
		m_ScoreY1 = m_ResultY - (m_ScoreHeight+m_ScoreMargin)*4-2; 
		
	}
	
	public static Rect GetBasicCardRect(int index)
	{
		Rect rect = new Rect(0, 0, 0, 0);

		rect.left = m_BCardStartX + m_HLWidth + index*(m_CardWidth+m_HLWidth);
		rect.right = rect.left + m_CardWidth;
		rect.top = m_BCardStartY + m_HLWidth;
		rect.bottom = rect.top + m_CardHeight;
		return rect;
	}

	public static Rect GetBasicCardBoundary(int index)
	{
		Rect rect = new Rect(0, 0, 0, 0);
	
		rect.left = m_BCardStartX + index*(m_CardWidth+m_HLWidth);
		rect.right = rect.left + m_nCardLotWidth;
		rect.top = m_BCardStartY;
		rect.bottom = rect.top + m_nCardLotHeight;
		return rect;
	}

	public static Rect GetTempCardRect(int index)
	{
		Rect rect = new Rect(0, 0, 0, 0);
	
		rect.left = m_TCardStartX + m_HLWidth + index*(m_CardWidth + m_HLWidth);
		rect.right = rect.left + m_CardWidth;
		rect.top = m_TCardStartY + m_HLWidth;
		rect.bottom = rect.top + m_CardHeight;
		return rect;
	}

	public static Rect GetTempCardBoundary(int index)
	{
		Rect rect = new Rect(0, 0, 0, 0);
	
		rect.left = m_TCardStartX + index*(m_CardWidth+m_HLWidth);
		rect.right = rect.left + m_nCardLotWidth;
		rect.top = m_TCardStartY;
		rect.bottom = rect.top + m_nCardLotHeight;
		return rect;
	}
	
	public static Rect GetOperand1Rect()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_OperandX1 + m_HLWidth;
		rect.right = rect.left + m_CardWidth;
		rect.top = m_OperandY1 + m_HLWidth;
		rect.bottom = rect.top + m_CardHeight;
		
		return rect;
	}

	public static Rect GetOperand1Boundary()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_OperandX1;
		rect.right = rect.left + m_nCardLotWidth;
		rect.top = m_OperandY1;
		rect.bottom = rect.top + m_nCardLotHeight;
		
		return rect;
	}

	public static Rect GetOperand2Rect()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_OperandX2 + m_HLWidth;
		rect.right = rect.left + m_CardWidth;
		rect.top = m_OperandY2 + m_HLWidth;
		rect.bottom = rect.top + m_CardHeight;
		
		return rect;
	}

	public static Rect GetOperand2Boundary()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_OperandX2;
		rect.right = rect.left + m_nCardLotWidth;
		rect.top = m_OperandY2;
		rect.bottom = rect.top + m_nCardLotHeight;
		
		return rect;
	}

	public static Rect GetOperatorRect()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_OperatorX + m_HLWidth;
		rect.right = rect.left + m_SignWidth;
		rect.top = m_OperatorY + m_HLWidth;
		rect.bottom = rect.top + m_SignHeight;
		
		return rect;
	}

	public static Rect GetOperatorBoundary()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_OperatorX;
		rect.right = rect.left + m_SignLotWidth;
		rect.top = m_OperatorY;
		rect.bottom = rect.top + m_SignLotHeight;
		
		return rect;
	}

	public static Rect GetCalculateRect()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_CalculateX + m_HLWidth;
		rect.right = rect.left + m_SignWidth;
		rect.top = m_CalculateY + m_HLWidth;
		rect.bottom = rect.top + m_SignHeight;
		
		return rect;
	}

	public static Rect GetCalculateBoundary()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_CalculateX;
		rect.right = rect.left + m_SignLotWidth;
		rect.top = m_CalculateY;
		rect.bottom = rect.top + m_SignLotHeight;
		
		return rect;
	}

	public static Rect GetResultCardRect()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_ResultX + m_HLWidth;
		rect.right = rect.left + m_CardWidth;
		rect.top = m_ResultY + m_HLWidth;
		rect.bottom = rect.top + m_CardHeight;
		
		return rect;
	}

	public static Rect GetResultCardBoundary()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_ResultX;
		rect.right = rect.left + m_nCardLotWidth;
		rect.top = m_ResultY;
		rect.bottom = rect.top + m_nCardLotHeight;
		
		return rect;
	}
	
	public static Rect GetSignsRect(int index)
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_SignsBarStartX + m_HLWidth + index*(m_SignWidth + m_HLWidth);
		rect.right = rect.left + m_SignWidth;
		rect.top = m_SignsBarStartY + m_HLWidth;
		rect.bottom = rect.top + m_SignHeight;
		return rect;
	}

	public static Rect GetSignsBoundary(int index)
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_SignsBarStartX + index*(m_SignWidth + m_HLWidth);
		rect.right = rect.left + m_SignLotWidth;
		rect.top = m_SignsBarStartY;
		rect.bottom = rect.top + m_SignLotHeight;
		return rect;
	}

	public static Rect GetCancelButtonRect()
	{
		Rect rect = new Rect(0, 0, 0, 0);

		rect.left = m_CancelStartX + m_HLWidth;
		rect.right = rect.left + m_SignWidth;
		rect.top = m_CancelStartY + m_HLWidth;
		rect.bottom = rect.top + m_SignHeight;

		return rect;
	}

	public static Rect GetCancelButtonBoundary()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_CancelStartX;
		rect.right = rect.left + m_SignLotWidth;
		rect.top = m_CancelStartY;
		rect.bottom = rect.top + m_SignLotHeight;
		
		return rect;
	}
	
	
	public static Rect GetNewDealRect()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_DealStartX + m_HLWidth;
		rect.right = rect.left + m_DealWidth;
		rect.top = m_DealStartY + m_HLWidth;
		rect.bottom = rect.top + m_DealHeight;

		return rect;
	}

	public static Rect GetNewDealBoundary()
	{
		Rect rect = new Rect(0, 0, 0, 0);
		
		rect.left = m_DealStartX;
		rect.right = rect.left + m_DealLotWidth;
		rect.top = m_DealStartY;
		rect.bottom = rect.top + m_DealLotHeight;
		
		return rect;
	}

	public static CLayoutCursor NextCursor(CLayoutCursor cursor)
	{
		CLayoutCursor newcur = new CLayoutCursor();
		
		int OldType = cursor.GetType();
		int OldIndex = cursor.GetIndex();
		int NewType = cursor.GetType();
		int NewIndex = cursor.GetIndex();
		
		switch(OldType)
		{
			case CLayoutCursor.TYPE_NONE:
				NewType = CLayoutCursor.TYPE_BASIC_CARD;
				NewIndex = 0;
				break;
			case CLayoutCursor.TYPE_BASIC_CARD:
				if(OldIndex < 0)
				{	
					NewIndex = 0;
					NewType = OldType;
				}					
				else
				{	
					if(OldIndex < 3)
					{	
						NewIndex = OldIndex+1;
						NewType = OldType;
					}
					else
					{
						NewIndex = 0;
						NewType = CLayoutCursor.TYPE_TEMP_CARD;
					}
				}
				break;
			case CLayoutCursor.TYPE_TEMP_CARD:
				if(OldIndex < 0)
				{	
					NewIndex = 0;
					NewType = OldType;
				}					
				else
				{	
					if(OldIndex < 3)
					{	
						NewIndex = OldIndex+1;
						NewType = OldType;
					}
					else
					{
						NewIndex = 0;
						NewType = CLayoutCursor.TYPE_OPERAND_CARD;
					}
				}
				break;
			case CLayoutCursor.TYPE_OPERAND_CARD:
				if(OldIndex < 0)
				{	
					NewIndex = 0;
					NewType = OldType;
				}					
				else
				{	
					if(OldIndex == 0)
					{	
						NewIndex = 0;
						NewType = CLayoutCursor.TYPE_OPERATOR_BUTTON;
					}
					else
					{
						NewIndex = 0;
						NewType = CLayoutCursor.TYPE_CALCULATE_BUTTON;
					}
				}
				break;
			case CLayoutCursor.TYPE_OPERATOR_BUTTON:
				NewIndex = 1;
				NewType = CLayoutCursor.TYPE_OPERAND_CARD;
				break;
			case CLayoutCursor.TYPE_CALCULATE_BUTTON:
				NewIndex = 0;
				NewType = CLayoutCursor.TYPE_RESULT_CARD;
				break;
			case CLayoutCursor.TYPE_RESULT_CARD:
				NewIndex = 0;
				NewType = CLayoutCursor.TYPE_SIGNSBAR_BUTTON;
				break;
			case CLayoutCursor.TYPE_SIGNSBAR_BUTTON:
				if(OldIndex < 0)
				{	
					NewIndex = 0;
					NewType = OldType;
				}					
				else
				{	
					if(OldIndex < 3)
					{	
						NewIndex = OldIndex+1;
						NewType = OldType;
					}
					else
					{
						NewIndex = 0;
						NewType = CLayoutCursor.TYPE_CANCEL_BUTTON;
					}
				}
				break;
			case CLayoutCursor.TYPE_CANCEL_BUTTON:
				NewIndex = 0;
				NewType = CLayoutCursor.TYPE_NEWDEAL_BUTTON;
				break;
			case CLayoutCursor.TYPE_NEWDEAL_BUTTON:
				NewIndex = 0;
				NewType = CLayoutCursor.TYPE_BASIC_CARD;
				break;
		}
		
		newcur.Set(NewType, NewIndex);
		
		return newcur;
	}

	public static CLayoutCursor PrevCursor(CLayoutCursor cursor)
	{
		CLayoutCursor newcur = new CLayoutCursor();
		
		int OldType = cursor.GetType();
		int OldIndex = cursor.GetIndex();
		int NewType = cursor.GetType();
		int NewIndex = cursor.GetIndex();
		
		switch(OldType)
		{
			case CLayoutCursor.TYPE_NONE:
				NewType = CLayoutCursor.TYPE_BASIC_CARD;
				NewIndex = 0;
				break;
			case CLayoutCursor.TYPE_BASIC_CARD:
				if(0 < OldIndex)
				{	
					NewIndex = OldIndex-1;
					NewType = OldType;
				}					
				else
				{	
					NewIndex = 0;
					NewType = CLayoutCursor.TYPE_NEWDEAL_BUTTON;
				}
				break;
			case CLayoutCursor.TYPE_TEMP_CARD:
				if(0 < OldIndex)
				{	
					NewIndex = OldIndex-1;
					NewType = OldType;
				}					
				else
				{	
					NewIndex = 3;
					NewType = CLayoutCursor.TYPE_BASIC_CARD;
				}
				break;
			case CLayoutCursor.TYPE_OPERAND_CARD:
				if(OldIndex == 0)
				{	
					NewIndex = 3;
					NewType = CLayoutCursor.TYPE_TEMP_CARD;
				}					
				else
				{	
					NewIndex = 0;
					NewType = CLayoutCursor.TYPE_OPERATOR_BUTTON;
				}
				break;
			case CLayoutCursor.TYPE_OPERATOR_BUTTON:
				NewIndex = 0;
				NewType = CLayoutCursor.TYPE_OPERAND_CARD;
				break;
			case CLayoutCursor.TYPE_CALCULATE_BUTTON:
				NewIndex = 1;
				NewType = CLayoutCursor.TYPE_OPERAND_CARD;
				break;
			case CLayoutCursor.TYPE_RESULT_CARD:
				NewIndex = 0;
				NewType = CLayoutCursor.TYPE_CALCULATE_BUTTON;
				break;
			case CLayoutCursor.TYPE_SIGNSBAR_BUTTON:
				if(0 < OldIndex)
				{	
					NewIndex = OldIndex-1;
					NewType = OldType;
				}					
				else
				{	
					NewIndex = 0;
					NewType = CLayoutCursor.TYPE_RESULT_CARD;
				}
				break;
			case CLayoutCursor.TYPE_CANCEL_BUTTON:
				NewIndex = 3;
				NewType = CLayoutCursor.TYPE_SIGNSBAR_BUTTON;
				break;
			case CLayoutCursor.TYPE_NEWDEAL_BUTTON:
				NewIndex = 0;
				NewType = CLayoutCursor.TYPE_CANCEL_BUTTON;
				break;
		}
		
		newcur.Set(NewType, NewIndex);
		
		return newcur;
	}
	
	/*
	 * Note: if the cursor type is temporary card, then the index of the cursor must be the index among real displayed 
	 * temporary cards, not the index in the temporary card array.
	 */
	public static Rect GetRect(CLayoutCursor cursor)
	{
		Rect rect = new Rect(0, 0, 0, 0);

		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();

		switch(nType)
		{
			case CLayoutCursor.TYPE_NONE:
				break;
			case CLayoutCursor.TYPE_BASIC_CARD:
				rect = GetBasicCardRect(nIndex);
				break;
			case CLayoutCursor.TYPE_TEMP_CARD:
				/*
				 * Note: The index is the index among displayed temporary cards.
				 * not the index in temporary card array
				 */
				rect = GetTempCardRect(nIndex);
				break;
			case CLayoutCursor.TYPE_OPERAND_CARD:
				if(nIndex == 0)
					rect = GetOperand1Rect();
				else
					rect = GetOperand2Rect();
				break;
			case CLayoutCursor.TYPE_OPERATOR_BUTTON:
				rect = GetOperatorRect();
				break;
			case CLayoutCursor.TYPE_CALCULATE_BUTTON:
				rect = GetCalculateRect();
				break;
			case CLayoutCursor.TYPE_RESULT_CARD:
				rect = GetResultCardRect();
				break;
			case CLayoutCursor.TYPE_SIGNSBAR_BUTTON:
				rect = GetSignsRect(nIndex);
				break;
			case CLayoutCursor.TYPE_CANCEL_BUTTON:
				rect = GetCancelButtonRect();
				break;
			case CLayoutCursor.TYPE_NEWDEAL_BUTTON:
				rect = GetNewDealRect();
				break;
		}
		
		return rect;
	}

	/*
	 * Note: if the cursor type is temporary card, then the index of the cursor must be the index among real displayed 
	 * temporary cards, not the index in the temporary card array.
	 */
	public static Rect GetBoundary(CLayoutCursor cursor)
	{
		Rect rect = new Rect(0, 0, 0, 0);

		int nType = cursor.GetType();
		int nIndex = cursor.GetIndex();

		switch(nType)
		{
			case CLayoutCursor.TYPE_NONE:
				break;
			case CLayoutCursor.TYPE_BASIC_CARD:
				rect = GetBasicCardBoundary(nIndex);
				break;
			case CLayoutCursor.TYPE_TEMP_CARD:
				/*
				 * Note: The index is the index among displayed temporary cards.
				 * not the index in temporary card array
				 */
				rect = GetTempCardBoundary(nIndex);
				break;
			case CLayoutCursor.TYPE_OPERAND_CARD:
				if(nIndex == 0)
					rect = GetOperand1Boundary();
				else
					rect = GetOperand2Boundary();
				break;
			case CLayoutCursor.TYPE_OPERATOR_BUTTON:
				rect = GetOperatorBoundary();
				break;
			case CLayoutCursor.TYPE_CALCULATE_BUTTON:
				rect = GetCalculateBoundary();
				break;
			case CLayoutCursor.TYPE_RESULT_CARD:
				rect = GetResultCardBoundary();
				break;
			case CLayoutCursor.TYPE_SIGNSBAR_BUTTON:
				rect = GetSignsBoundary(nIndex);
				break;
			case CLayoutCursor.TYPE_CANCEL_BUTTON:
				rect = GetCancelButtonBoundary();
				break;
			case CLayoutCursor.TYPE_NEWDEAL_BUTTON:
				rect = GetNewDealBoundary();
				break;
		}
		
		return rect;
	}
	
	public static int HitBasicCard(int x, int y)
	{
		int nRet = -1;
		
		Rect rect;
		for(int i = 0; i < 4; ++i)
		{
			rect = GetBasicCardRect(i);
			if(rect.contains(x, y))
			{
				return i;
			}
		}
		
		return nRet;
	}

	public static boolean InBasicCardRow(int x, int y)
	{
		boolean bRet = false;
		
		Rect rect;
		rect = GetBasicCardRect(0);
		bRet = (0 <= x && x < m_nClinetX && rect.top <= y && y <= rect.bottom);
		
		return bRet;
	}
	
	public static boolean InTempCardRow(int x, int y)
	{
		boolean bRet = false;
		
		Rect rect;
		rect = GetTempCardRect(0);
		bRet = (0 <= x && x < m_nClinetX && rect.top <= y && y <= rect.bottom);
		
		return bRet;
	}

	public static int HitTempCard(int x, int y, int count)
	{
		int nRet = -1;
		
		Rect rect;
		for(int i = 0; i < count; ++i)
		{
			rect = GetTempCardRect(i);
			if(rect.contains(x, y))
			{
				return i;
			}
		}
		
		return nRet;
	}

	public static int HitOperand(int x, int y)
	{
		int nRet = -1;
		
		Rect rect;
		rect = GetOperand1Rect();
		if(rect.contains(x, y))
		{
			return 0;
		}
		rect = GetOperand2Rect();
		if(rect.contains(x, y))
		{
			return 1;
		}
		
		return nRet;
	}

	public static boolean HitOperator(int x, int y)
	{
		boolean bRet = false;
		
		Rect rect;
		rect = GetOperatorRect();
		if(rect.contains(x, y))
		{
			return true;
		}
		
		return bRet;
	}

	public static boolean HitAsignButton(int x, int y)
	{
		boolean bRet = false;
		
		Rect rect;
		rect = GetCalculateRect();
		if(rect.contains(x, y))
		{
			return true;
		}
		
		return bRet;
	}

	public static boolean HitCancelButton(int x, int y)
	{
		boolean bRet = false;
		
		Rect rect;
		rect = GetCancelButtonRect();
		if(rect.contains(x, y))
		{
			return true;
		}
		
		return bRet;
	}

	public static boolean HitDealButton(int x, int y)
	{
		boolean bRet = false;
		
		Rect rect;
		rect = GetNewDealRect();
		if(rect.contains(x, y))
		{
			return true;
		}
		
		return bRet;
	}

	public static int HitSignsBar(int x, int y)
	{
		int nRet = -1;
		
		Rect rect;
		for(int i = 0; i < 4; ++i)
		{
			rect = GetSignsRect(i);
			if(rect.contains(x, y))
			{
				return i;
			}
		}
		
		return nRet;
	}

	public static int GetCalculationWidth()
	{
		int nRet = m_ResultX + m_CardWidth - m_OperandX1;
		return nRet;
	}

	public static int GetCalculationHeight()
	{
		return m_CardHeight;
	}
	
	public static int GetDealSpriteHeight()
	{
		return m_DealSpriteHeight;
	}

	public static int GetDealSpriteWidth()
	{
		return m_DealSpriteWidth;
	}

	public static Rect GetDealSpriteRect()
	{
		return m_DealSpriteRect;
	}
	
	public static Rect GetScoreRect(int index)
	{
		Rect rect = new Rect(0, 0, 1, 1);
		int col = (int)(index/4);
		int row = index%4;
		
		rect.left = m_ScoreX1 - col*(m_ScoreWidth+m_ScoreMargin);
		rect.right = rect.left + m_ScoreWidth;
		rect.top = m_ScoreY1 + row *(m_ScoreHeight+m_ScoreMargin);
		rect.bottom = rect.top + m_ScoreHeight;
	
		return rect;
	}
	
	public static int WindowWidth()
	{
		return m_nClinetX;
	}

	public static int WindowHeight()
	{
		return m_nClinetY;
	}
	
	public static int WindowCenterX()
	{
		return m_nClinetX/2;
	}

	public static int WindowCenterY()
	{
		return m_nClinetY/2;
	}

	public static int GetStarWidth()
	{
		return m_nStarWidth;
	}

	public static int GetStarHeight()
	{
		return m_nStarHeight;
	}
}
