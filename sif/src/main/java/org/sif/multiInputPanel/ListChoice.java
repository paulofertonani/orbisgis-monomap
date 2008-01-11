/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at french IRSTV institute and is able
 * to manipulate and create vectorial and raster spatial information. OrbisGIS
 * is distributed under GPL 3 license. It is produced  by the geomatic team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/>, CNRS FR 2488:
 *    Erwan BOCHER, scientific researcher,
 *    Thomas LEDUC, scientific researcher,
 *    Fernando GONZALEZ CORTES, computer engineer.
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OrbisGIS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult:
 *    <http://orbisgis.cerma.archi.fr/>
 *    <http://sourcesup.cru.fr/projects/orbisgis/>
 *    <http://listes.cru.fr/sympa/info/orbisgis-developers/>
 *    <http://listes.cru.fr/sympa/info/orbisgis-users/>
 *
 * or contact directly:
 *    erwan.bocher _at_ ec-nantes.fr
 *    fergonco _at_ gmail.com
 *    thomas.leduc _at_ cerma.archi.fr
 */
package org.sif.multiInputPanel;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.sif.SQLUIPanel;

public class ListChoice implements InputType {
	public static final String SEPARATOR = "#";
	private JList comp;

	public ListChoice(String... choices) {
		comp = new JList(choices);
	}

	public Component getComponent() {
		return new JScrollPane(comp);
	}

	public int getType() {
		return SQLUIPanel.STRING;
	}

	public String getValue() {
		final Object[] selectedValues = comp.getSelectedValues();
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < selectedValues.length; i++) {
			sb.append(selectedValues[i]);
			if (i + 1 != selectedValues.length) {
				sb.append(SEPARATOR);
			}
		}
		return sb.toString();
	}

	public void setValue(String value) {
		if (null != value) {
			comp.setListData(value.split(SEPARATOR));
		}
	}

	public boolean isPersistent() {
		return true;
	}
}