/*
 * aTunes 2.1.0-SNAPSHOT
 * Copyright (C) 2006-2010 Alex Aranda, Sylvain Gaudard and contributors
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sourceforge.atunes.gui.views.controls.CustomTextField;
import net.sourceforge.atunes.gui.views.controls.PopUpButton;
import net.sourceforge.atunes.utils.I18nUtils;
import net.sourceforge.atunes.utils.StringUtils;

public class FilterPanel extends JPanel {

    private static final long serialVersionUID = 1801321624657098000L;

    private PopUpButton filterButton;
    private JTextField filterTextField;

    public FilterPanel() {
        super(new GridBagLayout());
        addContent();
    }

    private void addContent() {
        filterButton = new PopUpButton(PopUpButton.TOP_RIGHT);
        filterTextField = new CustomTextField(13);
        filterTextField.setText(StringUtils.getString(I18nUtils.getString("FILTER"), "..."));
        filterTextField.setToolTipText(I18nUtils.getString("FILTER_TEXTFIELD_TOOLTIP"));
        setMinimumSize(filterTextField.getPreferredSize());
        setPreferredSize(filterTextField.getPreferredSize());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1, 0, 1, 0);
        add(filterButton, c);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        add(filterTextField, c);
    }

    /**
     * @return the filterButton
     */
    public PopUpButton getFilterButton() {
        return filterButton;
    }

    /**
     * @return the filterTextField
     */
    public JTextField getFilterTextField() {
        return filterTextField;
    }

}
