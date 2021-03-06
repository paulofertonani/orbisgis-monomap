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
package org.orbisgis.core.renderer.legend;

import java.util.ArrayList;

import org.gdms.data.DataSource;
import org.orbisgis.core.renderer.legend.carto.persistence.LegendType;

public abstract class AbstractLegend implements Legend {

	private String name;

	private int minScale = Integer.MIN_VALUE;

	private int maxScale = Integer.MAX_VALUE;

	private ArrayList<LegendListener> listeners = new ArrayList<LegendListener>();

	private boolean visible = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addLegendListener(LegendListener listener) {
		listeners.add(listener);
	}

	public void removeLegendListener(LegendListener listener) {
		listeners.remove(listener);
	}

	protected void fireLegendInvalid() {
		for (LegendListener listener : listeners) {
			listener.invalidateLegend(this);
		}
	}

	public void preprocess(DataSource sds)
			throws RenderException {
	}

	public int getMinScale() {
		return minScale;
	}

	public void setMinScale(int minScale) {
		this.minScale = minScale;
	}

	public int getMaxScale() {
		return maxScale;
	}

	public void setMaxScale(int maxScale) {
		this.maxScale = maxScale;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	protected void save(LegendType legend) {
		legend.setName(getName());

		if (minScale != Integer.MIN_VALUE) {
			legend.setMinScale(minScale);
		}

		if (maxScale != Integer.MAX_VALUE) {
			legend.setMaxScale(maxScale);
		}

		legend.setVisible(visible);
	}

	protected void load(LegendType legend) {
		this.setName(legend.getName());

		if (legend.getMinScale() != null) {
			minScale = legend.getMinScale();
		}

		if (legend.getMaxScale() != null) {
			maxScale = legend.getMaxScale();
		}

		visible = legend.isVisible();

	}
}
