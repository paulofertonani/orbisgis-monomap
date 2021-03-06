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
package org.orbisgis.core.ui.components.job;

import javax.swing.SwingUtilities;

import org.orbisgis.core.Services;
import org.orbisgis.core.background.BackgroundListener;
import org.orbisgis.core.background.BackgroundManager;
import org.orbisgis.core.background.Job;
import org.orbisgis.core.ui.windows.mainFrame.OrbisGISFrame;

public class JobPopup {

	public JobWindow panel;

	public JobWindow getPanel() {
		return panel;
	}

	public JobPopup(final OrbisGISFrame orbisGISFrame) {
		panel = new JobWindow(orbisGISFrame);
	}

	public void initialize() {
		BackgroundManager bm = (BackgroundManager) Services
				.getService(BackgroundManager.class);

		bm.addBackgroundListener(new BackgroundListener() {

			public void jobAdded(final Job job) {
				if (SwingUtilities.isEventDispatchThread()) {
					panel.show();
					panel.addJob(job);
				} else {
					SwingUtilities.invokeLater(new Runnable() {

						public void run() {
							panel.show();
							panel.addJob(job);
						}

					});
				}
			}

			public void jobRemoved(final Job job) {
				if (SwingUtilities.isEventDispatchThread()) {
					panel.show();
					panel.removeJob(job);
				} else {
					SwingUtilities.invokeLater(new Runnable() {

						public void run() {
							panel.show();
							panel.removeJob(job);
						}

					});
				}
			}

			public void jobReplaced(final Job job) {
				if (SwingUtilities.isEventDispatchThread()) {
					panel.show();
					panel.replaceJob(job);
				} else {
					SwingUtilities.invokeLater(new Runnable() {

						public void run() {
							panel.show();
							panel.replaceJob(job);
						}

					});
				}
			}

			
		});
	}
}
