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

package net.sourceforge.atunes.kernel.modules.player;

/**
 * if user wants to ignore play back error then play back continues, if not
 * it's stopped
 * @author alex
 *
 */
final class ApplyUserSelectionRunnable implements Runnable {
	
    private AbstractPlayerEngine engine;
    private final boolean ignorePlaybackError;

    ApplyUserSelectionRunnable(AbstractPlayerEngine engine, boolean ignorePlaybackError) {
        this.engine = engine;
        this.ignorePlaybackError = ignorePlaybackError;
    }

    @Override
    public void run() {
        if (ignorePlaybackError) {
            // Move to the next audio object
            engine.playNextAudioObject(true);
        } else {
            // Stop playback
            engine.stopCurrentAudioObject(false);
        }
    }
}