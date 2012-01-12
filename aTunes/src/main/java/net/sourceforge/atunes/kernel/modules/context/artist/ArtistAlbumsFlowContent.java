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

package net.sourceforge.atunes.kernel.modules.context.artist;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import net.sourceforge.atunes.Constants;
import net.sourceforge.atunes.Context;
import net.sourceforge.atunes.gui.GuiUtils;
import net.sourceforge.atunes.gui.views.controls.ScrollableFlowPanel;
import net.sourceforge.atunes.kernel.modules.context.AbstractContextPanelContent;
import net.sourceforge.atunes.model.IAlbumInfo;
import net.sourceforge.atunes.utils.I18nUtils;

import org.jdesktop.swingx.border.DropShadowBorder;

public class ArtistAlbumsFlowContent extends AbstractContextPanelContent<ArtistInfoDataSource> {

    private ScrollableFlowPanel coversPanel;
    
    @Override
    public Component getComponent() {
        coversPanel = new ScrollableFlowPanel();
        coversPanel.setOpaque(false);
        coversPanel.setLayout(new FlowLayout());
        return coversPanel;
    }

    @Override
    public String getContentName() {
        return I18nUtils.getString("ALBUMS");
    }

    @Override
    public void updateContentFromDataSource(ArtistInfoDataSource source) {
    	List<IAlbumInfo> albums = source.getAlbumList().getAlbums();
    	for (IAlbumInfo album : albums) {
    		coversPanel.add(getLabelForAlbum(album));
    	}
    	coversPanel.revalidate();
    	coversPanel.repaint();
    	coversPanel.validate();
    }

    @Override
    public void clearContextPanelContent() {
        super.clearContextPanelContent();
        coversPanel.removeAll();
    };

    /**
     * Gets the Label for album.
     * 
     * @param album
     *            the album
     * @return the label for album
     */
    JLabel getLabelForAlbum(final IAlbumInfo album) {
        final JLabel coverLabel = new JLabel(album.getCover());
        coverLabel.setToolTipText(album.getTitle());
        if (album.getCover() == null) {
            coverLabel.setPreferredSize(new Dimension(Constants.CONTEXT_IMAGE_WIDTH, Constants.CONTEXT_IMAGE_HEIGHT));
            coverLabel.setBorder(BorderFactory.createLineBorder(GuiUtils.getBorderColor()));
        } else {
            coverLabel.setBorder(Context.getBean(DropShadowBorder.class));
        }

        coverLabel.addMouseListener(new CoverMouseAdapter(album, coverLabel, getDesktop()));

        return coverLabel;
    }

}
