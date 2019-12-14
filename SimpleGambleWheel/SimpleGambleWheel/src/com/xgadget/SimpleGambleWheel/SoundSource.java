package com.xgadget.SimpleGambleWheel;

import java.io.IOException;

import com.xgadget.uimodule.*;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class SoundSource 
{
	public static MediaPlayer  	g_WheelStaticSound = null;
	public static boolean		g_bPlayingWheelStaticSound = false;	

	public static MediaPlayer  g_PointerSpinSound = null;
	public static boolean			   g_bPlayingPointerSpinSound = false;	

	public static MediaPlayer  g_GameResultSound = null;
	public static boolean			   g_bPlayingGameResultSound = false;	

	public static MediaPlayer  g_DropCoinSound = null;
	public static boolean			   g_bPlayingDropCoinSound = false;	

	public static void InitializeSoundSource()
	{
		if(g_DropCoinSound == null)
		{	
			g_DropCoinSound = MediaPlayer.create(ResourceHelper.GetResourceContext() , R.raw.sndcoin);
			g_DropCoinSound.setOnCompletionListener(new OnCompletionListener()
			{
				public void onCompletion(MediaPlayer mp)
				{
					SoundSource.g_bPlayingDropCoinSound = false;
					try 
					{
						mp.prepare();
					} catch (IllegalStateException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		
		if(g_GameResultSound == null)
		{	
			g_GameResultSound = MediaPlayer.create(ResourceHelper.GetResourceContext() , R.raw.sndresult);
			g_GameResultSound.setOnCompletionListener(new OnCompletionListener()
			{
				public void onCompletion(MediaPlayer mp)
				{
					SoundSource.g_bPlayingGameResultSound = false;
					try 
					{
						mp.prepare();
					} catch (IllegalStateException e) 
					{
					// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SoundSource.PlayGameResultSound();
				}
			});
		}	
		
		
		if(g_WheelStaticSound == null)
		{	
			g_WheelStaticSound = MediaPlayer.create(ResourceHelper.GetResourceContext() , R.raw.sndsrc1);
			g_WheelStaticSound.setOnCompletionListener(new OnCompletionListener()
			{
				public void onCompletion(MediaPlayer mp)
				{
					SoundSource.g_bPlayingWheelStaticSound = false;
					try 
					{
						mp.prepare();
					} catch (IllegalStateException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SoundSource.PlayWheelStaticSound();
				}
			});
		}	

		if(g_PointerSpinSound != null)
		{	
			g_PointerSpinSound = MediaPlayer.create(ResourceHelper.GetResourceContext() , R.raw.sndsrc4);
			g_PointerSpinSound.setOnCompletionListener(new OnCompletionListener()
			{
				public void onCompletion(MediaPlayer mp)
				{
					SoundSource.g_bPlayingPointerSpinSound = false;
					try 
					{
						mp.prepare();
					} catch (IllegalStateException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SoundSource.PlayPointerSpinSound();
				}
			});
		}	
	
	}
	
	public static void ReleaseSoundSource()
	{
		if(g_WheelStaticSound != null)
		{
			g_WheelStaticSound.release();
			g_WheelStaticSound = null;
		}
		g_bPlayingWheelStaticSound = false;	

		if(g_PointerSpinSound != null)
		{
			g_PointerSpinSound.release();
			g_PointerSpinSound = null;
		}
				
		g_bPlayingPointerSpinSound = false;	

		if(g_GameResultSound != null)
		{
			g_GameResultSound.release();
			g_GameResultSound = null;
		}
		g_bPlayingGameResultSound = false;	

		if(g_DropCoinSound != null)
		{
			g_DropCoinSound.release();
			g_DropCoinSound = null;
		}
		g_bPlayingDropCoinSound = false;	
	}
	
	public static boolean IsPlayWheelStaticSound()
	{
	    return g_bPlayingWheelStaticSound;
	}

	public static boolean IsPlayPointerSpinSound()
	{
	    return g_bPlayingPointerSpinSound;
	}

	public static boolean IsPlayGameResultSound()
	{
	    return g_bPlayingGameResultSound;
	}

	public static boolean IsPlayDropCoinSound()
	{
	    return g_bPlayingDropCoinSound;
	}

	public static void PlayWheelStaticSound()
	{
		if(g_WheelStaticSound != null)
		{
	        g_bPlayingWheelStaticSound = true;
			g_WheelStaticSound.start();
		}	
	    
	}

	public static void PlayPointerSpinSound()
	{
	    if(g_PointerSpinSound != null)
	    {
	        g_bPlayingPointerSpinSound = true;
	        g_PointerSpinSound.start();
	    }
	}

	public static void PlayGameResultSound()
	{
	    if(g_GameResultSound != null)
	    {
	        g_bPlayingGameResultSound = true;
	        g_GameResultSound.start();
	    }
	}

	public static void PlayDropCoinSound()
	{
	    if(g_DropCoinSound != null)
	    {
	        g_bPlayingDropCoinSound = true;
	        g_DropCoinSound.start();
	    }
	}

	public static void StopWheelStaticSound()
	{
		if(g_WheelStaticSound != null)
		{
	        g_bPlayingWheelStaticSound = false;
			g_WheelStaticSound.pause();
		}	
	    
	}

	public static void StopPointerSpinSound()
	{
	    if(g_PointerSpinSound != null)
	    {
	        g_bPlayingPointerSpinSound = false;
	        g_PointerSpinSound.pause();
	    }
	}
	    
	public static void StopGameResultSound()
	{
	    if(g_GameResultSound != null)
	    {
	        g_bPlayingGameResultSound = false;
	        g_GameResultSound.pause();
	    }
	}

	public static void StopDropCoinSound()
	{
	    if(g_DropCoinSound != null)
	    {
	        g_bPlayingDropCoinSound = false;
	        g_DropCoinSound.pause();
	    }
	}


	public static void StopAllPlayingSound()
	{
	    SoundSource.StopWheelStaticSound();
	    SoundSource.StopPointerSpinSound();
	    SoundSource.StopGameResultSound();
	    SoundSource.StopDropCoinSound();
	}	
	
}
