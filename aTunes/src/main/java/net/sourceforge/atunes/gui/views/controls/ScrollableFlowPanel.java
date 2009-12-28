/*
 * aTunes 2.0.0-SNAPSHOT
 * Copyright (C) 2006-2009 Alex Aranda, Sylvain Gaudard, Thomas Beckers and contributors
 *
 * See http://www.atunes.org/wiki/index.php?title=Contributing for information about contributors
 *
 * http://www.atunes.org
 * http://sourceforge.net/projects/atunes
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package net.sourceforge.atunes.gui.views.controls;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

public class ScrollableFlowPanel extends JPanel implements Scrollable {

    private static final long serialVersionUID = -6868667863386961211L;

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, getParent().getWidth(), height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getPreferredHeight());
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return super.getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        int hundredth = (orientation == SwingConstants.VERTICAL ? getParent().getHeight() : getParent().getWidth()) / 100;
        return (hundredth == 0 ? 1 : hundredth);
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return orientation == SwingConstants.VERTICAL ? getParent().getHeight() : getParent().getWidth();
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    private int getPreferredHeight() {
        int rv = 0;
        for (int k = 0, count = getComponentCount(); k < count; k++) {
            Component comp = getComponent(k);
            Rectangle r = comp.getBounds();
            int height = r.y + r.height;
            if (height > rv)
                rv = height;
        }
        rv += ((FlowLayout) getLayout()).getVgap();
        return rv;
    }
}