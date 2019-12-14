package com.xgadget.BubbleTile;
import java.util.ArrayList;
import java.util.Random;

public class EasyShuffleSuite {
	protected   enGridType       m_GridType;
    protected	int              m_nCount;
    //Using CUndoCommand object to record easy level step
    protected	ArrayList<CUndoCommand>     m_StepList;

    private int m_nCachedIndex;
    
    
    public EasyShuffleSuite()
    {
        // Initialization code here.
        m_GridType = enGridType.PUZZLE_GRID_TRIANDLE;
        m_nCount = 0;
        //Using CUndoCommand object to record easy level step
        m_StepList = new ArrayList<CUndoCommand>();
        m_nCachedIndex = -1;
    }


    public int GetBubbleCount()
    {
        return m_nCount;
    }

    public int GetSteps()
    {
        int nStep = m_StepList.size();
        return nStep;
    }

    public enGridType GetType()
    {
        return m_GridType;
    }

    public CUndoCommand GetStep(int nIndex)
    {
        CUndoCommand pStep = null;
        int nStep = m_StepList.size();
        if(0 <= nIndex && nIndex < nStep)
        {
            pStep = (CUndoCommand)m_StepList.get(nIndex);
        }
        return pStep;
    }

    private int CreateSteps()
    {
//        srand(time(NULL));
//        int nRand = rand();
    	Random generator = new Random();
		int nRand = generator.nextInt();    	
        if(nRand < 0)
            nRand *= -1;
        
        int nThreshold = m_nCount;//*3 < 30 ? m_nCount*3 : 30;
        int nSteps = nRand%nThreshold;
        if(nSteps == 0)
            nSteps = m_nCount/2; 
        return nSteps;
    }

    private void CreateEasyStep()
    {
//        if(m_nCachedIndex == -1)
 //           srand(time(NULL));
 //       else
  //          srand(m_nCachedIndex);
            
//        int nRand = rand();
    	Random generator = new Random();
		int nRand = generator.nextInt();     	
    	
        if(nRand < 0)
            nRand *= -1;
        
        int nIndex = nRand%m_nCount;
        if(m_nCachedIndex == nIndex)
        {
            nIndex = (nIndex+1)%m_nCount;
        }
        
        int nDirect = nRand%2;
        int nMotion = 0;
        switch (m_GridType)
        {
            case PUZZLE_GRID_TRIANDLE:
                nMotion = nRand%3;
                if(0 < nMotion)
                    nMotion += 1;
                break;
            case PUZZLE_GRID_SQUARE:
                nMotion = nRand%2;
                break;
            case PUZZLE_GRID_DIAMOND:
                nMotion = nRand%3;
                if(0 < nMotion)
                    nMotion += 1;
                break;
            case PUZZLE_GRID_HEXAGON:
                nMotion = nRand%3;
                if(0 < nMotion)
                    nMotion += 1;
                break;
        }
        CUndoCommand pStep = new CUndoCommand();//[[[CUndoCommand alloc] init] autorelease];
        pStep.m_Motion = enBubbleMotion.values()[nMotion];
        pStep.m_Direction = enMotionDirection.values()[nDirect];
        pStep.m_PositionIndex = nIndex;
        m_StepList.add(pStep);
        m_nCachedIndex = nIndex;
        return;
    }

    public void Shuffle(enGridType enType, int nCount)
    {
        m_StepList.clear();
        m_nCachedIndex = -1;
        m_GridType = enType;
        m_nCount = nCount;
        int nSteps = this.CreateSteps();
        for(int i = 0; i < nSteps; ++i)
        {
            this.CreateEasyStep();
        }
    }    
    
}
