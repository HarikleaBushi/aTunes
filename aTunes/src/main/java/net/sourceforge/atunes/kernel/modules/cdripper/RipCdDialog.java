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

package net.sourceforge.atunes.kernel.modules.cdripper;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.sourceforge.atunes.gui.ComponentOrientationTableCellRendererCode;
import net.sourceforge.atunes.gui.GuiUtils;
import net.sourceforge.atunes.gui.views.controls.AbstractCustomDialog;
import net.sourceforge.atunes.model.IBeanFactory;
import net.sourceforge.atunes.model.IControlsBuilder;
import net.sourceforge.atunes.model.IFrame;
import net.sourceforge.atunes.model.ILookAndFeel;
import net.sourceforge.atunes.utils.I18nUtils;

/**
 * The dialog for ripping cds
 */
public final class RipCdDialog extends AbstractCustomDialog {

	private static final long serialVersionUID = 1987727841297807350L;

	private JTextField albumArtistTextField;
	private JTextField albumTextField;
	private JTextField yearTextField;
	private JComboBox genreComboBox;
	private JButton titlesButton;
	private JComboBox format;
	private JLabel qualityLabel;
	private JComboBox quality;
	private JCheckBox useCdErrorCorrection;
	private JButton ok;
	private JButton cancel;
	private CdInfoTableModel tableModel;

	private JTextField discNumberField;

	private IBeanFactory beanFactory;

	private IControlsBuilder controlsBuilder;

	/**
	 * @param controlsBuilder
	 */
	public void setControlsBuilder(IControlsBuilder controlsBuilder) {
		this.controlsBuilder = controlsBuilder;
	}

	/**
	 * @param beanFactory
	 */
	public void setBeanFactory(final IBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	/**
	 * Instantiates a new rip cd dialog.
	 * 
	 * @param frame
	 */
	public RipCdDialog(final IFrame frame) {
		super(frame, 750, 650);
	}

	@Override
	public void initialize() {
		setTitle(I18nUtils.getString("RIP_CD"));
		setResizable(false);
		add(getContent(getLookAndFeel()));
	}

	/**
	 * @return
	 */
	public JTextField getDiscNumberField() {
		return discNumberField;
	}

	/**
	 * Gets the album text field.
	 * 
	 * @return the album text field
	 */
	public JTextField getAlbumTextField() {
		return albumTextField;
	}

	/**
	 * Gets the amazon button.
	 * 
	 * @return the amazon button
	 */
	public JButton getTitlesButton() {
		return titlesButton;
	}

	/**
	 * Gets the artist names.
	 * 
	 * @return the artist names
	 */
	public List<String> getArtistNames() {
		return tableModel.getArtistNames();
	}

	/**
	 * Gets the artist text field.
	 * 
	 * @return the artist text field
	 */
	public JTextField getArtistTextField() {
		return albumArtistTextField;
	}

	/**
	 * Gets the cancel.
	 * 
	 * @return the cancel
	 */
	public JButton getCancel() {
		return cancel;
	}

	/**
	 * Gets the composer names.
	 * 
	 * @return the composer names
	 */
	public List<String> getComposerNames() {
		return tableModel.getComposerNames();
	}

	/**
	 * Panel with basic fields: artist, album, ...
	 * 
	 * @return
	 */
	private JPanel getBasicPanel() {
		JPanel basicPanel = new JPanel(new GridBagLayout());

		JLabel albumArtistLabel = new JLabel(
				I18nUtils.getString("ALBUM_ARTIST"));
		albumArtistTextField = controlsBuilder.createTextField();

		JLabel albumLabel = new JLabel(I18nUtils.getString("ALBUM"));
		albumTextField = controlsBuilder.createTextField();

		JLabel cdLabel = new JLabel(I18nUtils.getString("DISC_NUMBER"));
		discNumberField = controlsBuilder.createTextField();

		JLabel yearLabel = new JLabel(I18nUtils.getString("YEAR"));
		yearTextField = controlsBuilder.createTextField();

		JLabel genreLabel = new JLabel(I18nUtils.getString("GENRE"));
		genreComboBox = new JComboBox();
		genreComboBox.setEditable(true);

		titlesButton = new JButton(I18nUtils.getString("GET_TITLES"));

		arrangeBasicPanel(basicPanel, albumArtistLabel, albumLabel, yearLabel,
				genreLabel, cdLabel);

		return basicPanel;
	}

	/**
	 * @param basicPanel
	 * @param albumArtistLabel
	 * @param albumLabel
	 * @param yearLabel
	 * @param genreLabel
	 * @param cdLabel
	 */
	private void arrangeBasicPanel(final JPanel basicPanel,
			final JLabel albumArtistLabel, final JLabel albumLabel,
			final JLabel yearLabel, final JLabel genreLabel,
			final JLabel cdLabel) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 5, 10);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.25;
		basicPanel.add(albumArtistLabel, c);

		c.gridx = 1;
		basicPanel.add(albumArtistTextField, c);

		c.gridx = 0;
		c.gridy = 1;
		basicPanel.add(albumLabel, c);

		c.gridx = 1;
		basicPanel.add(albumTextField, c);

		c.gridx = 0;
		c.gridy = 2;
		basicPanel.add(genreLabel, c);

		c.gridx = 1;
		basicPanel.add(genreComboBox, c);

		c.gridx = 2;
		c.gridy = 0;
		basicPanel.add(cdLabel, c);

		c.gridx = 3;
		basicPanel.add(discNumberField, c);

		c.gridx = 2;
		c.gridy = 1;
		basicPanel.add(yearLabel, c);

		c.gridx = 3;
		basicPanel.add(yearTextField, c);

		c.gridx = 2;
		c.gridy = 2;
		basicPanel.add(titlesButton, c);
	}

	private JPanel getAdvancedPanel() {
		JPanel advancedPanel = new JPanel(new GridBagLayout());

		JLabel formatLabel = new JLabel(I18nUtils.getString("ENCODE_TO"));

		format = new JComboBox();
		qualityLabel = new JLabel(I18nUtils.getString("QUALITY"));

		quality = new JComboBox(new String[] {});

		useCdErrorCorrection = new JCheckBox(
				I18nUtils.getString("USE_CD_ERROR_CORRECTION"));

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 5, 10);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		advancedPanel.add(formatLabel, c);
		c.gridx = 1;
		advancedPanel.add(format, c);

		c.gridx = 2;
		c.gridwidth = 3;
		advancedPanel.add(useCdErrorCorrection, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.gridwidth = 1;
		advancedPanel.add(qualityLabel, c);
		c.gridx = 1;
		advancedPanel.add(quality, c);

		return advancedPanel;
	}

	/**
	 * Defines the content of the dialog box.
	 * 
	 * @param iLookAndFeel
	 * 
	 * @return the content
	 */
	private JPanel getContent(final ILookAndFeel iLookAndFeel) {
		JPanel panel = new JPanel(new GridBagLayout());

		tableModel = new CdInfoTableModel();

		JTable table = getTable(iLookAndFeel);
		JScrollPane scrollPane = iLookAndFeel.getTableScrollPane(table);

		// Here we define the cd ripper dialog display layout
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(20, 20, 10, 20);
		panel.add(scrollPane, c);

		c.gridy = 1;
		c.weighty = 0;
		c.insets = new Insets(20, 20, 0, 20);
		c.anchor = GridBagConstraints.WEST;
		panel.add(getBasicPanel(), c);

		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		panel.add(getAdvancedPanel(), c);

		c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(20, 0, 20, 0);
		panel.add(getOkCancelPanel(), c);

		return panel;
	}

	/**
	 * @return
	 */
	private JPanel getOkCancelPanel() {
		ok = new JButton(I18nUtils.getString("OK"));
		cancel = new JButton(I18nUtils.getString("CANCEL"));

		JPanel okCancelPanel = new JPanel();
		okCancelPanel.setOpaque(false);
		okCancelPanel.add(ok);
		okCancelPanel.add(cancel);
		return okCancelPanel;
	}

	/**
	 * @param iLookAndFeel
	 * @return
	 */
	private JTable getTable(final ILookAndFeel iLookAndFeel) {
		JTable table = iLookAndFeel.getTable();
		table.setModel(tableModel);

		table.getColumnModel().getColumn(0).setMaxWidth(20); // Width of check
		table.getColumnModel().getColumn(4).setMaxWidth(50); // Width of
																// duration

		JCheckBox checkBox = new JCheckBox();
		table.getColumnModel().getColumn(0)
				.setCellEditor(new DefaultCellEditor(checkBox));

		JTextField textfield1 = controlsBuilder.createTextField();
		JTextField textfield2 = controlsBuilder.createTextField();
		JTextField textfield3 = controlsBuilder.createTextField();
		textfield1.setBorder(BorderFactory.createEmptyBorder());
		textfield2.setBorder(BorderFactory.createEmptyBorder());
		textfield3.setBorder(BorderFactory.createEmptyBorder());
		GuiUtils.applyComponentOrientation(textfield1);
		GuiUtils.applyComponentOrientation(textfield2);
		GuiUtils.applyComponentOrientation(textfield3);

		table.getColumnModel().getColumn(1)
				.setCellEditor(new DefaultCellEditor(textfield1));
		table.getColumnModel().getColumn(2)
				.setCellEditor(new DefaultCellEditor(textfield2));
		table.getColumnModel().getColumn(3)
				.setCellEditor(new DefaultCellEditor(textfield3));

		table.setDefaultRenderer(
				String.class,
				iLookAndFeel.getTableCellRenderer(beanFactory
						.getBean(ComponentOrientationTableCellRendererCode.class)));
		return table;
	}

	/**
	 * Gets the format.
	 * 
	 * @return the format
	 */
	public JComboBox getFormat() {
		return format;
	}

	/**
	 * Gets the genre combo box.
	 * 
	 * @return the genre combo box
	 */
	public JComboBox getGenreComboBox() {
		return genreComboBox;
	}

	/**
	 * Gets the ok.
	 * 
	 * @return the ok
	 */
	public JButton getOk() {
		return ok;
	}

	/**
	 * Gets the quality.
	 * 
	 * @return the quality
	 */
	public String getQuality() {
		return (String) quality.getSelectedItem();
	}

	/**
	 * Gets the quality combo box.
	 * 
	 * @return the quality combo box
	 */
	public JComboBox getQualityComboBox() {
		return quality;
	}

	/**
	 * @return
	 */
	public JLabel getQualityLabel() {
		return qualityLabel;
	}

	/**
	 * Gets the track names.
	 * 
	 * @return the track names
	 */
	public List<String> getTrackNames() {
		return tableModel.getTrackNames();
	}

	/**
	 * Gets the tracks selected.
	 * 
	 * @return the tracks selected
	 */

	public List<Integer> getTracksSelected() {
		List<Boolean> tracks = tableModel.getTracksSelected();
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < tracks.size(); i++) {
			if (tracks.get(i)) {
				result.add(i + 1);
			}
		}
		return result;
	}

	/**
	 * Gets the CD error correction checkbox
	 * 
	 * @return The cd error correction checkbox
	 */
	public JCheckBox getUseCdErrorCorrection() {
		return useCdErrorCorrection;
	}

	/**
	 * Gets the year text field.
	 * 
	 * @return the year text field
	 */
	public JTextField getYearTextField() {
		return yearTextField;
	}

	/**
	 * Sets the table data.
	 * 
	 * @param cdInfo
	 *            the new table data
	 */
	public void setTableData(final CDInfo cdInfo) {
		tableModel.setCDInfo(cdInfo);
		tableModel.fireTableDataChanged();
	}

	/**
	 * Update artist names.
	 * 
	 * @param cdInfo
	 */
	public void updateArtistNames(final CDInfo cdInfo) {
		// Fill names of artists
		// Each track has an artist which can be "" if it's the same artist of
		// all CD tracks
		List<String> names = new ArrayList<String>();
		for (String artist : cdInfo.getArtists()) {
			if (!artist.trim().equals("")) {
				names.add(artist);
			} else {
				// TODO if cdda2wav is modified for detecting song artist modify
				// here
				if (cdInfo.getArtist() != null
						&& !cdInfo.getArtist().trim().equals("")) {
					names.add(cdInfo.getArtist());
				} else {
					names.add("");
				}
			}
		}

		tableModel.setArtistNames(names);
		tableModel.fireTableDataChanged();
	}

	/**
	 * Update composer names.
	 * 
	 * @param names
	 *            the names
	 */
	public void updateComposerNames(final List<String> names) {
		tableModel.setComposerNames(names);
		tableModel.fireTableDataChanged();
	}

	/**
	 * Update track names.
	 * 
	 * @param names
	 *            the names
	 */
	public void updateTrackNames(final List<String> names) {
		tableModel.setTrackNames(names);
		tableModel.fireTableDataChanged();
	}

	@Override
	public void showDialog() {
		setVisible(true);
	}

	@Override
	public void hideDialog() {
		setVisible(false);
	}
}
