package com.xingzhaohui.guicomponent;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class NOMMenuCoreView extends ListView 
{
    private Context 			m_Context;
	
    private NOMMenuCoreView					m_Parent;
	private INOMMenuViewDelegate			m_RootContainer;
    private int                             m_nMenuID;

    private NOMMenuAdapter					m_Adapter;
    
    public boolean IsScrollable()
    {
    	boolean bRet = false;
    	
    	if(m_Adapter != null)
    	{
    		int nTotalCount = m_Adapter.getCount();
    		int nThresold = NOMPopoverMenu.GetMaxDisplayMenuItemNumber();
    		if(nThresold < nTotalCount)
    			bRet = true;
    	}
    	
    	return bRet;
    }
    
    private void Initialize(Context context)
    {
    	this.setBackgroundColor(Color.TRANSPARENT);
    	m_Parent = null;
    	m_RootContainer = null;
        m_nMenuID = -1;
        m_Adapter = null;
        m_Context = context;
    }
    
	public NOMMenuCoreView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public NOMMenuCoreView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public NOMMenuCoreView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public void RegisterParent(NOMMenuCoreView pParent, INOMMenuViewDelegate controller)
	{
    	m_Parent = pParent;
    	m_RootContainer = controller;
	}
	
	public void RegisterMenuID(int nID)
	{
		m_nMenuID = nID;
	}
	
	public int GetMenuID()
	{
		return m_nMenuID;
	}

/*	
	public int GetMenuItemCount()
	{
		int nRet = 1;
		
		nRet = getChildCount();
		
		return nRet;
	}
*/	

	public float GetMenuHeight()
	{
		float height = NOMPopoverMenu.GetMenuItemHeight();
		int count = GetMenuItemCount();
		
		if(NOMPopoverMenu.GetMaxDisplayMenuItemNumber() < count)
			count = NOMPopoverMenu.GetMaxDisplayMenuItemNumber();
		
		float fRet = ((float)count)*height;
		return fRet;
	}
	
	public float GetMenuWidth()
	{
		return NOMPopoverMenu.GetMenuItemWidth();
	}
	
	public float GetAllItemHeight()
	{
		float height = NOMPopoverMenu.GetMenuItemHeight();
		int count = GetMenuItemCount();
		float fRet = ((float)count)*height;
		return fRet;
	}

	public void GotoParentMenu()
	{
		if(m_Parent != null && m_RootContainer != null)
		{
			m_RootContainer.OpenMenu(m_Parent);
		}
	}
	
	public void GotoChildMenu(NOMMenuCoreView menu)
	{
		if(m_RootContainer != null && menu != null)
		{
			m_RootContainer.OpenMenu(menu);
		}
	}
	
	public void HandleMenuEvent(int nMenuID)
	{
		if(m_RootContainer != null)
		{
			m_RootContainer.OnMenuItemClicked(nMenuID);
		}
	}
	
	public boolean IsRootMenu()
	{
		boolean bRet = true;
		if(m_Parent != null)
			bRet = false;
		
		return bRet;
	}

	public void AddMenuList(List<NOMMenuItem> itemList)
	{
        m_Adapter = new NOMMenuAdapter(m_Context, itemList);
        this.setAdapter(m_Adapter);
        this.setOnItemClickListener(new OnItemClickListener() 
		{
        	@Override
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) 
        	{ 
        		//if(0 <= position && 0 < m_CurrentPlayerList.size() && position < m_CurrentPlayerList.size())
        		//	m_pSelectedPeer = m_CurrentPlayerList.get(position);
        		if(m_Adapter != null && 0 <= position && position < m_Adapter.getCount())
        		{
        			NOMMenuItem item = (NOMMenuItem)m_Adapter.getItem(position);
        			if(item != null)
        			{
        				if(item.GetChildMenu() != null)
        				{
        					GotoChildMenu(item.GetChildMenu());
        				}
        				else 
        				{
        					int nMenuID = item.GetMenuID();
        					HandleMenuEvent(nMenuID);
        				}
        			}
        		}
        	}  
        });
	}

	public void UpdateLayout()
	{
		for(int i = 0; i < this.getChildCount(); ++i)
		{
			NOMMenuItemContainerView view = (NOMMenuItemContainerView)this.getChildAt(i);
			if(view != null)
			{
				int t = i*((int)NOMPopoverMenu.GetMenuItemHeight());
				int b = (i+1)*((int)NOMPopoverMenu.GetMenuItemHeight());
				int r = (int)NOMPopoverMenu.GetMenuItemWidth();
				view.layout(0, t, r, b);
			}
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.ListView#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		UpdateLayout();
	}

	/* (non-Javadoc)
	 * @see android.widget.ListView#onSizeChanged(int, int, int, int)
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		UpdateLayout();
	}

	/* (non-Javadoc)
	 * @see android.view.View#onScrollChanged(int, int, int, int)
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) 
	{
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);

		int top = 0;
		int bottom = 0;
		int r = (int)NOMPopoverMenu.GetMenuItemWidth();
		NOMMenuItemContainerView view = (NOMMenuItemContainerView)this.getChildAt(0);
		if(view != null)
		{
			top = view.getTop();
			bottom = top + ((int)NOMPopoverMenu.GetMenuItemHeight());
			r = (int)NOMPopoverMenu.GetMenuItemWidth();
			view.UpdateChildLayout();
			view.layout(0, top, r, bottom);
		}
		
		for(int i = 1; i < this.getChildCount(); ++i)
		{
			view = (NOMMenuItemContainerView)this.getChildAt(i);
			if(view != null)
			{
				top = bottom;
				bottom = top + ((int)NOMPopoverMenu.GetMenuItemHeight());
				view.UpdateChildLayout();
				view.layout(0, top, r, bottom);
			}
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.ListView#layoutChildren()
	 */
	@Override
	protected void layoutChildren() 
	{
		// TODO Auto-generated method stub
		//super.layoutChildren() must be called when menu is visible, otherwise list item can not be added into list view as child
		//if called when invisible, cound crash.
		if(this.getVisibility() == View.VISIBLE) 
			super.layoutChildren();
		UpdateLayout();
	}

	public int GetMenuItemCount() 
	{
		// TODO Auto-generated method stub
		int nRet = 1;
		
		if(m_Adapter != null)
		{
			nRet = m_Adapter.getCount();
		}
		
		return nRet;
	}

	/* (non-Javadoc)
	 * @see android.view.ViewGroup#addView(android.view.View, int, int)
	 */
/*	@Override
	public void addView(View child, int width, int height) 
	{
		// TODO Auto-generated method stub
		super.addView(child, width, height);
		int n = this.getChildCount();
	} */

	/* (non-Javadoc)
	 * @see android.view.ViewGroup#addViewInLayout(android.view.View, int, android.view.ViewGroup.LayoutParams, boolean)
	 */
/*	@Override
	protected boolean addViewInLayout(View child, int index,
			android.view.ViewGroup.LayoutParams params,
			boolean preventRequestLayout) 
	{
		// TODO Auto-generated method stub
		boolean bRet = super.addViewInLayout(child, index, params, preventRequestLayout);
		return bRet;
	}*/

	/* (non-Javadoc)
	 * @see android.view.ViewGroup#addViewInLayout(android.view.View, int, android.view.ViewGroup.LayoutParams)
	 */
/*	@Override
	protected boolean addViewInLayout(View child, int index,
			android.view.ViewGroup.LayoutParams params) 
	{
		// TODO Auto-generated method stub
		boolean bRet = super.addViewInLayout(child, index, params);
		return bRet;
	} */
}
