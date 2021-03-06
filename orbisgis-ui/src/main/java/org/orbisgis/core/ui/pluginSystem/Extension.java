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
package org.orbisgis.core.ui.pluginSystem;

import org.orbisgis.core.ui.pluginSystem.utils.StringUtil;
/**
 * From OpenJump project
 * 
 * The "entry point" into a JAR file containing PlugIns. The Workbench searches
 * the JARs in its lib/ext directory for Extensions. It instantiates each
 * Extension and calls its #configure method.
 */
public abstract class Extension implements Configuration {
	public String getName() {
		// Package is null if default package.
		return StringUtil.toFriendlyName(getClass().getName(), "Extension")
				+ (getClass().getPackage() == null ? "" : " ("
						+ getClass().getPackage().getName() + ")");
	}

	public String getVersion() {
		return "";
	}
}
