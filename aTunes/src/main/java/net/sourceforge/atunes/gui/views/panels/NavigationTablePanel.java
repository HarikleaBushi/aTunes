/*
 * aTunes
 * Copyright (C) Alex Aranda, Sylvain Gaudard and contributors
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

package net.sourceforge.atunes.gui.views.panels;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import net.sourceforge.atunes.model.IFilterPanel;
import net.sourceforge.atunes.model.ILookAndFeelManager;
import net.sourceforge.atunes.model.INavigationTablePanel;
import net.sourceforge.atunes.model.ITable;

/**
 * Panel holding navigation table
 * 
 * @author alex
 * 
 */
public final class NavigationTablePanel extends JPanel implements
		INavigationTablePanel {

	private static final long serialVersionUID = -2900418193013495812L;

	private IFilterPanel navigatorTableFilterPanel;

	private ITable navigationTable;

	private ILookAndFeelManager lookAndFeelManager;

	/**
	 * Instantiates a new navigation panel.
	 */
	public NavigationTablePanel() {
		super(new BorderLayout(), true);
	}

	/**
	 * @param navigatorTableFilterPanel
	 */
	public void setNavigatorTableFilterPanel(
			IFilterPanel navigatorTableFilterPanel) {
		this.navigatorTableFilterPanel = navigatorTableFilterPanel;
	}

	/**
	 * Adds the content.
	 * 
	 * @param lookAndFeelManager
	 */
	public void initialize() {
		navigationTable
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// Disable autoresize, as we will control it
		navigationTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JPanel panel = navigatorTableFilterPanel.getSwingComponent();
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

		add(panel, BorderLayout.NORTH);
		add(lookAndFeelManager.getCurrentLookAndFeel().getTableScrollPane(
				navigationTable.getSwingComponent()), BorderLayout.CENTER);
	}

	/**
	 * @param navigationTable
	 */
	public void setNavigationTable(final ITable navigationTable) {
		this.navigationTable = navigationTable;
	}

	/**
	 * @param lookAndFeelManager
	 */
	public void setLookAndFeelManager(
			final ILookAndFeelManager lookAndFeelManager) {
		this.lookAndFeelManager = lookAndFeelManager;
	}

	/**
	 * Gets the navigation table.
	 * 
	 * @return the navigation table
	 */
	public ITable getNavigationTable() {
		return navigationTable;
	}

	@Override
	public JPanel getSwingComponent() {
		return this;
	}

	@Override
	public void showNavigationTableFilter(boolean show) {
		navigatorTableFilterPanel.setVisible(show);
	}
}
