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
package org.orbisgis.core;

import java.io.File;

import org.orbisgis.utils.I18N;

public class OrbisGISApplicationInfo implements ApplicationInfo {

	@Override
	public String getLogFile() {
		return new File(System.getProperty("user.home")
				+ "/.OrbisGIS/orbisgis.log").getAbsolutePath();
	}

	@Override
	public File getUserHomeFolder() {
		return new File(System.getProperty("user.home"));
	}

	@Override
	public File getHomeFolder() {
		return new File(System.getProperty("user.home") + "/.OrbisGIS");
	}

	@Override
	public String getName() {
		return "OrbisGIS";
	}

	@Override
	public String getOrganization() {
		return "IRSTV CNRS-FR-2488";
	}

	@Override
	public String getVersionName() {
		return I18N.getString("orbisgis.org.orbisgis.versionName");
	}

	@Override
	public String getVersionNumber() {
		return I18N.getString("orbisgis.org.orbisgis.version");
	}
}
