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

import net.sourceforge.atunes.kernel.modules.tags.TagEditionOperations;
import net.sourceforge.atunes.model.IPlayListHandler;
import net.sourceforge.atunes.model.IProcessFactory;
import net.sourceforge.atunes.model.IRepositoryHandler;
import net.sourceforge.atunes.utils.I18nUtils;

/**
 * This action invokes process to repair album names in repository
 * 
 * @author fleax
 * 
 */
public class RepairAlbumNamesAction extends CustomAbstractAction {

    private static final long serialVersionUID = -7828819966696617838L;

    private IPlayListHandler playListHandler;
    
    private IRepositoryHandler repositoryHandler;
    
    private IProcessFactory processFactory;
    
    /**
     * @param processFactory
     */
    public void setProcessFactory(IProcessFactory processFactory) {
		this.processFactory = processFactory;
	}
    
    /**
     * @param playListHandler
     */
    public void setPlayListHandler(IPlayListHandler playListHandler) {
		this.playListHandler = playListHandler;
	}
    
    /**
     * @param repositoryHandler
     */
    public void setRepositoryHandler(IRepositoryHandler repositoryHandler) {
		this.repositoryHandler = repositoryHandler;
	}
    
    public RepairAlbumNamesAction() {
        super(I18nUtils.getString("REPAIR_ALBUM_NAMES"));
    }

    @Override
    protected void executeAction() {
        TagEditionOperations.repairAlbumNames(playListHandler, repositoryHandler, processFactory);
    }
}
