/*
 * aTunes 2.1.0-SNAPSHOT
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

import java.util.Collection;
import java.util.List;

import net.sourceforge.atunes.Context;
import net.sourceforge.atunes.kernel.AbstractHandler;
import net.sourceforge.atunes.kernel.modules.plugins.PluginsHandler;
import net.sourceforge.atunes.misc.log.Logger;
import net.sourceforge.atunes.model.IAudioObject;
import net.sourceforge.atunes.model.ILocalAudioObject;
import net.sourceforge.atunes.model.IRadio;
import net.sourceforge.atunes.model.IState;

import org.commonjukebox.plugins.exceptions.PluginSystemException;
import org.commonjukebox.plugins.model.Plugin;
import org.commonjukebox.plugins.model.PluginInfo;
import org.commonjukebox.plugins.model.PluginListener;

public final class ContextHandler extends AbstractHandler implements PluginListener {

    /**
     * Singleton instance of handler
     */
    private static ContextHandler instance;

    private ContextController controller;
    
    /**
     * The current audio object used to retrieve information
     */
    private IAudioObject currentAudioObject;

    /**
     * Time stamp when audio object was modified. Used to decide if context info
     * must be updated
     */
    private long lastAudioObjectModificationTime;

    /**
     * Context panels defined
     */
    private List<AbstractContextPanel> contextPanels;

    @Override
    protected void initHandler() {
    }

    @SuppressWarnings("unchecked")
	@Override
    public void applicationStarted(List<IAudioObject> playList) {
    	
    	contextPanels = (List<AbstractContextPanel>) Context.getBean("contextPanels");
    	
    	getController().addContextPanels(contextPanels);
    	
        // Set previous selected tab
    	getController().setContextTab(getState().getSelectedContextTab());
    	
    	// Enable listener for user selections
    	getController().enableContextComboListener();
    }
    
    /**
     * Gets the single instance of ContextHandler.
     * 
     * @return single instance of ContextHandler
     */
    public static ContextHandler getInstance() {
        if (instance == null) {
            instance = new ContextHandler();
        }
        return instance;
    }

    /**
     * Called when user changes context tab
     */
    void contextPanelChanged() {
        // Update selected tab
        getState().setSelectedContextTab(getController().getContextTab() != null ? getController().getContextTab().getContextPanelName() : null);
        // Call to fill information: Don't force update since audio object can be the same
        retrieveInfo(currentAudioObject, false);
    }

    /**
     * Clears all context panels
     * 
     */
    private void clearContextPanels() {
        clearTabsContent();
        currentAudioObject = null;

        // Select first tab
        getState().setSelectedContextTab(null);
        getController().setContextTab(null);
    }

    /**
     * Clears tabs content
     */
    private void clearTabsContent() {
        // Clear all context panels
        for (AbstractContextPanel panel : contextPanels) {
            panel.clearContextPanel();
        }
    }

    /**
     * Updates panel with audio object information.
     * 
     * @param ao
     *            the audio object
     */
    public void retrieveInfoAndShowInPanel(IAudioObject ao) {
        boolean audioObjectModified = false;
        // Avoid retrieve information about the same audio object twice except if is an LocalAudioObject and has been recently changed
        if (currentAudioObject != null && currentAudioObject.equals(ao)) {
            if (ao instanceof ILocalAudioObject) {
                if (((ILocalAudioObject) ao).getFile().lastModified() == lastAudioObjectModificationTime) {
                    return;
                } else {
                    audioObjectModified = true;
                }
            } else if (!(ao instanceof IRadio)) {
                return;
            }
        }
        currentAudioObject = ao;

        // Update modification time if necessary
        if (ao instanceof ILocalAudioObject) {
            lastAudioObjectModificationTime = ((ILocalAudioObject) ao).getFile().lastModified();
        } else {
            lastAudioObjectModificationTime = 0;
        }

        if (getState().isUseContext()) {
            // Enable or disable tabs
            getController().updateContextTabs();

            if (ao == null) {
                // Clear all tabs
                clearContextPanels();
            } else {
                if (audioObjectModified) {
                    clearTabsContent();
                }
                // Retrieve data for audio object. Force Update since audio file is different or has been modified
                retrieveInfo(ao, true);
            }
        }
    }

    /**
     * Retrieve info.
     * 
     * @param audioObject
     *            the audio object
     * @param forceUpdate
     *            If <code>true</code> data will be retrieved and shown even if
     *            the audio object is the same as before This is necessary when
     *            audio object is the same but has been modified so context data
     *            can be different
     */
    private void retrieveInfo(IAudioObject audioObject, boolean forceUpdate) {
        if (audioObject == null) {
            return;
        }

        // Context panel can be removed so check index
        String selectedTab = getState().getSelectedContextTab();
        // Update current context panel
        for (AbstractContextPanel panel : contextPanels) {
        	if (panel.getContextPanelName().equals(selectedTab)) {
        		panel.updateContextPanel(audioObject, forceUpdate);
        		break;
        	}
        }
    }

    /**
     * Finish context information
     */
    public void applicationFinish() {
    }

    @Override
    public void applicationStateChanged(IState newState) {
    }

    /**
     * @return the lastAudioObject
     */
    public IAudioObject getCurrentAudioObject() {
        return currentAudioObject;
    }

    @Override
    public void pluginActivated(PluginInfo plugin) {
        try {
            AbstractContextPanel newPanel = (AbstractContextPanel) PluginsHandler.getInstance().getNewInstance(plugin);
            contextPanels.add(newPanel);
        } catch (PluginSystemException e) {
            Logger.error(e);
        }
    }

    @Override
    public void pluginDeactivated(PluginInfo plugin, Collection<Plugin> createdInstances) {
        for (Plugin instance : createdInstances) {
        	contextPanels.remove(instance);
            getController().removeContextPanel((AbstractContextPanel) instance);
        }
    }
    
    @Override
    public void selectedAudioObjectChanged(IAudioObject audioObject) {
        if (getState().isUseContext()) {
            retrieveInfoAndShowInPanel(audioObject);
        }
    }
    
    @Override
    public void playListCleared() {
        if (getState().isUseContext()) {
            retrieveInfoAndShowInPanel(null);
            
            if (getState().isStopPlayerOnPlayListClear()) {
            	ContextHandler.getInstance().clearContextPanels();
            }
        }
    }
    
    private ContextController getController() {
    	if (controller == null) {
    		controller = new ContextController(getFrame().getContextPanel(), getState());
    	}
    	return controller;
    }
}
