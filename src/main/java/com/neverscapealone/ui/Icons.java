package com.neverscapealone.ui;

import com.neverscapealone.NeverScapeAlonePlugin;
import javax.swing.ImageIcon;
import net.runelite.client.util.ImageUtil;

public class Icons
{
    private static final Class<?> PLUGIN_CLASS = NeverScapeAlonePlugin.class;

    public static final ImageIcon GITHUB_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/github.png"));
    public static final ImageIcon DISCORD_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/discord.png"));
    public static final ImageIcon WEB_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/web.png"));
    public static final ImageIcon PATREON_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/patreon.png"));
    public static final ImageIcon TWITTER_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/twitter.png"));
    public static final ImageIcon WARNING_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/warning.png"));
    public static final ImageIcon ERROR_ICON = new ImageIcon(ImageUtil.loadImageResource(PLUGIN_CLASS, "/error.png"));
}