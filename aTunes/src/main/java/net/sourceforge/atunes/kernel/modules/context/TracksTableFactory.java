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

package net.sourceforge.atunes.kernel.modules.context;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sourceforge.atunes.gui.GuiUtils;
import net.sourceforge.atunes.model.ILookAndFeelManager;
import net.sourceforge.atunes.model.ITrackInfo;

public class TracksTableFactory {

	private ILookAndFeelManager lookAndFeelManager;
	
	/**
	 * @param lookAndFeelManager
	 */
	public void setLookAndFeelManager(ILookAndFeelManager lookAndFeelManager) {
		this.lookAndFeelManager = lookAndFeelManager;
	}
	
	/**
	 * Returns a new table to show tracks information from context
	 * @param listener
	 * @return
	 */
	public JTable getNewTracksTable(final ITracksTableListener listener) {
        final JTable tracksTable = lookAndFeelManager.getCurrentLookAndFeel().getTable();
        tracksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tracksTable.setDefaultRenderer(String.class, lookAndFeelManager.getCurrentLookAndFeel().getTableCellRenderer(
                GuiUtils.getComponentOrientationTableCellRendererCode(lookAndFeelManager.getCurrentLookAndFeel())));

        tracksTable.setDefaultRenderer(Integer.class, lookAndFeelManager.getCurrentLookAndFeel().getTableCellRenderer(
                GuiUtils.getComponentOrientationTableCellRendererCode(lookAndFeelManager.getCurrentLookAndFeel())));

        tracksTable.getTableHeader().setReorderingAllowed(true);
        tracksTable.getTableHeader().setResizingAllowed(false);
        tracksTable.setColumnModel(new TracksDefaultTableColumnModel());

        tracksTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedTrack = tracksTable.getSelectedRow();
                    if (selectedTrack != -1) {
                        ITrackInfo track = ((ITrackTableModel) tracksTable.getModel()).getTrack(selectedTrack);
                        listener.trackSelected(track);
                    }
                }
            }
        });

        return tracksTable;
	}
}
