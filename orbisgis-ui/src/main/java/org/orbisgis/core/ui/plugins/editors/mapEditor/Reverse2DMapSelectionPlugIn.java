/**
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information.
 *
 * OrbisGIS is distributed under GPL 3 license. It is produced by the "Atelier SIG"
 * team of the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 *
 * Copyright (C) 2007-2012 IRSTV (FR CNRS 2488)
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
package org.orbisgis.core.ui.plugins.editors.mapEditor;

import com.vividsolutions.jts.geom.Geometry;
import java.util.Arrays;

import javax.swing.JButton;
import org.gdms.data.DataSource;

import org.gdms.driver.DriverException;
import org.orbisgis.core.layerModel.ILayer;
import org.orbisgis.core.layerModel.MapContext;
import org.orbisgis.core.ui.editor.IEditor;
import org.orbisgis.core.ui.pluginSystem.AbstractPlugIn;
import org.orbisgis.core.ui.pluginSystem.PlugInContext;
import org.orbisgis.core.ui.pluginSystem.message.ErrorMessages;
import org.orbisgis.core.ui.pluginSystem.workbench.WorkbenchContext;
import org.orbisgis.core.ui.plugins.views.mapEditor.MapEditorPlugIn;
import org.orbisgis.core.ui.preferences.lookandfeel.images.IconLoader;

public class Reverse2DMapSelectionPlugIn extends AbstractPlugIn {

        private JButton btn;

        public Reverse2DMapSelectionPlugIn() {
                btn = new JButton(IconLoader.getIcon("reverse2dgeometry.png"));
                btn.setToolTipText("Reverse a geometry");
        }

        public boolean execute(PlugInContext context) throws Exception {
                IEditor editor = getPlugInContext().getActiveEditor();
                MapContext map = (MapContext) editor.getElement().getObject();
                ILayer activeLayer = map.getActiveLayer();
                int[] sel = activeLayer.getSelection().clone();
                Arrays.sort(sel);
                DataSource dataSource = activeLayer.getDataSource();
                try {
                        dataSource.setDispatchingMode(DataSource.STORE);
                        for (int i = sel.length - 1; i >= 0; i--) {
                                Geometry geom = dataSource.getGeometry(sel[i]);
                                dataSource.setGeometry(sel[i],  geom.reverse());
                        }
			dataSource.setDispatchingMode(DataSource.DISPATCH);
                } catch (DriverException e) {
                        ErrorMessages.error("Cannot reverse the geometry", e);
                }
                return true;
        }

        public void initialize(PlugInContext context) throws Exception {
                WorkbenchContext wbcontext = context.getWorkbenchContext();
                wbcontext.getWorkbench().getFrame().getEditionMapToolBar().addPlugIn(
                        this, btn, context);
        }

        public boolean isEnabled() {
                boolean isEnabled = false;
                MapEditorPlugIn mapEditor = null;
                if ((mapEditor = getPlugInContext().getMapEditor()) != null) {
                        MapContext map = (MapContext) mapEditor.getElement().getObject();
                        ILayer activeLayer = map.getActiveLayer();
                        if (activeLayer != null && activeLayer.getSelection().length > 0) {
                                isEnabled = true;
                        }
                }
                btn.setEnabled(isEnabled);
                return isEnabled;
        }
}
