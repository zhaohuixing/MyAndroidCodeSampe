/**
 * 
 */
package com.xgadget.ChuiNiuLite;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

/**
 * @author zhaohuixing
 *
 */
public class CGameAudio 
{
	public static final int GAME_SOUND_ID_PLAYERSHOOT =	0;
	public static final int GAME_SOUND_ID_TARGETHORN = 1;
	public static final int GAME_SOUND_ID_TARGETSHOOT = 2;
	public static final int GAME_SOUND_ID_TARGETKNOCKDOWN = 3;
	public static final int GAME_SOUND_ID_BLAST	= 4;
	public static final int GAME_SOUND_ID_COLLISION	= 5;
	public static final int GAME_SOUND_ID_JUMP = 6;
	public static final int GAME_SOUND_ID_CRASH	= 7;
	public static final int GAME_SOUND_ID_THUNDER	= 8;
	
	private MediaPlayer m_PlayerShootSound;
	private MediaPlayer m_TargetThornSound;
	private MediaPlayer m_TargetShootSound;
	private MediaPlayer m_TargetKnockDownSound;
	private MediaPlayer m_BlastSound;
	private MediaPlayer m_CollisionSound;
	private MediaPlayer m_JumpSound;
	private MediaPlayer m_CrashSound;
	private MediaPlayer m_BackgroundSound;
	private MediaPlayer m_BlockageSound;
	private MediaPlayer m_WinSound;
	private MediaPlayer m_LoseSound;
	private MediaPlayer m_ThunderSound;

	private MediaPlayer m_LoseSound_ZH;
	private MediaPlayer m_WinSound_Level1_ZH;
	private MediaPlayer m_WinSound_Level2_ZH;
	private MediaPlayer m_WinSound_Level3_ZH;
	private MediaPlayer m_WinSound_Level4_ZH;
	
	private boolean 	m_bPlayerShootSound;
	private boolean  	m_bTargetThornSound;
	private boolean  	m_bTargetShootSound;
	private boolean  	m_bTargetKnockDownSound;
	private boolean  	m_bBlastSound;
	private boolean  	m_bCollisionSound;
	private boolean  	m_bJumpSound;
	private boolean  	m_bCrashSound;
	private boolean  	m_bBackgroundSound;
	private boolean  	m_bBlockageSound;
	private boolean  	m_bWinSound;
	private boolean  	m_bLoseSound;
	private boolean  	m_bThunderSound;
	
	public CGameAudio(Context  context)
	{
		m_PlayerShootSound = MediaPlayer.create(context, R.raw.breath);
		m_PlayerShootSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bPlayerShootSound = false;
			}
		}
		
		);
		
		m_TargetThornSound = MediaPlayer.create(context, R.raw.cowmee);
		m_TargetThornSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bTargetThornSound = false;
			}
		}
		);
		
		
		m_TargetShootSound = MediaPlayer.create(context, R.raw.cowpupu);
		m_TargetShootSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bTargetShootSound = false;
			}
		}
		);
		
		m_TargetKnockDownSound = MediaPlayer.create(context, R.raw.cowdown);
		m_TargetKnockDownSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bTargetKnockDownSound = false;
			}
		}
		);
		
		m_BlastSound = MediaPlayer.create(context, R.raw.blast);
		m_BlastSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bBlastSound = false;
			}
		}
		);
		
		m_CollisionSound = MediaPlayer.create(context, R.raw.collision);
		m_CollisionSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bCollisionSound = false;
			}
		}
		);
		
		m_JumpSound = MediaPlayer.create(context, R.raw.jump);
		m_JumpSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bJumpSound = false;
			}
		}
		);
		
		m_CrashSound = MediaPlayer.create(context, R.raw.dead);
		m_CrashSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bCrashSound = false;
			}
		}
		);
		
		m_BackgroundSound = MediaPlayer.create(context, R.raw.bkmusic);
		m_BackgroundSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				//m_BackgroundSound.setLooping(false);
				try 
				{
					mp.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m_bBackgroundSound = false; 
				if(CConfiguration.IsSoundEnable() == true)
					PlayBackgroundSound();
			}
		}
		);
		
		m_BlockageSound = MediaPlayer.create(context, R.raw.bkmusic2);
		m_BlockageSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				//m_BlockageSound.setLooping(false);
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
				m_bBlockageSound = false;
				if(CConfiguration.IsSoundEnable() == true)
					PlayBlockageSound();
			}
		}
		);
		
		m_WinSound = MediaPlayer.create(context, R.raw.win);
		m_WinSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bWinSound = false;
			}
		}
		);
		
		m_LoseSound = MediaPlayer.create(context, R.raw.lose);
		m_LoseSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bLoseSound = false;
			}
		}
		);
		
		m_ThunderSound = MediaPlayer.create(context, R.raw.thunders);
		m_ThunderSound.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bThunderSound = false;
			}
		}
		);
		
		m_LoseSound_ZH = MediaPlayer.create(context, R.raw.lose_zh);
		m_LoseSound_ZH.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bLoseSound = false;
			}
		}
		);
		
		m_WinSound_Level1_ZH = MediaPlayer.create(context, R.raw.winlevel_zh);
		m_WinSound_Level1_ZH.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bWinSound = false;
			}
		}
		);

		m_WinSound_Level2_ZH = MediaPlayer.create(context, R.raw.winlevel2_zh);
		m_WinSound_Level2_ZH.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bWinSound = false;
			}
		}
		);

		m_WinSound_Level3_ZH = MediaPlayer.create(context, R.raw.winlevel3_zh);
		m_WinSound_Level3_ZH.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bWinSound = false;
			}
		}
		);
		
		m_WinSound_Level4_ZH = MediaPlayer.create(context, R.raw.winlevel4_zh);
		m_WinSound_Level4_ZH.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
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
				m_bWinSound = false;
			}
		}
		);
		
		m_bPlayerShootSound = false;
		m_bTargetThornSound = false;
		m_bTargetShootSound = false;
		m_bTargetKnockDownSound = false;
		m_bBlastSound = false;
		m_bCollisionSound = false;
		m_bJumpSound = false;
		m_bCrashSound = false;
		m_bBackgroundSound = false;
		m_bBlockageSound = false;
		m_bWinSound = false;
		m_bLoseSound = false;
		m_bThunderSound = false;
	}
	
	public void ReleaseSoundSource()
	{
		m_PlayerShootSound.release();
		m_TargetThornSound.release();
		m_TargetShootSound.release();
		m_TargetKnockDownSound.release();
		m_BlastSound.release();
		m_CollisionSound.release();
		m_JumpSound.release();
		m_CrashSound.release();
		m_BackgroundSound.release();
		m_BlockageSound.release();
		m_WinSound.release();
		m_LoseSound.release();
		m_ThunderSound.release();
	}
	
	public void PlayBackgroundSound()
	{
		if(CConfiguration.IsSoundEnable() == false || CGameScene.IsGameStatePlay() == false)
			return;
		
		if(m_BackgroundSound != null && m_bBackgroundSound == false)
		{	
			m_bBackgroundSound = true;
			//m_BackgroundSound.setLooping(true);
			m_BackgroundSound.start();
		}	
	}

	public void StopBackgroundSound()
	{
		if(m_BackgroundSound != null && m_bBackgroundSound == true)
		{	
			//m_BackgroundSound.setLooping(false);
			m_BackgroundSound.stop();
			m_BackgroundSound.reset();
			try 
			{
				m_BackgroundSound.prepare();
			} 
			catch (IllegalStateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m_bBackgroundSound = false;
		}	
	}
	
	public void PlayBlockageSound()
	{
		if(CConfiguration.IsSoundEnable() == false || CGameScene.IsGameStatePlay() == false)
			return;
		
		//StopBackgroundSound();
		if(m_BlockageSound != null && m_bBlockageSound == false)
		{
			//m_BlockageSound.setLooping(true);
			m_bBlockageSound = true;
			m_BlockageSound.start();
		}	
	}	

	public void SwitchToBackgroundSound()
	{
		if(m_bBlockageSound == true)
		{
			StopBlockageSound();
		}
		//PlayBackgroundSound();
	}

	public void StopBlockageSound()
	{
		if(m_BlockageSound != null && m_bBlockageSound == true)
		{
			//m_BlockageSound.setLooping(false);
			m_BlockageSound.stop();
			m_bBlockageSound = false;
			m_BlockageSound.reset();
			try 
			{
				m_BlockageSound.prepare();
			} 
			catch (IllegalStateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void PauseBackgroundSound()
	{
		if(m_BackgroundSound != null && m_bBackgroundSound == true)
		{	
			m_BackgroundSound.pause();
		}	
	}

	public void PauseBlockageSound()
	{
		if(m_BlockageSound != null && m_bBlockageSound == true)
		{
			m_BlockageSound.pause();
		}
	}
	
	public void ResumeBackgroundSound()
	{
		if(m_BackgroundSound != null && m_bBackgroundSound == true)
		{	
			m_BackgroundSound.start();
		}	
	}	

	public void ResumeBlockageSound()
	{
		if(m_BlockageSound != null && m_bBlockageSound == true)
		{
			m_BlockageSound.start();
		}
	}	
	
	public void StopAllPlayingSound()
	{
		//StopBackgroundSound();
		//StopBlockageSound();
		return;

/*		
		StopCowMeeo();
		StopCowPupu();
		StopCowKnockdown();
		StopDogBreath();
		StopBlast();
		StopCollision();
		StopJump();
		StopCrash();
		StopWin();
		StopLose();
		StopThunder();*/
	}	

	public void PauseAllPlayingSound()
	{
		//PauseBackgroundSound();
		PauseBlockageSound();
		PauseCowMeeo();
		PauseCowPupu();
		PauseCowKnockdown();
		PauseDogBreath();
		PauseBlast();
		PauseCollision();
		PauseCrash();
		PauseJump();
		PauseWin();
		PauseLose();
		PauseThunder();
	}

	public void ResumeAllPlayingSound()
	{
		//ResumeBackgroundSound();
		PlayBackgroundSound();
		ResumeBlockageSound();
		ResumeCowMeeo();
		ResumeCowPupu();
		ResumeCowKnockdown();
		ResumeDogBreath();
		ResumeBlast();
		ResumeCollision();
		ResumeJump();
		ResumeCrash();
		ResumeWin();
		ResumeLose();
		ResumeThunder();
	}	

	public void PlayCowMeeo()
	{
		if(m_TargetThornSound != null && m_bTargetThornSound == false)
		{	
			m_bTargetThornSound = true;	
			m_TargetThornSound.start();
		}	
	}	

	public void StopCowMeeo()
	{
		if(m_TargetThornSound != null && m_bTargetThornSound == true)
		{	
			m_TargetThornSound.stop();
			m_TargetThornSound.reset();
			try 
			{
				m_TargetThornSound.prepare();
			} 
			catch (IllegalStateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m_bTargetThornSound = false;	
		}	
	}

	public void PauseCowMeeo()
	{
		if(m_TargetThornSound != null && m_bTargetThornSound == true)
		{	
			m_TargetThornSound.pause();
		}	
	}

	public void ResumeCowMeeo()
	{
		if(m_TargetThornSound != null && m_bTargetThornSound == true)
		{	
			m_TargetThornSound.start();
		}	
	}	


	public void PlayCowPupu()
	{
		if(m_TargetShootSound != null && m_bTargetShootSound == false)
		{	
			m_bTargetShootSound = true;	
			m_TargetShootSound.start();
		}	
	}

	public void StopCowPupu()
	{
		if(m_TargetShootSound != null && m_bTargetShootSound == true)
		{	
			m_TargetShootSound.stop();
			m_TargetShootSound.reset();
			try 
			{
				m_TargetShootSound.prepare();
			} 
			catch (IllegalStateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m_bTargetShootSound = false;	
		}	
	}	

	public void PauseCowPupu()
	{
		if(m_TargetShootSound != null && m_bTargetShootSound == true)
		{	
			m_TargetShootSound.pause();
		}	
	}

	public void ResumeCowPupu()
	{
		if(m_TargetShootSound != null && m_bTargetShootSound == true)
		{	
			m_TargetShootSound.start();
		}	
	}	

	public void PlayCowKnockdown()
	{
		if(m_bTargetKnockDownSound == false && m_TargetKnockDownSound != null)
		{
			m_bTargetKnockDownSound = true;	
			m_TargetKnockDownSound.start();
		}	
	}

	public void StopCowKnockdown()
	{
		if(m_bTargetKnockDownSound == true && m_TargetKnockDownSound != null)
		{
			m_TargetKnockDownSound.stop();
			m_TargetKnockDownSound.reset();
			try 
			{
				m_TargetKnockDownSound.prepare();
			} 
			catch (IllegalStateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bTargetKnockDownSound = false;
				return;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bTargetKnockDownSound = false;
				return;
			}
			m_bTargetKnockDownSound = false;	
		}	
	}

	public void PauseCowKnockdown()
	{
		if(m_bTargetKnockDownSound == true && m_TargetKnockDownSound != null)
		{
			m_TargetKnockDownSound.pause();
		}	
	}

	public void ResumeCowKnockdown()
	{
		if(m_bTargetKnockDownSound == true && m_TargetKnockDownSound != null)
		{
			m_TargetKnockDownSound.start();
		}	
	}	

	public void PlayDogBreath()
	{
		if(m_PlayerShootSound != null && m_bPlayerShootSound == false)
		{
			m_bPlayerShootSound = true;
			m_PlayerShootSound.start();
		}	
	}

	public void StopDogBreath()
	{
		if(m_PlayerShootSound != null && m_bPlayerShootSound == true)
		{
			m_PlayerShootSound.stop();
			m_PlayerShootSound.reset();
			try 
			{
				m_PlayerShootSound.prepare();
			} 
			catch (IllegalStateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m_bPlayerShootSound = false;
		}	
	}	

	public void PauseDogBreath()
	{
		if(m_PlayerShootSound != null && m_bPlayerShootSound == true)
		{
			m_PlayerShootSound.pause();
		}	
	}

	public void ResumeDogBreath()
	{
		if(m_PlayerShootSound != null && m_bPlayerShootSound == true)
		{
			m_PlayerShootSound.start();
		}	
	}	

	public void PlayBlast()
	{
		if(m_BlastSound != null)
		{
			//if(m_bBlastSound == true)
			//{	
			//	StopBlast();
			//}	
			m_bBlastSound = true;
			m_BlastSound.start();
		}	
	}

	public void StopBlast()
	{
		if(m_BlastSound != null && m_bBlastSound == true)
		{
			m_BlastSound.stop();
			m_BlastSound.reset();
			try 
			{
				m_BlastSound.prepare();
			} 
			catch (IllegalStateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bBlastSound = false;
				return;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bBlastSound = false;
				return;
			}
			m_bBlastSound = false;
		}	
	}

	public void PauseBlast()
	{
		if(m_BlastSound != null && m_bBlastSound == true)
		{
			m_BlastSound.pause();
		}	
	}

	public void ResumeBlast()
	{
		if(m_BlastSound != null && m_bBlastSound == true)
		{
			m_BlastSound.start();
		}	
	}	


	public void PlayCollision()
	{
		if(m_CollisionSound != null)
		{
			//if(m_bCollisionSound == true)
			//{	
			//	StopCollision();
			//}
			
			m_bCollisionSound = true;
			m_CollisionSound.start();
		}	
	}

	public void StopCollision()
	{
		if(m_CollisionSound != null && m_bCollisionSound == true)
		{
			m_CollisionSound.stop();
			m_CollisionSound.reset();
			try 
			{
				m_CollisionSound.prepare();
			} 
			catch (IllegalStateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bCollisionSound = false;
				return;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bCollisionSound = false;
				return;
			}
			m_bCollisionSound = false;
		}	
	}

	public void PauseCollision()
	{
		if(m_CollisionSound != null && m_bCollisionSound == true)
		{
			m_CollisionSound.pause();
		}	
	}

	public void ResumeCollision()
	{
		if(m_CollisionSound != null && m_bCollisionSound == true)
		{
			m_CollisionSound.start();
		}	
	}	

	public void PlayJump()
	{
		if(m_JumpSound != null && m_bJumpSound == false)
		{	
			m_bJumpSound = true;	
			m_JumpSound.start();
		}	
	}
		
	public void StopJump()
	{
		if(m_JumpSound != null && m_bJumpSound == true)
		{
			m_JumpSound.stop();
			m_JumpSound.reset();
			try 
			{
				m_JumpSound.prepare();
			} 
			catch (IllegalStateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bJumpSound = false;	
				return;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bJumpSound = false;	
				return;
			}
			m_bJumpSound = false;	
		}	
	}

	public void PauseJump()
	{
		if(m_JumpSound != null && m_bJumpSound == true)
		{
			m_JumpSound.pause();
		}	
	}

	public void ResumeJump()
	{
		if(m_JumpSound != null && m_bJumpSound == true)
		{
			m_JumpSound.start();
		}	
	}


	public void PlayCrash()
	{
		if(m_CrashSound != null)
		{
			//if(m_bCrashSound == true)
			//	StopCrash();
			
			m_bCrashSound = true;
			m_CrashSound.start();
		}	
	}

	public void StopCrash()
	{
		if(m_CrashSound != null && m_bCrashSound == true)
		{
			m_CrashSound.stop();
			m_CrashSound.reset();
			try 
			{
				m_CrashSound.prepare();
			} 
			catch (IllegalStateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bCrashSound = false;
				return;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bCrashSound = false;
				return;
			}
			m_bCrashSound = false;
		}	
	}

	public void PauseCrash()
	{
		if(m_CrashSound != null && m_bCrashSound == true)
		{
			m_CrashSound.pause();
		}	
	}

	public void ResumeCrash()
	{
		if(m_CrashSound != null && m_bCrashSound == true)
		{
			m_CrashSound.start();
		}	
	}
	
	private void PlayWinChinese()
	{
		if(CConfiguration.isGameLevelOne() == true)
		{
			if(m_WinSound_Level1_ZH != null)
			{
				m_WinSound_Level1_ZH.start();
			}
		}
		else if(CConfiguration.isGameLevelTwo() == true)
		{
			if(m_WinSound_Level2_ZH != null)
			{
				m_WinSound_Level2_ZH.start();
			}
		}
		else if(CConfiguration.isGameLevelThree() == true)
		{
			if(m_WinSound_Level3_ZH != null)
			{
				m_WinSound_Level3_ZH.start();
			}
		}
		else if(CConfiguration.isGameLevelFour() == true)
		{
			if(m_WinSound_Level4_ZH != null)
			{
				m_WinSound_Level4_ZH.start();
			}
		}
	}

	public void PlayWin()
	{
		if(m_bWinSound == false)
		{
			m_bWinSound = true;
			if(StringFactory.IsChinese() == true)
			{
				PlayWinChinese();
			}
			else
			{
				if(m_WinSound != null)
					m_WinSound.start();
			}	
		}	
	}

	private void StopWinChinese()
	{
		if(CConfiguration.isGameLevelOne() == true)
		{
			if(m_WinSound_Level1_ZH != null)
			{
				m_WinSound_Level1_ZH.stop();
				m_WinSound_Level1_ZH.reset();
				try 
				{
					m_WinSound_Level1_ZH.prepare();
				} 
				catch (IllegalStateException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					m_bWinSound = false;
					return;
				}	 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					m_bWinSound = false;
					return;
				}
			}
		}
		else if(CConfiguration.isGameLevelTwo() == true)
		{
			if(m_WinSound_Level2_ZH != null)
			{
				m_WinSound_Level2_ZH.stop();
				m_WinSound_Level2_ZH.reset();
				try 
				{
					m_WinSound_Level2_ZH.prepare();
				} 
				catch (IllegalStateException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					m_bWinSound = false;
					return;
				}	 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					m_bWinSound = false;
					return;
				}
			}
		}
		else if(CConfiguration.isGameLevelThree() == true)
		{
			if(m_WinSound_Level3_ZH != null)
			{
				m_WinSound_Level3_ZH.stop();
				m_WinSound_Level3_ZH.reset();
				try 
				{
					m_WinSound_Level3_ZH.prepare();
				} 
				catch (IllegalStateException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					m_bWinSound = false;
					return;
				}	 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					m_bWinSound = false;
					return;
				}
			}
		}
		else if(CConfiguration.isGameLevelFour() == true)
		{
			if(m_WinSound_Level4_ZH != null)
			{
				m_WinSound_Level4_ZH.stop();
				m_WinSound_Level4_ZH.reset();
				try 
				{
					m_WinSound_Level4_ZH.prepare();
				} 
				catch (IllegalStateException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					m_bWinSound = false;
					return;
				}	 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					m_bWinSound = false;
					return;
				}
			}
		}
	}
	
	public void StopWin()
	{
		if(m_bWinSound == true)
		{
			if(StringFactory.IsChinese() == true)
			{
				StopWinChinese();
			}
			else
			{
				if(m_WinSound != null)
				{	
					m_WinSound.stop();
					m_WinSound.reset();
					try 
					{
						m_WinSound.prepare();
					} 
					catch (IllegalStateException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						m_bWinSound = false;
						return;
					}	 
					catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
						m_bWinSound = false;
						return;
					}
				}	
			}	
			m_bWinSound = false;
		}	
	}

	private void PauseWinChinese()
	{
		if(CConfiguration.isGameLevelOne() == true)
		{
			if(m_WinSound_Level1_ZH != null)
			{
				m_WinSound_Level1_ZH.pause();
			}
		}
		else if(CConfiguration.isGameLevelTwo() == true)
		{
			if(m_WinSound_Level2_ZH != null)
			{
				m_WinSound_Level2_ZH.pause();
			}
		}
		else if(CConfiguration.isGameLevelThree() == true)
		{
			if(m_WinSound_Level3_ZH != null)
			{
				m_WinSound_Level3_ZH.pause();
			}
		}
		else if(CConfiguration.isGameLevelFour() == true)
		{
			if(m_WinSound_Level4_ZH != null)
			{
				m_WinSound_Level4_ZH.pause();
			}
		}
	}
	
	public void PauseWin()
	{
		if(m_bWinSound == true)
		{
			if(StringFactory.IsChinese() == true)
			{
				PauseWinChinese();
			}
			else
			{
				if(m_WinSound != null)
					m_WinSound.pause();
			}	
		}	
	}

	public void ResumeWin()
	{
		if(m_WinSound != null && m_bWinSound == true)
		{
			if(StringFactory.IsChinese() == true)
			{
				PlayWinChinese();
			}
			else
			{
				if(m_WinSound != null)
					m_WinSound.start();
			}	
		}	
	}
	
	
	public void PlayLose()
	{
		if(m_LoseSound != null && m_bLoseSound == false)
		{
			m_bLoseSound = true;
			if(StringFactory.IsChinese() == true)
			{
				m_LoseSound_ZH.start();
			}
			else
			{	
				m_LoseSound.start();
			}	
		}	
	}

	public void StopLose()
	{
		if(m_bLoseSound == true)
		{
			if(StringFactory.IsChinese() == true)
			{
				if(m_LoseSound_ZH != null)
				{	
					m_LoseSound_ZH.stop();
					m_LoseSound_ZH.reset();
				}	
			}
			else
			{	
				if(m_LoseSound != null)
				{	
					m_LoseSound.stop();
					m_LoseSound.reset();
				}	
			}	
			try 
			{
				if(StringFactory.IsChinese() == true)
				{
					if(m_LoseSound_ZH != null)
						m_LoseSound_ZH.prepare();
				}
				else
				{	
					if(m_LoseSound != null)
						m_LoseSound.prepare();
				}				
			} 
			catch (IllegalStateException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bLoseSound = false;
				return;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bLoseSound = false;
				return;
			}
			m_bLoseSound = false;
		}	
	}

	public void PauseLose()
	{
		if(m_bLoseSound == true)
		{
			if(StringFactory.IsChinese() == true)
			{
				if(m_LoseSound_ZH != null)
				{	
					m_LoseSound_ZH.pause();
				}
			}	
			else
			{
				if(m_LoseSound != null)
				{	
					m_LoseSound.pause();
				}
			}	
		}
	}	

	public void ResumeLose()
	{
		if(m_bLoseSound == true)
		{
			if(StringFactory.IsChinese() == true)
			{
				if(m_LoseSound_ZH != null)
				{	
					m_LoseSound_ZH.start();
				}
			}	
			else
			{
				if(m_LoseSound != null)
				{	
					m_LoseSound.start();
				}
			}	
		}
	}	

	public void PlayThunder()
	{
		if(m_ThunderSound != null && m_bThunderSound == false)
		{
			m_bThunderSound = true;
			m_ThunderSound.start();
		}	
	}

	public void StopThunder()
	{
		if(m_ThunderSound != null && m_bThunderSound == true)
		{
			m_ThunderSound.stop();
			m_ThunderSound.reset();
			try 
			{
				m_ThunderSound.prepare();
			} catch (IllegalStateException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bThunderSound = false;
				return;
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				m_bThunderSound = false;
				return;
			}
			m_bThunderSound = false;
		}	
	}

	public void PauseThunder()
	{
		if(m_ThunderSound != null && m_bThunderSound == true)
		{
			m_ThunderSound.pause();
		}
	}	

	public void ResumeThunder()
	{
		if(m_ThunderSound != null && m_bThunderSound == true)
		{
			m_ThunderSound.start();
		}
	}	

}
