/**
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information.
 *
 * OrbisGIS is distributed under GPL 3 license. It is produced by the "Atelier SIG"
 * team of the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 *
 * Copyright (C) 2007-1012 IRSTV (FR CNRS 2488)
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
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.view.table;

import java.util.Set;
import org.gdms.data.DataSource;
import org.orbisgis.core.common.IntegerUnion;
import org.orbisgis.core.layerModel.ILayer;
import org.orbisgis.progress.ProgressMonitor;
import org.orbisgis.view.edition.EditableElement;
import org.orbisgis.view.edition.EditableElementException;

/**
 * Interface to be implemented by those EditableElements that need to be edited
 * by the table editor.
 * 
 */
public class TableEditableElement extends EditableElement {
        public static final String TYPE_ID = "TableEditableElement";
        // Properties names
        public static final String PROP_SELECTION = "selection";
        
        // Properties
        protected Set<Integer> selection;
        protected DataSource dataSource;

        public TableEditableElement(DataSource dataSource) {
                this.dataSource = dataSource;
                this.selection = new IntegerUnion();
        }

        public TableEditableElement(Set<Integer> selection, DataSource dataSource) {
                this.selection = new IntegerUnion(selection);
                this.dataSource = dataSource;
        }
        
        
        

        public DataSource getDataSource() {
                return dataSource;
        }
        
	/**
	 * Get the object that manages selection
	 * 
	 * @return
	 */
	Set<Integer> getSelection() {
                return selection;
        }

        public void setSelection(Set<Integer> selection) {
                Set<Integer> oldSelection = this.selection;
                this.selection = new IntegerUnion(selection);
                propertyChangeSupport.firePropertyChange(PROP_SELECTION, oldSelection, this.selection);
        }
        
        

        @Override
        public String getTypeId() {
                return TYPE_ID;
        }

        @Override
        public void open(ProgressMonitor progressMonitor) throws UnsupportedOperationException, EditableElementException {
                throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void save() throws UnsupportedOperationException, EditableElementException {
                throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void close(ProgressMonitor progressMonitor) throws UnsupportedOperationException, EditableElementException {
                throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Object getObject() throws UnsupportedOperationException {
                return dataSource;
        }
     
	
	
}
