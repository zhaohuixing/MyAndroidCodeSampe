package com.xgadget.SimpleGambleWheel;

import com.xgadget.XSGService.XSGAPIReleaseConfigure;

public class GameMsgFormatter 
{
	public static void FormatTextMsg(GameMessage msg, String szText)
	{
	    if(msg == null)
	    {	
	    	XSGAPIReleaseConfigure.LogDebugInfo("GameMsgFormatter.FormatTextMsg", "msg object is not valid");
	    	return;
	    }
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_TYPE, GameMsgConstant.GAME_MSG_TYPE_TEXT);
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_TEXTMSG, szText);
	    msg.FormatMessage();
	}

	public static void FormatActionMsg(GameMessage msg, CPinActionLevel pAction)
	{
	    if(msg == null)
	    {	
	    	XSGAPIReleaseConfigure.LogDebugInfo("GameMsgFormatter.FormatActionMsg", "msg object is not valid");
	    	return;
	    }
	    if(pAction == null)
	    {	
	    	XSGAPIReleaseConfigure.LogDebugInfo("GameMsgFormatter.FormatActionMsg", "pAction object is not valid");
	    	return;
	    }

	    //NSNumber* msgType = [[[NSNumber alloc] initWithInt:GameMsgConstant.GAME_MSG_TYPE_ACTIONLEVEL] autorelease];
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_TYPE, GameMsgConstant.GAME_MSG_TYPE_ACTIONLEVEL);
	    
	    //NSNumber* msgFast = [[[NSNumber alloc] initWithInt:pAction.m_nFastCycle] autorelease]; 
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_ACTION_FASTCYCLE, pAction.m_nFastCycle);

	    //NSNumber* msgMedium = [[[NSNumber alloc] initWithInt:pAction.m_nMediumCycle] autorelease]; 
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_ACTION_MEDIUMCYCLE, pAction.m_nMediumCycle);

	    //NSNumber* msgSlow = [[[NSNumber alloc] initWithInt:pAction.m_nSlowCycle] autorelease]; 
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_ACTION_SLOWCYCLE, pAction.m_nSlowCycle);

	    //NSNumber* msgAngle = [[[NSNumber alloc] initWithInt:pAction.m_nSlowAngle] autorelease]; 
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_ACTION_SLOWANGLE, pAction.m_nSlowAngle);

	    //NSNumber* msgVib = [[[NSNumber alloc] initWithInt:pAction.m_nVibCycle] autorelease]; 
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_ACTION_VIBCYCLE, pAction.m_nVibCycle);

	    //NSNumber* msgClockwise = [[[NSNumber alloc] initWithInt:pAction.m_bClockwise] autorelease]; 
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_ACTION_CLOCKWISE, pAction.m_bClockwise);
	    
	    msg.FormatMessage();
	}

	public static void FormatStartWriteMsg(GameMessage msg)
	{
	    //NSNumber* msgType = [[[NSNumber alloc] initWithInt:GameMsgConstant.GAME_MSG_TYPE_STARTWRITTING] autorelease];
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_TYPE, GameMsgConstant.GAME_MSG_TYPE_STARTWRITTING);
	    msg.FormatMessage();
	}

	public static void FormatStartChatMsg(GameMessage msg)
	{
	    //NSNumber* msgType = [[[NSNumber alloc] initWithInt:GameMsgConstant.GAME_MSG_TYPE_STARTCHATTING] autorelease];
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_TYPE, GameMsgConstant.GAME_MSG_TYPE_STARTCHATTING);
	    msg.FormatMessage();
	}

	public static void FormatStopChatMsg(GameMessage msg)
	{
	    //NSNumber* msgType = [[[NSNumber alloc] initWithInt:GameMsgConstant.GAME_MSG_TYPE_STOPCHATTING] autorelease];
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_TYPE, GameMsgConstant.GAME_MSG_TYPE_STOPCHATTING);
	    msg.FormatMessage();
	}

	public static void FormatNextTurnMsg(GameMessage msg, String szPlayerID)
	{
	    //NSNumber* msgType = [[[NSNumber alloc] initWithInt:GameMsgConstant.GAME_MSG_TYPE_GAMEPLAYNEXTTURN] autorelease];
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_TYPE, GameMsgConstant.GAME_MSG_TYPE_GAMEPLAYNEXTTURN);
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_GAMENEXTTURN_ID, szPlayerID);
	    msg.FormatMessage();
	}


	public static void BeginFormatMsg(GameMessage msg, int nTypeID)
	{
	    //NSNumber* msgType = [[[NSNumber alloc] initWithInt:nTypeID] autorelease];
	    msg.AddMessage(GameMsgConstant.GAME_MSG_KEY_TYPE, nTypeID);
	}

	public static void AddMsgText(GameMessage msg, String sKey, String sText)
	{
	    msg.AddMessage(sKey, sText);
	}

	public static void AddMsgInt(GameMessage msg, String sKey, int nNumber)
	{
	    //NSNumber* msgInt = [[[NSNumber alloc] initWithInt:nNumber] autorelease]; 
	    msg.AddMessage(sKey, nNumber);
	}

	public static void AddMsgFloat(GameMessage msg, String sKey, float fNumber)
	{
	    //NSNumber* msgFloat = [[[NSNumber alloc] initWithFloat:fNumber] autorelease]; 
	    msg.AddMessage(sKey, fNumber);
	}

	public static void EndFormatMsg(GameMessage msg)
	{
	    msg.FormatMessage();
	}

	public static void FormatPlayerBetMsg(GameMessage msg, String szPlayerID, int nLuckNumber, int nBet, int chipBalace)
	{
	    GameMsgFormatter.BeginFormatMsg(msg, GameMsgConstant.GAME_MSG_TYPE_PLAYERBET);
	    GameMsgFormatter.AddMsgText(msg, GameMsgConstant.GAME_MSG_KEY_PLAIN_PLAYER_ID, szPlayerID);
	    GameMsgFormatter.AddMsgInt(msg, GameMsgConstant.GAME_MSG_KEY_PLEDGET_LUCKYNUMBER, nLuckNumber);
	    GameMsgFormatter.AddMsgInt(msg, GameMsgConstant.GAME_MSG_KEY_PLEDGET_BET, nBet);
	    GameMsgFormatter.AddMsgInt(msg, GameMsgConstant.GAME_MSG_KEY_PLAYER_CHIPS_BALANCE, chipBalace);
	    GameMsgFormatter.EndFormatMsg(msg);
	}

	public static void FormatPlayerBalanceMsg(GameMessage msg, String szPlayerID, int chipBalace)
	{
	    GameMsgFormatter.BeginFormatMsg(msg, GameMsgConstant.GAME_MSG_TYPE_PLAYERBALANCE);
	    GameMsgFormatter.AddMsgText(msg, GameMsgConstant.GAME_MSG_KEY_PLAIN_PLAYER_ID ,szPlayerID);
	    GameMsgFormatter.AddMsgInt(msg, GameMsgConstant.GAME_MSG_KEY_PLAYER_CHIPS_BALANCE ,chipBalace);
	    GameMsgFormatter.EndFormatMsg(msg);
	}

	public static void FormatGameSettingMsg(GameMessage msg, int nGameType, int nPlayTurnType)
	{
	    GameMsgFormatter.BeginFormatMsg(msg, GameMsgConstant.GAME_MSG_TYPE_GAMESETTINGSYNC);
	    GameMsgFormatter.AddMsgInt(msg, GameMsgConstant.GAME_MSG_KEY_GAMETYPEMSG, nGameType);
	    GameMsgFormatter.AddMsgInt(msg, GameMsgConstant.GAME_MSG_KEY_GAMETHEMEMSG, Configuration.getCurrentThemeType());
	    GameMsgFormatter.AddMsgInt(msg, GameMsgConstant.GAME_MSG_KEY_ONLINEPLAYSEQUENCE, nPlayTurnType);
	    GameMsgFormatter.EndFormatMsg(msg);
	}

	public static void FormatPlayerStateMsg(GameMessage msg, String szPlayerID, int nState)
	{
	    GameMsgFormatter.BeginFormatMsg(msg, GameMsgConstant.GAME_MSG_TYPE_PLAYERSTATE);
	    GameMsgFormatter.AddMsgText(msg, GameMsgConstant.GAME_MSG_KEY_PLAIN_PLAYER_ID, szPlayerID);
	    GameMsgFormatter.AddMsgInt(msg, GameMsgConstant.GAME_MSG_KEY_PLAYERSTATE, nState);
	    GameMsgFormatter.EndFormatMsg(msg);
	}

	public static void FormatChipTransferMsg(GameMessage msg, String szRecieverID, int nChips)
	{
	    GameMsgFormatter.BeginFormatMsg(msg, GameMsgConstant.GAME_MSG_TYPE_MONEYTRANSFER);
	    GameMsgFormatter.AddMsgText(msg, GameMsgConstant.GAME_MSG_KEY_MONEYRECIEVER, szRecieverID);
	    GameMsgFormatter.AddMsgInt(msg, GameMsgConstant.GAME_MSG_KEY_TRANSMONEYMOUNT, nChips);
	    GameMsgFormatter.EndFormatMsg(msg);
	}

	public static void FormatChipTransferReceiptMsg(GameMessage msg, String szSenderID)
	{
	    GameMsgFormatter.BeginFormatMsg(msg, GameMsgConstant.GAME_MSG_TYPE_MONEYTRANSFERRECEIPT);
	    GameMsgFormatter.AddMsgText(msg, GameMsgConstant.GAME_MSG_KEY_MONEYSENDER, szSenderID);
	    GameMsgFormatter.EndFormatMsg(msg);
	}

	//May not be used
	public static void FormatPlayerPlayabilityMsg(GameMessage msg,  String szPlayerID, boolean bEnable)
	{
	    GameMsgFormatter.BeginFormatMsg(msg, GameMsgConstant.GAME_MSG_TYPE_PLAYERPLAYABLITY);
	    GameMsgFormatter.AddMsgText(msg, GameMsgConstant.GAME_MSG_KEY_PLAIN_PLAYER_ID, szPlayerID);
	    int nEable = 0;
	    if(bEnable)
	        nEable = 1;
	    GameMsgFormatter.AddMsgInt(msg, GameMsgConstant.GAME_MSG_KEY_PLAYERPLAYABLITY, nEable);
	    GameMsgFormatter.EndFormatMsg(msg);
	}

	public static void FormatCancelPendingBetMsg(GameMessage msg)
	{
		if(msg != null)
		{	
			GameMsgFormatter.BeginFormatMsg(msg, GameMsgConstant.GAME_MSG_TYPE_CANCELPENDINGBET);
			GameMsgFormatter.EndFormatMsg(msg);
		}	
	}

}
