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
package org.orbisgis.core.ui.editors.map.actions.export;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.text.NumberFormat;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.orbisgis.core.Services;
import org.orbisgis.core.map.export.MapExportManager;
import org.orbisgis.core.map.export.Scale;
import org.orbisgis.core.sif.CRFlowLayout;
import org.orbisgis.core.sif.CarriageReturn;
import org.orbisgis.core.sif.UIFactory;
import org.orbisgis.core.sif.UIPanel;
import org.orbisgis.core.ui.components.patterns.PatternChangeListener;
import org.orbisgis.core.ui.components.patterns.PatternConfigurator;
import org.orbisgis.core.ui.components.preview.JNumericSpinner;
import org.orbisgis.core.ui.components.preview.SizeSelector;
import org.orbisgis.core.ui.components.preview.UnitSelector;
import org.orbisgis.utils.I18N;

public class ScaleEditor extends JPanel implements UIPanel {

	private JComboBox cmbScaleType;
	private JNumericSpinner spnPartCount;
	private SizeSelector spnPartSize;
	private SizeSelector spnHeight;
	private ScalePreview scalePreview;
	private Scale scale;
	private double scaleDenominator;
	private PatternConfigurator labelPattern;
	private PatternConfigurator markPattern;

	private boolean syncing = false;

	public ScaleEditor(double scaleDenominator) {
		this.scaleDenominator = scaleDenominator;

		this.setLayout(new BorderLayout());
		JPanel controlPanel = new JPanel();
		MapExportManager export = Services.getService(MapExportManager.class);
		Scale[] scales = export.getScales();
		controlPanel.setLayout(new CRFlowLayout(CRFlowLayout.LEFT));

		cmbScaleType = new JComboBox(scales);
		cmbScaleType.setRenderer(new BasicComboBoxRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel ret = (JLabel) super.getListCellRendererComponent(list,
						value, index, isSelected, cellHasFocus);
				ret.setText(((Scale) value).getScaleTypeName());
				return ret;
			}
		});
		cmbScaleType.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				changeScale();
			}
		});
		controlPanel.add(new JLabel(I18N.getString("orbisgis.org.orbisgis.ui.scaleEditor.selectScaleType"))); //$NON-NLS-1$
		controlPanel.add(cmbScaleType);
		controlPanel.add(new CarriageReturn());

		controlPanel.add(new JLabel(I18N.getString("orbisgis.org.orbisgis.ui.scaleEditor.partCount"))); //$NON-NLS-1$
		spnPartCount = new JNumericSpinner(4);
		spnPartCount.setInc(1);
		spnPartCount.setMin(0);
		spnPartCount.setNumberFormat(NumberFormat.getIntegerInstance());
		spnPartCount.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				resizePattern(labelPattern);
				resizePattern(markPattern);
				safeSyncScale();
			}
		});
		controlPanel.add(spnPartCount);
		controlPanel.add(new CarriageReturn());

		ChangeListener synListener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				safeSyncScale();
			}
		};
		controlPanel.add(new JLabel(I18N.getString("orbisgis.org.orbisgis.ui.scaleEditor.partSize"))); //$NON-NLS-1$
		UnitSelector unitSelector = new UnitSelector();
		spnPartSize = new SizeSelector(5, unitSelector);
		spnPartSize.addChangeListener(synListener);
		spnPartSize.setMin(0);
		controlPanel.add(spnPartSize);
		controlPanel.add(unitSelector);
		controlPanel.add(new CarriageReturn());

		controlPanel.add(new JLabel(I18N.getString("orbisgis.org.orbisgis.ui.scaleEditor.height"))); //$NON-NLS-1$
		spnHeight = new SizeSelector(5, unitSelector);
		spnHeight.addChangeListener(synListener);
		spnHeight.setMin(0);
		controlPanel.add(spnHeight);
		controlPanel.add(new CarriageReturn());

		controlPanel.add(new JLabel(I18N.getString("orbisgis.org.orbisgis.ui.scaleEditor.labeledPart"))); //$NON-NLS-1$
		PatternChangeListener patternChangeListener = new PatternChangeListener() {

			@Override
			public void patternChanged(int row, int column) {
				safeSyncScale();
			}
		};
		labelPattern = new PatternConfigurator();
		Dimension patternSize = new Dimension(100, 25);
		labelPattern.setPreferredSize(patternSize);
		labelPattern.addChangeListener(patternChangeListener);
		controlPanel.add(labelPattern);
		controlPanel.add(new CarriageReturn());

		controlPanel.add(new JLabel(I18N.getString("orbisgis.org.orbisgis.ui.scaleEditor.remarkedPart"))); //$NON-NLS-1$
		markPattern = new PatternConfigurator();
		markPattern.setPreferredSize(patternSize);
		markPattern.addChangeListener(patternChangeListener);
		controlPanel.add(markPattern);

		scalePreview = new ScalePreview();
		scalePreview.setPreferredSize(new Dimension(300, 50));
		this.add(scalePreview, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);

		changeScale();
	}

	private void resizePattern(PatternConfigurator patternComponent) {
		try {
			int dim = ((int) spnPartCount.getValue()) + 1;
			if (dim > 0) {
				patternComponent.setDimensions(1, dim);
			}
		} catch (NumberFormatException e) {
			patternComponent.setDimensions(1, 1);
		}

		this.revalidate();
		this.validate();
		this.doLayout();
	}

	private void changeScale() {
		scale = (Scale) cmbScaleType.getSelectedItem();
		if (scale != null) {
			scalePreview.setModel(scale);
			scale.setScaleDenominator(scaleDenominator);
			// Must resize before syncing or we'll lose the patterns
			resizePattern(markPattern);
			resizePattern(labelPattern);
			syncControls();
		}
	}

	private void syncControls() {
		if (!syncing) {
			syncing = true;
			try {
				spnHeight.setMeasure(scale.getHeight());
				spnPartSize.setMeasure(scale.getPartWidth());
				spnPartCount.setValue(scale.getPartCount());
				labelPattern.setRowPattern(0, scale.getLabeledParts());
				markPattern.setRowPattern(0, scale.getRemarkedParts());
			} finally {
				syncing = false;
			}
		}
	}

	private void safeSyncScale() {
		try {
			syncScale();
		} catch (RuntimeException e) {
			// ignore
		}
	}

	private void syncScale() {
		if (!syncing) {
			syncing = true;
			try {
				scale.setHeight(spnHeight.getMeasure());
				scale.setPartCount((int) spnPartCount.getValue());
				scale.setPartWidth(spnPartSize.getMeasure());
				scale.setPartsWithText(labelPattern.getRowPattern(0));
				scale.setRemarkedParts(markPattern.getRowPattern(0));
				scalePreview.repaint();
			} finally {
				syncing = false;
			}
		}
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public String getTitle() {
		return I18N.getString("orbisgis.org.orbisgis.ui.scaleEditor.chooseScale"); //$NON-NLS-1$
	}

	@Override
	public String validateInput() {
		try {
			syncScale();
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}

		return null;
	}

	public URL getIconURL() {
		return UIFactory.getDefaultIcon();
	}

	@Override
	public String getInfoText() {
		return I18N.getString("orbisgis.org.orbisgis.ui.scaleEditor.chooseScaleToUse"); //$NON-NLS-1$
	}

	@Override
	public String initialize() {
		return null;
	}

	@Override
	public String postProcess() {
		return null;
	}

	public Scale getScale() {
		return scale;
	}

}
