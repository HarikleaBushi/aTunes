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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import net.sourceforge.atunes.gui.ComponentOrientationTableCellRendererCode;
import net.sourceforge.atunes.gui.views.controls.AbstractCustomDialog;
import net.sourceforge.atunes.model.IBeanFactory;
import net.sourceforge.atunes.model.IColumn;
import net.sourceforge.atunes.model.IColumnSelectorDialog;
import net.sourceforge.atunes.model.IColumnSet;
import net.sourceforge.atunes.model.IControlsBuilder;
import net.sourceforge.atunes.model.IFrame;
import net.sourceforge.atunes.model.ILookAndFeel;
import net.sourceforge.atunes.utils.I18nUtils;

/**
 * Dialog to select column set
 */
public final class ColumnSetSelectorDialog extends AbstractCustomDialog
		implements IColumnSelectorDialog {

	private class ColumnsTableModel implements TableModel {

		private static final long serialVersionUID = 5251001708812824836L;

		private List<IColumn<?>> columns;
		private final List<TableModelListener> listeners = new ArrayList<TableModelListener>();

		/**
		 * Instantiates a new columns table model.
		 */
		ColumnsTableModel() {
			// Nothing to do
		}

		@Override
		public void addTableModelListener(final TableModelListener l) {
			this.listeners.add(l);
		}

		@Override
		public Class<?> getColumnClass(final int columnIndex) {
			return columnIndex == 0 ? Boolean.class : String.class;
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public String getColumnName(final int column) {
			return "";
		}

		@Override
		public int getRowCount() {
			if (this.columns != null) {
				return this.columns.size();
			}
			return 0;
		}

		@Override
		public Object getValueAt(final int rowIndex, final int columnIndex) {
			if (columnIndex == 0) {
				return this.columns.get(rowIndex).isVisible();
			}
			return I18nUtils.getString(this.columns.get(rowIndex)
					.getColumnName());
		}

		@Override
		public boolean isCellEditable(final int rowIndex, final int columnIndex) {
			return columnIndex == 0;
		}

		/**
		 * Move down.
		 * 
		 * @param columnPos
		 *            the column pos
		 */
		public void moveDown(final int columnPos) {
			// Get this column and previous
			IColumn<?> columnSelected = this.columns.get(columnPos);
			IColumn<?> nextColumn = this.columns.get(columnPos + 1);

			// Swap order
			int aux = columnSelected.getOrder();
			columnSelected.setOrder(nextColumn.getOrder());
			nextColumn.setOrder(aux);

			// Swap position on columns array
			this.columns.remove(nextColumn);
			this.columns.add(columnPos, nextColumn);

			TableModelEvent event;
			event = new TableModelEvent(this, TableModelEvent.ALL_COLUMNS,
					TableModelEvent.UPDATE);

			for (int i = 0; i < this.listeners.size(); i++) {
				this.listeners.get(i).tableChanged(event);
			}

			ColumnSetSelectorDialog.this.columnsList.getColumnModel()
					.getColumn(0).setMaxWidth(20);

			ColumnSetSelectorDialog.this.columnsList.getSelectionModel()
					.setSelectionInterval(columnPos + 1, columnPos + 1);
		}

		/**
		 * Move up.
		 * 
		 * @param columnPos
		 *            the column pos
		 */
		public void moveUp(final int columnPos) {
			// Get this column and previous
			IColumn<?> columnSelected = this.columns.get(columnPos);
			IColumn<?> previousColumn = this.columns.get(columnPos - 1);

			// Swap order
			int aux = columnSelected.getOrder();
			columnSelected.setOrder(previousColumn.getOrder());
			previousColumn.setOrder(aux);

			// Swap position on columns array
			this.columns.remove(previousColumn);
			this.columns.add(columnPos, previousColumn);

			TableModelEvent event;
			event = new TableModelEvent(this, TableModelEvent.ALL_COLUMNS,
					TableModelEvent.UPDATE);

			for (int i = 0; i < this.listeners.size(); i++) {
				this.listeners.get(i).tableChanged(event);
			}

			ColumnSetSelectorDialog.this.columnsList.getColumnModel()
					.getColumn(0).setMaxWidth(20);

			ColumnSetSelectorDialog.this.columnsList.getSelectionModel()
					.setSelectionInterval(columnPos - 1, columnPos - 1);
		}

		@Override
		public void removeTableModelListener(final TableModelListener l) {
			this.listeners.remove(l);
		}

		public void setColumns(final List<IColumn<?>> columns) {
			this.columns = columns;
			Collections.sort(this.columns);
		}

		@Override
		public void setValueAt(final Object aValue, final int rowIndex,
				final int columnIndex) {
			if (columnIndex == 0) {
				this.columns.get(rowIndex).setVisible((Boolean) aValue);
			}
		}
	}

	private static final long serialVersionUID = -7592059207162524630L;

	private JTable columnsList;
	private ColumnsTableModel model;

	private IBeanFactory beanFactory;

	/**
	 * @param beanFactory
	 */
	public void setBeanFactory(final IBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	/**
	 * Instantiates a new play list column selector.
	 * 
	 * @param frame
	 * @param controlsBuilder
	 */
	public ColumnSetSelectorDialog(final IFrame frame,
			final IControlsBuilder controlsBuilder) {
		super(frame, 250, 300, controlsBuilder);
	}

	@Override
	public void initialize() {
		add(getContent(getLookAndFeel()));
		setTitle(I18nUtils.getString("ARRANGE_COLUMNS"));
		setResizable(false);
		// TODO: Add pack to all dialogs
		pack();
	}

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	private JPanel getContent(final ILookAndFeel lookAndFeel) {
		JPanel panel = new JPanel(new GridBagLayout());

		this.model = new ColumnsTableModel();

		this.columnsList = lookAndFeel.getTable();
		this.columnsList.setModel(this.model);
		this.columnsList.setTableHeader(null);
		this.columnsList.getColumnModel().getColumn(0).setMaxWidth(20);
		this.columnsList.getColumnModel().getColumn(0)
				.setCellEditor(new DefaultCellEditor(new JCheckBox()));
		this.columnsList.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		this.columnsList
				.setDefaultRenderer(
						String.class,
						lookAndFeel.getTableCellRenderer(this.beanFactory
								.getBean(ComponentOrientationTableCellRendererCode.class)));

		JScrollPane scrollPane = lookAndFeel
				.getTableScrollPane(this.columnsList);
		JLabel label = new JLabel(I18nUtils.getString("SELECT_COLUMNS"));
		JButton okButton = new JButton(I18nUtils.getString("OK"));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				hideDialog();
			}
		});

		JButton upButton = new JButton(I18nUtils.getString("MOVE_UP"));
		upButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				int selectedColumn = ColumnSetSelectorDialog.this.columnsList
						.getSelectedRow();
				// If some column has been selected, not the first one
				if (selectedColumn > 0) {
					ColumnSetSelectorDialog.this.model.moveUp(selectedColumn);
				}
			}
		});

		JButton downButton = new JButton(I18nUtils.getString("MOVE_DOWN"));
		downButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				int selectedColumn = ColumnSetSelectorDialog.this.columnsList
						.getSelectedRow();
				// If some column has been selected, not the last one
				if (selectedColumn < ColumnSetSelectorDialog.this.columnsList
						.getModel().getRowCount() - 1) {
					ColumnSetSelectorDialog.this.model.moveDown(selectedColumn);
				}
			}
		});

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		panel.add(label, c);

		c.gridy = 1;
		c.weighty = 1;
		c.gridheight = 2;
		panel.add(scrollPane, c);

		c.gridx = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.SOUTH;
		c.insets = new Insets(10, 0, 10, 10);
		panel.add(upButton, c);

		c.gridy = 2;
		c.anchor = GridBagConstraints.NORTH;
		panel.add(downButton, c);

		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0;
		c.gridwidth = 2;
		c.insets = new Insets(10, 10, 10, 10);
		panel.add(okButton, c);

		return panel;
	}

	@Override
	public void setColumnSetToSelect(final IColumnSet columnSet) {
		this.model.setColumns(columnSet.getColumnsForSelection());
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
