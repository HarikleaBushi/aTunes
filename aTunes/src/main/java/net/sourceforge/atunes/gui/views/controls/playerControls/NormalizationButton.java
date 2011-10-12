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

package net.sourceforge.atunes.gui.views.controls.playerControls;

import net.sourceforge.atunes.kernel.actions.Actions;
import net.sourceforge.atunes.kernel.actions.NormalizeModeAction;
import net.sourceforge.atunes.kernel.modules.player.PlayerEngineCapability;
import net.sourceforge.atunes.model.ILookAndFeelManager;
import net.sourceforge.atunes.model.IPlayerHandler;

/*
 * based on code from Xtreme Media Player
 */
public final class NormalizationButton extends SecondaryControl {

    private static final long serialVersionUID = 6007885049773560874L;

    /**
     * Instantiates a new Normalize button.
     * @param lookAndFeelManager
     * @param playerHandler
     */
    public NormalizationButton(ILookAndFeelManager lookAndFeelManager, IPlayerHandler playerHandler) {
        super(Actions.getAction(NormalizeModeAction.class), lookAndFeelManager);
        if (!playerHandler.supportsCapability(PlayerEngineCapability.NORMALIZATION)) {
            setVisible(false);
        }
    }

}
