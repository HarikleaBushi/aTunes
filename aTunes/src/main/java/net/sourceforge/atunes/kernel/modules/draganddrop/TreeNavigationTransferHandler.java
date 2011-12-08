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

package net.sourceforge.atunes.kernel.modules.draganddrop;

import java.awt.datatransfer.DataFlavor;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import net.sourceforge.atunes.Context;
import net.sourceforge.atunes.gui.TransferableList;
import net.sourceforge.atunes.model.INavigationHandler;
import net.sourceforge.atunes.utils.Logger;

/**
 * Some methods of this class about how to drag and drop from Gnome/KDE file
 * managers taken from:
 * 
 * http://www.davidgrant.ca/drag_drop_from_linux_kde_gnome_file_managers_konqueror_nautilus_to_java_applications
 * 
 */
public class TreeNavigationTransferHandler extends TransferHandler {


	private static final long serialVersionUID = 4589661138322620954L;

	/**
     * Data flavor of a list of objects dragged from inside application
     */
    private static DataFlavor internalDataFlavor;

   
    static {
        try {
            internalDataFlavor = new DataFlavor(TransferableList.mimeType);
        } catch (ClassNotFoundException e) {
            Logger.error(e);
        }
    }

    @Override
    public boolean canImport(TransferSupport support) {
        // Check if internal data flavor is supported
        if (support.getTransferable().isDataFlavorSupported(internalDataFlavor)) {
            try {
                List<?> listOfObjectsDragged = (List<?>) support.getTransferable().getTransferData(internalDataFlavor);
                if (listOfObjectsDragged == null || listOfObjectsDragged.isEmpty()) {
                    return false;
                }

                // Drag is made from another component...
                if (listOfObjectsDragged.get(0) instanceof PlayListDragableRow) {
                    return true;
                }

                return true;
            } catch (Exception e) {
                Logger.error(e);
            }

            support.setShowDropLocation(true);
            return true;
        }

        
        return false;
    }

    
   
    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        if (support.getTransferable().isDataFlavorSupported(internalDataFlavor)) {
            return processInternalImport(support);
        }

        return false;
    }

    /**
     * Perform drop with data dragged from another component
     * 
     * @param support
     * @return
     */
    private static boolean processInternalImport(TransferSupport support) {
        try {
            List<?> listOfObjectsDragged = (List<?>) support.getTransferable().getTransferData(internalDataFlavor);
            if (listOfObjectsDragged == null || listOfObjectsDragged.isEmpty()) {
                return false;
            }

            
            // DRAG AND DROP OF AN ARTIST -> SELECT IN NAVIGATOR
            if (listOfObjectsDragged.get(0) instanceof DragableArtist) {
            	final DragableArtist draggedArtist = (DragableArtist)listOfObjectsDragged.get(0);
            	SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						Context.getBean(INavigationHandler.class).selectArtist(draggedArtist.getArtistInfo().getName());
					}
				});
            	
            }
            
            // DRAG AND DROP OF A PLAY LIST ITEM -> SELECT IN NAVIGATOR
            if (listOfObjectsDragged.get(0) instanceof PlayListDragableRow){
            	final PlayListDragableRow playlistRow = (PlayListDragableRow)listOfObjectsDragged.get(0);
            	SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						Context.getBean(INavigationHandler.class).selectAudioObject(playlistRow.getRowContent());
					}
				});
            	
            }

            
        } catch (Exception e) {
            Logger.error(e);
        }

        return false;
    }


   
    
}
