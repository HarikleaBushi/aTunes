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

package net.sourceforge.atunes.gui.views.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sourceforge.atunes.Context;
import net.sourceforge.atunes.gui.views.controls.AbstractCustomDialog;
import net.sourceforge.atunes.gui.views.controls.CustomTextField;
import net.sourceforge.atunes.model.IFrame;
import net.sourceforge.atunes.model.IIconFactory;
import net.sourceforge.atunes.model.ILookAndFeelManager;
import net.sourceforge.atunes.model.IRadio;
import net.sourceforge.atunes.model.IRadioDialog;
import net.sourceforge.atunes.model.IRadioHandler;
import net.sourceforge.atunes.utils.I18nUtils;

/**
 * Dialog to add or edit a radio
 * @author fleax
 *
 */
public final class RadioDialog extends AbstractCustomDialog implements IRadioDialog {

    private static final long serialVersionUID = 7295438534550341824L;

    private JTextField nameTextField;
    private JTextField urlTextField;
    private JTextField labelTextField;
    
    private IRadioHandler radioHandler;

    /** The radio. */
    private IRadio result;
    
    /**
     * Instantiates a new radio dialog for adding a new radio
     * 
     * @param frame
     * @param lookAndFeelManager
     */
    public RadioDialog(IFrame frame, ILookAndFeelManager lookAndFeelManager) {
        super(frame, 500, 200, true, CloseAction.DISPOSE, lookAndFeelManager.getCurrentLookAndFeel());
        setTitle(I18nUtils.getString("ADD_RADIO"));
        setResizable(false);
        add(getContent());
    }
    
    /**
     * @param radioHandler
     */
    public void setRadioHandler(IRadioHandler radioHandler) {
		this.radioHandler = radioHandler;
	}

    /* (non-Javadoc)
	 * @see net.sourceforge.atunes.gui.views.dialogs.IRadioDialog#setRadio(net.sourceforge.atunes.kernel.modules.radio.Radio)
	 */
    @Override
	public void setRadio(IRadio radio) {
        setTitle(radio != null ? I18nUtils.getString("EDIT_RADIO") : I18nUtils.getString("ADD_RADIO"));
        nameTextField.setText(radio != null ? radio.getName() : null);
        urlTextField.setText(radio != null ? radio.getUrl() : null);
        labelTextField.setText(radio != null ? radio.getLabel() : null);
	}
    
    /**
     * Gets the content.
     * 
     * @return the content
     */
    private JPanel getContent() {
        JPanel panel = new JPanel(new GridBagLayout());

        JLabel nameLabel = new JLabel(I18nUtils.getString("NAME"));
        nameTextField = new CustomTextField();
        JLabel urlLabel = new JLabel(I18nUtils.getString("URL"));
        urlTextField = new CustomTextField();
        JLabel labelLabel = new JLabel(I18nUtils.getString("LABEL"));
        labelTextField = new CustomTextField();

        JButton okButton = new JButton(I18nUtils.getString("OK"));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	result = radioHandler.createRadio(nameTextField.getText(), urlTextField.getText(), labelTextField.getText());
                RadioDialog.this.dispose();
            }
        });
        JButton cancelButton = new JButton(I18nUtils.getString("CANCEL"));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RadioDialog.this.dispose();
            }
        });

        arrangePanel(panel, nameLabel, urlLabel, labelLabel, okButton,
				cancelButton);

        return panel;
    }

	/**
	 * @param panel
	 * @param nameLabel
	 * @param urlLabel
	 * @param labelLabel
	 * @param okButton
	 * @param cancelButton
	 */
	private void arrangePanel(JPanel panel, JLabel nameLabel, JLabel urlLabel,
			JLabel labelLabel, JButton okButton, JButton cancelButton) {
		GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 5, 10);
        panel.add(nameLabel, c);
        c.gridx = 2;
        c.weightx = 1;
        panel.add(nameTextField, c);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0;
        panel.add(urlLabel, c);
        c.gridx = 2;
        c.weightx = 1;
        panel.add(urlTextField, c);
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0;
        panel.add(labelLabel, c);
        c.gridx = 2;
        c.weightx = 1;
        panel.add(labelTextField, c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        c.fill = GridBagConstraints.NONE;
        c.weightx = -1;
        panel.add(new JLabel(Context.getBean("radioMediumIcon", IIconFactory.class).getIcon(getLookAndFeel().getPaintForSpecialControls())), c);

        JPanel auxPanel = new JPanel();
        auxPanel.add(okButton);
        auxPanel.add(cancelButton);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.insets = new Insets(0, 0, 0, 0);
        panel.add(auxPanel, c);
	}

    @Override
	public IRadio getRadio() {
        return result;
    }
    
    @Override
    public void showDialog() {
    	setVisible(true);
    }

}
