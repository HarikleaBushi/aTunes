/*
 * aTunes 2.2.0-SNAPSHOT
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

package net.sourceforge.atunes.kernel.actions;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import net.sourceforge.atunes.gui.GuiUtils;
import net.sourceforge.atunes.model.IPlayerHandler;
import net.sourceforge.atunes.utils.I18nUtils;

/**
 * @author alex
 *
 */
public class PlayPreviousAudioObjectAction extends CustomAbstractAction {

    private static final long serialVersionUID = -1177020643937370678L;

    private IPlayerHandler playerHandler;
    
    /**
     * @param playerHandler
     */
    public void setPlayerHandler(IPlayerHandler playerHandler) {
		this.playerHandler = playerHandler;
	}
    
    /**
     * Default constructor
     */
    public PlayPreviousAudioObjectAction() {
    	super(I18nUtils.getString("PREVIOUS"));
    }
    
    @Override
    public void initialize() {
    	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, GuiUtils.getCtrlOrMetaActionEventMask()));
    }

    @Override
    protected void executeAction() {
    	playerHandler.playPreviousAudioObject();
    }
}
