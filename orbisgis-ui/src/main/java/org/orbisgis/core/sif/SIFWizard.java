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
package org.orbisgis.core.sif;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.orbisgis.utils.I18N;

public class SIFWizard extends AbstractOutsideFrame {

	private JPanel wizardButtons;
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnFinish;
	private JButton btnCancel;
	private JPanel mainPanel;

	private boolean test;

	private SimplePanel[] panels;
	private int index = 0;

	private CardLayout layout = new CardLayout();

	public SIFWizard(Window owner) {
		super(owner);
		init();
	}

	private void init() {
		this.setLayout(new BorderLayout());

		this.add(getWizardButtons(), BorderLayout.SOUTH);

		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentShown(ComponentEvent e) {
				if (test) {
					exit(true);
				}
			}

		});

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private JPanel getWizardButtons() {
		if (wizardButtons == null) {
			wizardButtons = new JPanel();
			wizardButtons.add(getBtnPrevious());
			wizardButtons.add(getBtnNext());
			wizardButtons.add(getBtnFinish());
			wizardButtons.add(getBtnCancel());
		}

		return wizardButtons;
	}

	private void buildMainPanel(SimplePanel[] panels) {
		mainPanel = new JPanel();
		mainPanel.setLayout(layout);

		for (int i = 0; i < panels.length; i++) {
			mainPanel.add(panels[i], Integer.toString(i));
		}
	}

	public JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton(I18N.getString("orbisgis.org.core.previous"));
			btnPrevious.setBorderPainted(false);
			btnPrevious.setEnabled(false);
			btnPrevious.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					index--;
					layout.previous(mainPanel);
				}

			});
		}

		return btnPrevious;
	}

	public JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton(I18N.getString("orbisgis.org.core.next"));
			btnNext.setBorderPainted(false);
			btnNext.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (getPanel().postProcess()) {
						index++;
						layout.next(mainPanel);
						getPanel().initialize();
						setDefaultButton();
					} else {
						return;
					}
				}

			});
		}

		return btnNext;
	}

	private void setDefaultButton() {
		if (index == panels.length - 1) {
			getRootPane().setDefaultButton(btnFinish);
		} else {
			getRootPane().setDefaultButton(btnNext);
		}
	}

	public JButton getBtnFinish() {
		if (btnFinish == null) {
			btnFinish = new JButton(I18N.getString("orbisgis.org.core.finish"));
			btnFinish.setBorderPainted(false);
			btnFinish.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					exit(true);
				}

			});
		}

		return btnFinish;
	}

	public JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton(I18N.getString("orbisgis.org.core.cancel"));
			btnCancel.setBorderPainted(false);
			btnCancel.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					exit(false);
				}

			});
		}

		return btnCancel;
	}

	public void setComponent(SimplePanel[] panels,
			HashMap<String, String> inputs) {
		this.panels = panels;
		this.index = 0;
		panels[0].validateInput();
		buildMainPanel(panels);
		this.add(mainPanel, BorderLayout.CENTER);
		listen(this);
		loadInput(inputs);
		getPanel().initialize();
		this.setIconImage(getPanel().getIconImage());
		setDefaultButton();
	}

	public void canContinue() {
		enableByPosition();
		visualizeByPosition();

		btnNext.setEnabled(true);
		btnFinish.setEnabled(true);
	}

	private void visualizeByPosition() {
		if (panels != null) {
			if (index == panels.length - 1) {
				btnFinish.setVisible(true);
				btnNext.setVisible(false);
			} else {
				btnFinish.setVisible(false);
				btnNext.setVisible(true);
			}
		}
	}

	private void enableByPosition() {
		if (panels != null) {
			if (index == 0) {
				btnPrevious.setEnabled(false);
			} else {
				btnPrevious.setEnabled(true);
			}

			if (index < panels.length - 1) {
				btnNext.setEnabled(true);
				btnFinish.setEnabled(false);
			} else {
				btnNext.setEnabled(false);
				btnFinish.setEnabled(true);
			}
		}
	}

	public void cannotContinue() {
		enableByPosition();
		visualizeByPosition();

		btnNext.setEnabled(false);
		btnFinish.setEnabled(false);
	}

	@Override
	protected SimplePanel getPanel() {
		return panels[index];
	}

	protected void loadInput(HashMap<String, String> inputs) {
		test = true;
		for (SimplePanel panel : panels) {
			if (!panel.loadInput(inputs)) {
				test = false;
			}
		}
	}

	@Override
	protected void saveInput() {
		for (SimplePanel panel : panels) {
			panel.saveInput();
		}
	}

}
