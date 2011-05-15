/*
 * aTunes 2.1.0-SNAPSHOT
 * Copyright (C) 2006-2011 Alex Aranda, Sylvain Gaudard and contributors
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

package net.sourceforge.atunes.gui.images;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import net.sourceforge.atunes.gui.lookandfeel.LookAndFeelSelector;

public class DateImageIcon {

	private static final int SIZE = 18;

	public static ImageIcon getIcon() {
		return getIcon(null);
	}
	
	public static ImageIcon getIcon(Paint color) {
		Rectangle r = new Rectangle(1, 2, 16, 14);
		Rectangle r2 = new Rectangle(2, 5, 14, 10);

		BufferedImage bi = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = bi.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(color != null ? color : LookAndFeelSelector.getInstance().getCurrentLookAndFeel().getPaintFor(null));

        g.fill(r);
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
        g.fill(r2);
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
        
        g.setFont(new Font("Dialog", Font.BOLD, 8));
        g.drawString("31", 3, 13);
        
        g.dispose();
        return new ImageIcon(bi);
	}

}
