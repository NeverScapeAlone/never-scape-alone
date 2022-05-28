package com.neverscapealone;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class NeverScapeAlonePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(NeverScapeAlonePlugin.class);
		RuneLite.main(args);
	}
}