package com.neverscapealone;
import javax.inject.Inject;
import net.runelite.client.util.HotkeyListener;

class NeverScapeAloneHotkeyListener extends HotkeyListener
{
    private final NeverScapeAlonePlugin plugin;
    private final NeverScapeAloneConfig config;

    @Inject
    private NeverScapeAloneHotkeyListener(NeverScapeAlonePlugin plugin, NeverScapeAloneConfig config)
    {
        super(config::hotkey);
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public void hotkeyPressed()
    {
        plugin.Ping();
        NeverScapeAlonePlugin.HotKeyPressed = true;
    }

    @Override
    public void hotkeyReleased()
    {
        NeverScapeAlonePlugin.HotKeyPressed = false;
    }
}