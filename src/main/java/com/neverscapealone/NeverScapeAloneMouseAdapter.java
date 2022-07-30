package com.neverscapealone;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.inject.Inject;
import javax.swing.SwingUtilities;
import net.runelite.client.input.MouseAdapter;

class NeverScapeAloneMouseAdapter extends MouseAdapter
{
    @Inject
    private NeverScapeAlonePlugin plugin;

    @Override
    public MouseEvent mousePressed(MouseEvent e) {
        final Point mousePos = e.getPoint();

        if (plugin.HotKeyPressed)
        {
            if (SwingUtilities.isLeftMouseButton(e))
            {
            return e;
        }
        }
        return e;
    }
}