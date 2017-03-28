package com.xingzhaohui.awsservice;

import java.util.*;

public class NOMSystemHelper 
{
	public static String GenerateGUID()
	{
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
	}
}
