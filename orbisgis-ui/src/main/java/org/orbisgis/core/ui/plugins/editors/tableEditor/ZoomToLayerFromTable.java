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
package org.orbisgis.core.ui.plugins.editors.tableEditor;

import org.gdms.driver.DriverException;
import org.orbisgis.core.Services;
import org.orbisgis.core.background.BackgroundJob;
import org.orbisgis.core.background.BackgroundManager;
import org.orbisgis.core.ui.editor.IEditor;
import org.orbisgis.core.ui.editors.table.TableEditableElement;
import org.orbisgis.core.ui.pluginSystem.AbstractPlugIn;
import org.orbisgis.core.ui.pluginSystem.PlugInContext;
import org.orbisgis.core.ui.pluginSystem.message.ErrorMessages;
import org.orbisgis.core.ui.pluginSystem.workbench.Names;
import org.orbisgis.core.ui.pluginSystem.workbench.WorkbenchContext;
import org.orbisgis.core.ui.pluginSystem.workbench.WorkbenchFrame;
import org.orbisgis.core.ui.plugins.views.editor.EditorManager;
import org.orbisgis.core.ui.plugins.views.mapEditor.MapEditorPlugIn;
import org.orbisgis.core.ui.plugins.views.tableEditor.TableEditorPlugIn;
import org.orbisgis.core.ui.preferences.lookandfeel.OrbisGISIcon;
import org.orbisgis.progress.ProgressMonitor;
import org.orbisgis.utils.I18N;

import com.vividsolutions.jts.geom.Envelope;
import org.gdms.data.DataSource;

public class ZoomToLayerFromTable extends AbstractPlugIn {

	public boolean execute(PlugInContext context) throws Exception {
		IEditor editor = context.getActiveEditor();
		final TableEditableElement element = (TableEditableElement) editor
				.getElement();
		BackgroundManager bm = Services.getService(BackgroundManager.class);
		bm.backgroundOperation(new BackgroundJob() {

			@Override
			public void run(ProgressMonitor pm) {
				try {

					DataSource sds = element.getDataSource();

					Envelope rect = sds.getFullExtent();

					EditorManager em = Services
							.getService(EditorManager.class);
					IEditor[] editors = em.getEditors(Names.EDITOR_MAP_ID,
							element.getMapContext());
					for (IEditor mapEditorPlugIn : editors) {
						((MapEditorPlugIn) mapEditorPlugIn).getMapTransform()
								.setExtent(rect);
					}

				} catch (DriverException e) {
					ErrorMessages.error(ErrorMessages.CannotComputeEnvelope, e);
				}
			}

			@Override
			public String getTaskName() {
				return I18N
						.getString("orbisgis.org.orbisgis.core.ui.plugins.editors.tableEditor.zoomToLayer.extent");
			}
		});
		return true;
	}

	public void initialize(PlugInContext context) throws Exception {
		WorkbenchContext wbContext = context.getWorkbenchContext();
		WorkbenchFrame frame = wbContext.getWorkbench()
				.getFrame().getTableEditor();
		context.getFeatureInstaller().addPopupMenuItem(frame, this,
				new String[] { Names.POPUP_TABLE_ZOOMTOLAYER_PATH1 },
				Names.POPUP_TABLE_ZOOMTOLAYER_GROUP, false,
				OrbisGISIcon.ZOOM, wbContext);
	}

	public boolean isEnabled() {
		boolean isEnabled = false;
		TableEditorPlugIn tableEditor = null;
		if ((tableEditor = getPlugInContext().getTableEditor()) != null
				&& getSelectedColumn() == -1) {

			final TableEditableElement element = (TableEditableElement) tableEditor
					.getElement();
			isEnabled = element.getMapContext() != null;
		}
		return isEnabled;
	}
}
