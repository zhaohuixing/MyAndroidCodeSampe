package com.xingzhaohui.guicomponent;

import com.xingzhaohui.component.GUILayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class NOMPopoverMenu extends RelativeLayout implements INOMMenuViewDelegate, IDualStateButtonDelegate
{
	public static final float MAX_POPOVERMENU_WIDTH = 240.0f;
	public static final int MAX_POPOVERMENU_DISPLAY_ITEMS = 5;
	
	private NOMMenuBackgroundView		m_Background;
	
	private NOMMenuCoreView				m_RootMenu;
	private NOMMenuCoreView				m_CurrentDisplayMenu;
	private DualStateButton				m_ControlButton;
	
	private int							m_MenuID;
	
	private INOMPopoverMenuDelegate		m_Delegate;
	
	//This function probably never be used
	public static float GetContainerViewMaxHeight()
	{
		return 300.0f;
	}

	public static float GetContainerViewWidth()
	{
		float screenw = (float)GUILayout.GetScreenWidth();
		float fRet = screenw*0.6f;
		if(MAX_POPOVERMENU_WIDTH < fRet)
			fRet = MAX_POPOVERMENU_WIDTH;
		
		return fRet;
	}

	public static float GetAchorSize()
	{
        return 50;
	}

	public static float GetCornerSize()
	{
	    float w = NOMPopoverMenu.GetContainerViewWidth();
	    float ret = w*0.05f;
	    return ret;
	}

	public static float GetMenuItemWidth()
	{
	    float w = NOMPopoverMenu.GetContainerViewWidth();
	    float ret = w*0.9f;
	    return ret;
	}

	public static float GetMenuItemHeight()
	{
	    return 56;
	}

	public static int GetMaxDisplayMenuItemNumber()
	{
	    return MAX_POPOVERMENU_DISPLAY_ITEMS;
	}

	public static int GetMinDisplayMenuItemNumber()
	{
	    return 1;
	}

	private void Initialize(Context context)
	{
		this.setBackgroundColor(Color.TRANSPARENT);
		m_Background = new NOMMenuBackgroundView(context);
		this.addView(m_Background);
		m_ControlButton = new DualStateButton(context);
		m_ControlButton.SetDelegate(this);
		this.addView(m_ControlButton);
		m_RootMenu = null;
		m_CurrentDisplayMenu = null;
		m_MenuID = -1;
		m_Delegate = null;
	}

	public NOMPopoverMenu(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public NOMPopoverMenu(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public NOMPopoverMenu(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public void SetScrollSigns(Bitmap upSign, Bitmap downSign)
	{
		m_Background.SetScrollSigns(upSign, downSign);
	}
	
	public void RegisterDelegate(INOMPopoverMenuDelegate delegate)
	{
		m_Delegate = delegate;
	}
	
	public void RegisterMenuID(int nID)
	{
		m_MenuID = nID;
	}
	
	public int GetMenuID()
	{
		return m_MenuID;
	}
	
	private void UpdateChildrenLayout()
	{
		int btnSize = (int)NOMPopoverMenu.GetAchorSize();
		int w = this.getWidth();
		int h = this.getHeight();
		m_Background.layout(0, 0, w, h-btnSize);
		int ml = (int)NOMPopoverMenu.GetCornerSize();
		int mt = ml;
		int mr = ml + (int)NOMPopoverMenu.GetMenuItemWidth();
		int mb = mt + (h-btnSize-ml*2);
		if(m_CurrentDisplayMenu != null)
		{	
			m_CurrentDisplayMenu.layout(ml, mt, mr, mb);
			m_CurrentDisplayMenu.UpdateLayout();
		}	
		ml = (w - btnSize)/2;
		mt = h-btnSize;
		mr = ml + btnSize;
		mb = h;
		m_ControlButton.layout(ml, mt, mr, mb);
		m_ControlButton.invalidate();
		this.bringChildToFront(m_ControlButton);
	}

	public void OpenMenu()
	{
		this.OpenMenu(m_RootMenu);
	}
	
	public void OpenMenu(NOMMenuCoreView menu)
	{
		if(m_CurrentDisplayMenu != null)
		{	
			m_CurrentDisplayMenu.setVisibility(View.GONE);
		}
		if(menu != null)
		{
			menu.setVisibility(View.VISIBLE);
			m_CurrentDisplayMenu = menu;
			this.bringChildToFront(m_CurrentDisplayMenu);
			float fMenuHeight = m_CurrentDisplayMenu.GetMenuHeight();
			int ml = (int)NOMPopoverMenu.GetCornerSize();
			int mt = ml;
			int mr = ml + (int)NOMPopoverMenu.GetMenuItemWidth();
			int mb = mt + (int)fMenuHeight;
			m_CurrentDisplayMenu.layout(ml, mt, mr, mb);

			int btnSize = (int)NOMPopoverMenu.GetAchorSize();
			int w = this.getWidth();
			int h = this.getHeight();
			ml = (w - btnSize)/2;
			mt = h-btnSize;
			mr = ml + btnSize;
			mb = h;
			m_ControlButton.layout(ml, mt, mr, mb);
			
			int left = this.getLeft();
			int right = this.getRight();
			
			float currentBottom = this.getBottom();
			float fMarge = NOMPopoverMenu.GetCornerSize();
			float fbtnSize = NOMPopoverMenu.GetAchorSize();
			float fNewHeight = fMenuHeight + 2*fMarge + fbtnSize;
			float newTop = currentBottom - fNewHeight;
			this.layout(left, (int)newTop, right, (int)currentBottom);
			
			this.requestLayout();
			this.invalidate();
		}
		if(m_CurrentDisplayMenu == null || (m_CurrentDisplayMenu != null && m_CurrentDisplayMenu.IsRootMenu() == true))
		{
			m_ControlButton.SetStateOne();
		}
		else
		{
			m_ControlButton.SetStateTwo();
		}
		boolean bScroll = false;
		if(m_CurrentDisplayMenu != null)
		{
			bScroll = m_CurrentDisplayMenu.IsScrollable(); 
		}
		m_Background.SetShownScrollSign(bScroll);
	}
	
	public void OnMenuItemClicked(int menuID)
	{
		if(m_Delegate != null)
		{
			m_Delegate.HandleMenuItemEvent(menuID);
		}
	}
	
	public void AddRootMenu(NOMMenuCoreView rootMenu)
	{
		m_RootMenu = rootMenu;
		m_CurrentDisplayMenu = rootMenu;
		this.addView(m_RootMenu);
		
	}

	public void AddMenu(NOMMenuCoreView menu)
	{
		this.addView(menu);
		menu.setVisibility(View.GONE);
	}
	
	public void SetButtonImages(Bitmap bmp1, Bitmap bmp2)
	{
		m_ControlButton.SetBitmap(bmp1, bmp2);
	}
	
	public void OnStateOneClick()
	{
		if(m_Delegate != null)
		{
			m_Delegate.CloseMenu(m_MenuID);
		}
	}
	
	public void OnStateTwoClick()
	{
		if(m_CurrentDisplayMenu != null && m_CurrentDisplayMenu.IsRootMenu() == false)
		{
			m_CurrentDisplayMenu.GotoParentMenu();
		}
	}
	
	public int GetLayoutHeight()
	{
		int nHeight = (int)NOMPopoverMenu.GetContainerViewMaxHeight();
		if(m_CurrentDisplayMenu != null)
		{
			float fMenuHeight = m_CurrentDisplayMenu.GetMenuHeight();
			float margin = NOMPopoverMenu.GetCornerSize();
			float btnSize = NOMPopoverMenu.GetAchorSize();
			nHeight = (int)(fMenuHeight + margin*2 + btnSize);
		}
		return nHeight;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.RelativeLayout#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) 
	{
		// TODO Auto-generated method stub
		//super.onLayout(changed, l, t, r, b);
		UpdateChildrenLayout();
	}

	/* (non-Javadoc)
	 * @see android.view.View#onSizeChanged(int, int, int, int)
	 */
	/*
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
	}
*/
}
