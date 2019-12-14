package com.xgadget.ChuiNiuLite;

import com.xgadget.uimodule.ResourceHelper;

public class StringFactory 
{
/*	public static String GetString_Automatically()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Automatically);
		return str;
	}

	public static String GetString_Manually()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Manually);
		return str;
	}
	
	public static String GetString_RoPaPledgeMethodLabel()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.RoPaBetMethod);
		return str;
	}
	
	public static String GetString_OnlineGamePlayLabel()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.GamePlay);
		return str;
	}
	
	public static String GetString_OnlineGamePlayBySequence()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.BySequence);
		return str;
	}
	
	public static String GetString_OnlineGamePlayByMaxBet()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.ByBiggestPledge);
		return str;
	}

	public static String GetString_Online()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Online);
		return str;
	}

	public static String GetString_Offline()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Offline);
		return str;
	}
	
	public static String GetString_OfflineMySelfID()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Me);
		return str;
	}

	public static String GetString_OfflinePlayer1ID()
	{
		String str = "RoPa1";
		return str;
	}

	public static String GetString_OfflinePlayer2ID()
	{
		String str = "RoPa2";
		
		return str;
	}

	public static String GetString_OfflinePlayer3ID()
	{
		String str = "RoPa3";
		
		return str;
	}

	public static String GetString_Chips()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Chips);
		return str;
	}
	
	public static String GetString_Earn()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Earn);
		return str;
	}
	
	public static String GetString_SendMoney()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Transfer);
		return str;
	}
	
	public static String GetString_BiggestWin()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.BiggestWin);
		return str;
	}
	
	public static String GetString_LatestPlay()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.LatestPlay);
		return str;
	}
	
	public static String GetString_OK()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.OK);
		return str;
	}
	
	public static String GetString_Cancel()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Cancel);
		return str;
	}
	
	public static String GetString_Yes()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Yes);
		return str;
	}
	
	public static String GetString_No()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.No);
		return str;
	}
	
	public static String GetString_Close()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Close);
		return str;
	}
	
	public static String GetString_TellFriends()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.TellFriend);
		return str;
	}
	
	public static String GetString_PostScore()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.PostScore);
		return str;
	}
	
	public static String GetString_Message()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Message);
		return str;
	}
	
	public static String GetString_Send()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Send);
		return str;
	}
	
	public static String GetString_Me()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.Me);
		return str;
	}

	public static String GetString_AskOnlineNickName()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.AskOnlineNickName);
		return str;
	}

	public static String GetString_AskOnlineConnection()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.AskOnlineConnection);
		return str;
	}
	
	public static String GetString_InvitationSentTo()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.InvitationSentTo);
		return str;
	}

	public static String GetString_WaitingForResponse()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.WaitingforResponse);
		return str;
	}
	
	public static String GetString_SearchingOnlinePlayers()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.SearchingOnlinePlayer);
		return str;
	}
	
	public static String GetString_SentInvitationRejected()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.SentInvitationRejected);
		return str;
	}
	
	public static String GetString_SentInvitationAccepted()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.SentInvitationAccepted);
		return str;
	}
	
	public static String GetString_InvitationCancelledStringFMT()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.InvitationCancelledStringFMT);
		return str;
	}

	public static String GetString_Spinning()
	{
		String str = "Spinning...";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.Spinning);
		
		return str;
	}

	public static String GetString_PlayTurnIsSequence()
	{
		String str = "Game turn is made by sequence.";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.PlayTurnIsSequence);
		
		return str;
	}

	public static String GetString_PlayTurnIsMaxBet()
	{
		String str = "Game turn is made by maxmum bet.";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.PlayTurnIsMaxBet);
		
		return str;
	}

	public static String GetString_ItIsPlayTurnFmt()
	{
		String str = "It is %@'s turn.";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.ItIsPlayTurnFmt);
		
		return str;
	}

	public static String GetString_YouWin()
	{
		String str = "I win!";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.YouWin);
		
		return str;
	}

	public static String GetString_YouLose()
	{
		String str = "Too bad!";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.YouLose);
		
		return str;
	}

	public static String GetString_OtherStillPlaying()
	{
		String str = "Others are still playing.";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.OtherStillPlaying);
		
		return str;
	}

	public static String GetString_PlayingDone()
	{
		String str = "Done.";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.Done);
		
		return str;
	}

	public static String GetString_SortPlayers()
	{
		String str = "Sorting players...";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.SortPlayers);
		
		return str;
	}

	public static String GetString_Pledge()
	{
		String str = "Pledging...";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.Pledge);
		
		return str;
	}

	public static String GetString_OtherPlayes()
	{
		String str = "other players";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.OtherPlayes);
		
		return str;
	}

	public static String GetString_PlayerSendMoneyToOtherFmt()
	{
		String str = "send chips to";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.PlayerSendMoneyToOtherFmt);
		
		return str;
	}
	
	public static String GetString_OnlineStarting()
	{
		String str = "Online game is starting...";
		
		//Localization string query here
		str = ResourceHelper.GetResourceContext().getString(R.string.OnlineStarting);
		
		return str;
	}
	
	public static String GetString_AskEnableFreeOnlineGameOption()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.AskEnableFreeOnlineGameOption);
		return str;
	}

	public static String GetString_OnlinePurchaseReminder()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.OnlinePurchasewarni);
		return str;
	}

	public static String GetString_AcknowledgePruchase()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.AcknowledgePruchase);
		return str;
	}

	public static String GetString_ScoreBoardPostWarning()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.ScoreBoardPostWarning);
		return str;
	}
	
	public static String GetString_HighScoreSubmitted()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.HighScoreSubmitted);
		return str;
	}
	
	public static boolean IsChineseLanguageSetting()
	{
		boolean bRet = false;
		String str = ResourceHelper.GetResourceContext().getString(R.string.app_lang);
		bRet = str.equals("1");
		return bRet;
	}*/
	public static String GetString_LevelLabel(int nLevel)
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.gamelevelone);
		if(nLevel == CConfiguration.GAME_PLAY_LEVEL_TWO)
			str = ResourceHelper.GetResourceContext().getString(R.string.gameleveltwo);
		else if(nLevel == CConfiguration.GAME_PLAY_LEVEL_THREE)
			str = ResourceHelper.GetResourceContext().getString(R.string.gamelevelthree);
		if(nLevel == CConfiguration.GAME_PLAY_LEVEL_FOUR)
			str = ResourceHelper.GetResourceContext().getString(R.string.gamelevelfour);
		
		return str;
	}

	public static String GetString_SkillLabel(int nSkill)
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.gameskillone);
		if(nSkill == CConfiguration.GAME_SKILL_LEVEL_TWO)
			str = ResourceHelper.GetResourceContext().getString(R.string.gameskilltwo);
		else if(nSkill == CConfiguration.GAME_SKILL_LEVEL_THREE)
			str = ResourceHelper.GetResourceContext().getString(R.string.gameskillthree);
		
		return str;
	}

	public static String GetString_LevelSkillLabel(int nLevel, int nSkill)
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.level1skill1);
		if(nLevel == CConfiguration.GAME_PLAY_LEVEL_ONE && nSkill == CConfiguration.GAME_SKILL_LEVEL_ONE)
			str = ResourceHelper.GetResourceContext().getString(R.string.level1skill1);
		else if(nLevel == CConfiguration.GAME_PLAY_LEVEL_ONE && nSkill == CConfiguration.GAME_SKILL_LEVEL_TWO)
			str = ResourceHelper.GetResourceContext().getString(R.string.level1skill2);
		else if(nLevel == CConfiguration.GAME_PLAY_LEVEL_ONE && nSkill == CConfiguration.GAME_SKILL_LEVEL_THREE)
			str = ResourceHelper.GetResourceContext().getString(R.string.level1skill3);
		else if(nLevel == CConfiguration.GAME_PLAY_LEVEL_TWO && nSkill == CConfiguration.GAME_SKILL_LEVEL_ONE)
			str = ResourceHelper.GetResourceContext().getString(R.string.level2skill1);
		else if(nLevel == CConfiguration.GAME_PLAY_LEVEL_TWO && nSkill == CConfiguration.GAME_SKILL_LEVEL_TWO)
			str = ResourceHelper.GetResourceContext().getString(R.string.level2skill2);
		else if(nLevel == CConfiguration.GAME_PLAY_LEVEL_TWO && nSkill == CConfiguration.GAME_SKILL_LEVEL_THREE)
			str = ResourceHelper.GetResourceContext().getString(R.string.level2skill3);
		else if(nLevel == CConfiguration.GAME_PLAY_LEVEL_THREE && nSkill == CConfiguration.GAME_SKILL_LEVEL_ONE)
			str = ResourceHelper.GetResourceContext().getString(R.string.level3skill1);
		else if(nLevel == CConfiguration.GAME_PLAY_LEVEL_THREE && nSkill == CConfiguration.GAME_SKILL_LEVEL_TWO)
			str = ResourceHelper.GetResourceContext().getString(R.string.level3skill2);
		else if(nLevel == CConfiguration.GAME_PLAY_LEVEL_THREE && nSkill == CConfiguration.GAME_SKILL_LEVEL_THREE)
			str = ResourceHelper.GetResourceContext().getString(R.string.level3skill3);
		else if(nLevel == CConfiguration.GAME_PLAY_LEVEL_FOUR && nSkill == CConfiguration.GAME_SKILL_LEVEL_ONE)
			str = ResourceHelper.GetResourceContext().getString(R.string.level4skill1);
		else if(nLevel == CConfiguration.GAME_PLAY_LEVEL_FOUR && nSkill == CConfiguration.GAME_SKILL_LEVEL_TWO)
			str = ResourceHelper.GetResourceContext().getString(R.string.level4skill2);
		else if(nLevel == CConfiguration.GAME_PLAY_LEVEL_FOUR && nSkill == CConfiguration.GAME_SKILL_LEVEL_THREE)
			str = ResourceHelper.GetResourceContext().getString(R.string.level4skill3);
			
		
		return str;
	}
	
	public static String GetString_CannotPlayerSelectedGame()
	{
		String str = ResourceHelper.GetResourceContext().getString(R.string.cannotplayselectedgame);
		
		return str;
	}
	
	public static boolean IsChinese()
	{
		boolean bRet = false;
		String str = ResourceHelper.GetResourceContext().getString(R.string.langid);
		bRet = str.equals("1");
		return bRet;
	}
	
	public static String GetString_PreferenceKeyPrefix(int nIndex)
	{
		Integer n = nIndex;
		String str = "CHUINIU_KEY_INDEX_" + n.toString()  + "_"; 
		return str;
	}

	public static String GetString_PreferenceScoreKey(int nIndex)
	{
		String str = StringFactory.GetString_PreferenceKeyPrefix(nIndex)+ "SCORE"; 
		
		return str;
	}

	public static String GetString_PreferencePointKey(int nIndex)
	{
		String str = StringFactory.GetString_PreferenceKeyPrefix(nIndex) + "POINT"; 
		
		return str;
	}	

	public static String GetString_PreferenceLastWinSkillKey()
	{
		String str = "CHUINIU_KEY_LASTWIN_SKILL"; 
		
		return str;
	}

	public static String GetString_PreferenceLastWinLevelKey()
	{
		String str = "CHUINIU_KEY_LASTWIN_LEVEL"; 
		
		return str;
	}

	public static String GetString_PreferenceSkillKey()
	{
		String str = "CHUINIU_KEY_SKILL"; 
		
		return str;
	}

	public static String GetString_PreferenceLevelKey()
	{
		String str = "CHUINIU_KEY_LEVEL"; 
		
		return str;
	}

	public static String GetString_PreferenceSoundKey()
	{
		String str = "CHUINIU_KEY_SOUND"; 
		
		return str;
	}	

	public static String GetString_PreferenceBackgroundKey()
	{
		String str = "CHUINIU_KEY_BACKGROUND_SETTING"; 
		
		return str;
	}	

	public static String GetString_PreferenceThunderThemeKey()
	{
		String str = "CHUINIU_KEY_THUNDERTHEME_SETTING"; 
		
		return str;
	}

	public static String GetString_PreferenceClockKey()
	{
		String str = "CHUINIU_KEY_CLOCK_SETTING"; 
		
		return str;
	}

	public static String GetString_PreferenceTotalScoreKey()
	{
		String str = "CHUINIULITE_KEY_TOTAL_SCORE"; 
		
		return str;
	}

	public static String GetString_PreferenceTotalPointKey()
	{
		String str = "CHUINIULITE_KEY_TOTAL_POINT"; 
		
		return str;
	}
	
	public static String GetString_PurchaseStateKey()
	{
		String str = "CHUINIULITE_KEY_PAYMENT_STATE"; 
		
		return str;
	}
	
	public static String GetString_AskAWSServiceEnableKey()
	{
		String str = "CHUINIUZH_KEY_AWSSERVICEDISABLE"; 
		
		return str;
	}
	
	
}
