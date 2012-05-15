/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information. OrbisGIS is
 * distributed under GPL 3 license. It is produced by the "Atelier SIG" team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/> CNRS FR 2488.
 * 
 *
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisGIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 *
 * or contact directly:
 * info _at_ orbisgis.org
 */
package org.orbisgis.view.map;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.TransferHandler;
import org.apache.log4j.Logger;
import org.orbisgis.core.events.EventException;
import org.orbisgis.core.events.Listener;
import org.orbisgis.core.events.ListenerContainer;
import org.orbisgis.view.edition.EditableElement;
import org.orbisgis.view.edition.TransferableEditableElement;
import org.orbisgis.view.geocatalog.EditableSource;
import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

/**
 * Swing Handler for dragging EditableElement
 * Support MapElement and EditableSource
 */
public class MapTransferHandler  extends TransferHandler{
    static final private Logger GUILOGGER = Logger.getLogger("gui."+MapTransferHandler.class);
    static final private I18n I18N = I18nFactory.getI18n(MapTransferHandler.class);
    private ListenerContainer<EditableTransferEvent> transferEditableEvent = new ListenerContainer<EditableTransferEvent>();
    /**
     * MapEditor support MapElement and EditableSource only
     * @param editableArray Array Of Editable
     * @return 
     */
    private boolean canImportEditableElement(EditableElement[] editableArray) {
        for(EditableElement ee : editableArray) {
            if(!ee.getTypeId().equals(EditableSource.EDITABLE_RESOURCE_TYPE) &&
                !ee.getTypeId().equals(MapElement.EDITABLE_TYPE)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean canImport(TransferSupport ts) {
        return ts.isDataFlavorSupported(TransferableEditableElement.editableElementFlavor);
    }

    /**
     * To add and remove editable transfer listener
     * @return 
     */
    public ListenerContainer<EditableTransferEvent> getTransferEditableEvent() {
        return transferEditableEvent;
    }

    
    @Override
    public boolean importData(TransferSupport ts) {
        //cancel the import if it is not a drop operation
        if(!ts.isDrop()) {
            return false;
        }
        
        if(ts.isDataFlavorSupported(TransferableEditableElement.editableElementFlavor)) {
            //This is an Editable Element
            try {
                //A transferable element
                Transferable trans = ts.getTransferable();
                EditableElement[] editableList = (EditableElement[])trans.getTransferData(TransferableEditableElement.editableElementFlavor);
                if(canImportEditableElement(editableList)) {
                    try {
                        //All elements are compatible
                        transferEditableEvent.callListeners(new EditableTransferEvent(editableList, ts.getComponent()));
                    } catch (EventException ex) {
                        GUILOGGER.error(I18N.tr("Error while drop Editable"),ex);
                        return false;
                    }
                    return true;
                }else{
                    GUILOGGER.error(I18N.tr("The map editor accept only map and geometry data source."));
                }
            } catch (UnsupportedFlavorException ex) {
                return false;
            } catch (IOException ex) {
                return false;
            }            
        }        
        return false;
    }
    
}