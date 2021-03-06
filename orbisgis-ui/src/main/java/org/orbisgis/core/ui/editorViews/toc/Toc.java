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
package org.orbisgis.core.ui.editorViews.toc;

import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragGestureEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.gdms.data.DataSource;
import org.gdms.data.DataSourceListener;
import org.gdms.data.edition.EditionEvent;
import org.gdms.data.edition.EditionListener;
import org.gdms.data.edition.MultipleEditionEvent;
import org.gdms.driver.DriverException;
import org.orbisgis.core.DataManager;
import org.orbisgis.core.Services;
import org.orbisgis.core.background.BackgroundJob;
import org.orbisgis.core.background.BackgroundManager;
import org.orbisgis.core.edition.EditableElement;
import org.orbisgis.core.layerModel.ILayer;
import org.orbisgis.core.layerModel.LayerCollectionEvent;
import org.orbisgis.core.layerModel.LayerException;
import org.orbisgis.core.layerModel.LayerListener;
import org.orbisgis.core.layerModel.LayerListenerEvent;
import org.orbisgis.core.layerModel.MapContext;
import org.orbisgis.core.layerModel.MapContextListener;
import org.orbisgis.core.layerModel.SelectionEvent;
import org.orbisgis.core.renderer.legend.Legend;
import org.orbisgis.core.ui.components.resourceTree.MyTreeUI;
import org.orbisgis.core.ui.components.resourceTree.ResourceTree;
import org.orbisgis.core.ui.editor.IEditor;
import org.orbisgis.core.ui.editorViews.toc.TocTreeModel.LegendNode;
import org.orbisgis.core.ui.pluginSystem.workbench.WorkbenchFrame;
import org.orbisgis.core.ui.plugins.views.editor.EditorManager;
import org.orbisgis.core.ui.plugins.views.geocatalog.TransferableSource;
import org.orbisgis.core.ui.plugins.views.mapEditor.MapEditorPlugIn;
import org.orbisgis.progress.ProgressMonitor;
import org.orbisgis.utils.I18N;

public class Toc extends ResourceTree implements WorkbenchFrame {

        private MyLayerListener ll;
        private TocRenderer tocRenderer;
        private TocTreeModel treeModel;
        private boolean ignoreSelection = false;
        private MyMapContextListener myMapContextListener;
        private MapContext mapContext = null;

        public MapContext getMapContext() {
                return mapContext;
        }
        private EditableElement element = null;
        private MapEditorPlugIn mapEditor;
        private org.orbisgis.core.ui.pluginSystem.menu.MenuTree menuTree;

        public org.orbisgis.core.ui.pluginSystem.menu.MenuTree getMenuTreePopup() {
                return menuTree;
        }

        public Toc() {

                menuTree = new org.orbisgis.core.ui.pluginSystem.menu.MenuTree();
                this.ll = new MyLayerListener();

                tocRenderer = new TocRenderer(this);
                DataManager dataManager = (DataManager) Services.getService(DataManager.class);
                treeModel = new TocTreeModel(dataManager.createLayerCollection("root"), //$NON-NLS-1$
                        getTree());
                this.setModel(treeModel);
                this.setTreeCellRenderer(tocRenderer);
                this.setTreeCellEditor(new TocEditor(tree));

                tree.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                                final int x = e.getX();
                                final int y = e.getY();
                                final int mouseButton = e.getButton();
                                TreePath path = tree.getPathForLocation(x, y);
                                Rectangle layerNodeLocation = Toc.this.tree.getPathBounds(path);

                                if (path != null) {
                                        if (path.getLastPathComponent() instanceof ILayer) {
                                                ILayer layer = (ILayer) path.getLastPathComponent();
                                                Rectangle checkBoxBounds = tocRenderer.getCheckBoxBounds();
                                                checkBoxBounds.translate(
                                                        (int) layerNodeLocation.getX(),
                                                        (int) layerNodeLocation.getY());
                                                if ((checkBoxBounds.contains(e.getPoint()))
                                                        && (MouseEvent.BUTTON1 == mouseButton)
                                                        && (1 == e.getClickCount())) {
                                                        // mouse click inside checkbox

                                                        try {
                                                                layer.setVisible(!layer.isVisible());

                                                                tree.repaint();
                                                        } catch (LayerException e1) {
                                                        }
                                                }
                                        } else if (path.getLastPathComponent() instanceof LegendNode) {

                                                LegendNode legendNode = (LegendNode) path.getLastPathComponent();
                                                Rectangle checkBoxBounds = tocRenderer.getCheckBoxBounds();
                                                checkBoxBounds.translate(
                                                        (int) layerNodeLocation.getX(),
                                                        (int) layerNodeLocation.getY());
                                                if ((checkBoxBounds.contains(e.getPoint()))
                                                        && (MouseEvent.BUTTON1 == mouseButton)
                                                        && (1 == e.getClickCount())) {
                                                        try {
                                                                Legend legend = legendNode.getLayer().getRenderingLegend()[legendNode.getLegendIndex()];
                                                                if (!legend.isVisible()) {
                                                                        legend.setVisible(true);
                                                                } else {
                                                                        legend.setVisible(false);
                                                                }
                                                                ILayer layer = legendNode.getLayer();
                                                                if (layer.isVisible()) {
                                                                        layer.setVisible(true);
                                                                }
                                                                tree.repaint();
                                                        } catch (DriverException e1) {
                                                                e1.printStackTrace();
                                                        } catch (LayerException e1) {
                                                                e1.printStackTrace();
                                                        }

                                                }
                                        }
                                }
                        }
                });

                myMapContextListener = new MyMapContextListener();

                this.getTree().getSelectionModel().addTreeSelectionListener(
                        new TreeSelectionListener() {

                                @Override
                                public void valueChanged(TreeSelectionEvent e) {
                                        if (!ignoreSelection) {
                                                TreePath[] selectedPaths = Toc.this.getSelection();
                                                ArrayList<ILayer> layers = getSelectedLayers(selectedPaths);

                                                ignoreSelection = true;
                                                mapContext.setSelectedLayers(layers.toArray(new ILayer[layers.size()]));
                                                ignoreSelection = false;
                                        }
                                }
                        });


        }

        private ArrayList<ILayer> getSelectedLayers(TreePath[] selectedPaths) {
                ArrayList<ILayer> layers = new ArrayList<ILayer>();
                for (int i = 0; i < selectedPaths.length; i++) {
                        Object lastPathComponent = selectedPaths[i].getLastPathComponent();
                        if (lastPathComponent instanceof ILayer) {
                                layers.add((ILayer) lastPathComponent);
                        }
                }
                return layers;
        }

        @Override
        public JPopupMenu getPopup() {
                JPopupMenu newPopup = new JPopupMenu();
                JComponent[] menus = menuTree.getJMenus();
                for (JComponent menu : menus) {
                        newPopup.add(menu);
                }
                return newPopup;
        }

        @Override
        protected boolean isDroppable(TreePath path) {
                return path.getLastPathComponent() instanceof ILayer;
        }

        @Override
        public boolean doDrop(Transferable trans, Object node) {

                ILayer dropNode;

                if (node instanceof TocTreeModel.LegendNode) {
                        dropNode = ((TocTreeModel.LegendNode) node).getLayer();
                } else {
                        dropNode = (ILayer) node;
                }
                // By default drop on rootNode
                if (dropNode == null) {
                        ILayer rootNode = (ILayer) treeModel.getRoot();
                        dropNode = rootNode;
                }

                try {

                        if (trans.isDataFlavorSupported(TransferableLayer.getLayerFlavor())) {
                                ILayer[] draggedLayers = (ILayer[]) trans.getTransferData(TransferableLayer.getLayerFlavor());
                                if (dropNode.acceptsChilds()) {
                                        for (ILayer layer : draggedLayers) {
                                                try {
                                                        layer.moveTo(dropNode);
                                                } catch (LayerException e) {
                                                        Services.getErrorManager().error(
                                                                I18N.getString("orbisgis.org.orbisgis.ui.toc.cannotMoveLayer"), e); //$NON-NLS-1$
                                                }
                                        }
                                } else {
                                        ILayer parent = dropNode.getParent();
                                        if (parent != null) {
                                                for (ILayer layer : draggedLayers) {
                                                        if (layer.getParent() == dropNode.getParent()) {
                                                                int index = parent.getIndex(dropNode);
                                                                try {
                                                                        layer.moveTo(parent, index);
                                                                } catch (LayerException e) {
                                                                        Services.getErrorManager().error(
                                                                                I18N.getString("orbisgis.org.orbisgis.ui.toc.cannotMoveLayer") //$NON-NLS-1$
                                                                                + layer.getName());
                                                                }
                                                        }
                                                }
                                        }
                                }
                        } else if (trans.isDataFlavorSupported(TransferableSource.getResourceFlavor())) {
                                final String[] draggedResources = (String[]) trans.getTransferData(TransferableSource.getResourceFlavor());
                                BackgroundManager bm = (BackgroundManager) Services.getService(BackgroundManager.class);
                                bm.backgroundOperation(new MoveProcess(draggedResources,
                                        dropNode));
                        } else {
                                return false;
                        }
                } catch (UnsupportedFlavorException e1) {
                        throw new RuntimeException(I18N.getString("orbisgis.org.orbisgis.ui.toc.bug"), e1); //$NON-NLS-1$
                } catch (IOException e1) {
                        throw new RuntimeException(I18N.getString("orbisgis.org.orbisgis.ui.toc.bug"), e1); //$NON-NLS-1$
                }

                return true;
        }

        @Override
        public Transferable getDragData(DragGestureEvent dge) {
                TreePath[] resources = getSelection();
                ArrayList<ILayer> layers = getSelectedLayers(resources);
                if (layers.isEmpty()) {
                        return null;
                } else {
                        return new TransferableLayer(element, layers.toArray(new ILayer[layers.size()]));
                }
        }

        private final class MyMapContextListener implements MapContextListener {

                @Override
                public void layerSelectionChanged(MapContext mapContext) {
                        setTocSelection(mapContext);
                }

                @Override
                public void activeLayerChanged(ILayer previousActiveLayer,
                        MapContext mapContext) {
                        treeModel.refresh();
                }
        }

        private class MyLayerListener implements LayerListener, EditionListener,
                DataSourceListener {

                @Override
                public void layerAdded(final LayerCollectionEvent e) {
                        for (final ILayer layer : e.getAffected()) {
                                addLayerListenerRecursively(layer, ll);
                        }
                        treeModel.refresh();
                }

                @Override
                public void layerMoved(LayerCollectionEvent e) {
                        treeModel.refresh();
                }

                @Override
                public boolean layerRemoving(LayerCollectionEvent e) {
                        // Close editors
                        for (final ILayer layer : e.getAffected()) {
                                ILayer[] layers = new ILayer[]{layer};
                                if (layer.acceptsChilds()) {
                                        layers = layer.getLayersRecursively();
                                }
                                for (ILayer lyr : layers) {
                                        EditorManager em = Services.getService(EditorManager.class);
                                        IEditor[] editors = em.getEditor(new EditableLayer(element,
                                                lyr));
                                        for (IEditor editor : editors) {
                                                if (!em.closeEditor(editor)) {
                                                        return false;
                                                }
                                        }
                                }
                        }
                        return true;
                }

                @Override
                public void layerRemoved(final LayerCollectionEvent e) {
                        for (final ILayer layer : e.getAffected()) {
                                removeLayerListenerRecursively(layer, ll);
                        }
                        treeModel.refresh();
                }

                @Override
                public void nameChanged(LayerListenerEvent e) {
                }

                @Override
                public void styleChanged(LayerListenerEvent e) {
                        treeModel.refresh();
                }

                @Override
                public void visibilityChanged(LayerListenerEvent e) {
                        treeModel.refresh();
                }

                @Override
                public void selectionChanged(SelectionEvent e) {
                        treeModel.refresh();
                }

                @Override
                public void multipleModification(MultipleEditionEvent e) {
                        treeModel.refresh();
                }

                @Override
                public void singleModification(EditionEvent e) {
                        treeModel.refresh();
                }

                @Override
                public void cancel(DataSource ds) {
                }

                @Override
                public void commit(DataSource ds) {
                        treeModel.refresh();
                }

                @Override
                public void open(DataSource ds) {
                        treeModel.refresh();
                }
        }

        public void delete() {
                if (mapContext != null) {
                        mapContext.removeMapContextListener(myMapContextListener);
                }
        }

        private class MoveProcess implements BackgroundJob {

                private ILayer dropNode;
                private String[] draggedResources;

                public MoveProcess(String[] draggedResources, ILayer dropNode) {
                        this.draggedResources = draggedResources;
                        this.dropNode = dropNode;
                }

                @Override
                public void run(ProgressMonitor pm) {
                        int index;
                        if (!dropNode.acceptsChilds()) {
                                ILayer parent = dropNode.getParent();
                                if (parent.acceptsChilds()) {
                                        index = parent.getIndex(dropNode);
                                        dropNode = parent;
                                } else {
                                        Services.getErrorManager().error(
                                                I18N.getString("orbisgis.org.orbisgis.ui.toc.cannotCreateLayerOn") + dropNode.getName()); //$NON-NLS-1$
                                        return;
                                }
                        } else {
                                index = dropNode.getLayerCount();
                        }
                        DataManager dataManager = (DataManager) Services.getService(DataManager.class);
                        for (int i = 0; i < draggedResources.length; i++) {
                                String sourceName = draggedResources[i];
                                if (pm.isCancelled()) {
                                        break;
                                } else {
                                        pm.progressTo(100 * i / draggedResources.length);
                                        try {
                                                dropNode.insertLayer(dataManager.createLayer(sourceName), index);
                                        } catch (LayerException e) {
                                                throw new RuntimeException(I18N.getString("orbisgis.org.orbisgis.ui.toc.cannot") //$NON-NLS-1$
                                                        + I18N.getString("orbisgis.org.orbisgis.ui.toc.addLayerToDestination"), e); //$NON-NLS-1$
                                        }
                                }
                        }
                }

                @Override
                public String getTaskName() {
                        return I18N.getString("orbisgis.org.orbisgis.ui.toc.importingResources"); //$NON-NLS-1$
                }
        }

        private void addLayerListenerRecursively(ILayer rootLayer,
                MyLayerListener refreshLayerListener) {
                rootLayer.addLayerListener(refreshLayerListener);
                DataSource dataSource = rootLayer.getDataSource();
                if (dataSource != null) {
                        if (dataSource.isEditable()) {
                                dataSource.addEditionListener(refreshLayerListener);
                        }
                        dataSource.addDataSourceListener(refreshLayerListener);
                }
                for (int i = 0; i < rootLayer.getLayerCount(); i++) {
                        addLayerListenerRecursively(rootLayer.getLayer(i),
                                refreshLayerListener);
                }
        }

        private void removeLayerListenerRecursively(ILayer rootLayer,
                MyLayerListener refreshLayerListener) {
                rootLayer.removeLayerListener(refreshLayerListener);
                DataSource dataSource = rootLayer.getDataSource();
                if (dataSource != null) {
                        if (dataSource.isEditable()) {
                                dataSource.removeEditionListener(refreshLayerListener);
                        }
                        dataSource.removeDataSourceListener(refreshLayerListener);
                }
                for (int i = 0; i < rootLayer.getLayerCount(); i++) {
                        removeLayerListenerRecursively(rootLayer.getLayer(i),
                                refreshLayerListener);
                }
        }

        private void setTocSelection(MapContext mapContext) {
                ILayer[] selected = mapContext.getSelectedLayers();
                TreePath[] selectedPaths = new TreePath[selected.length];
                for (int i = 0; i < selectedPaths.length; i++) {
                        selectedPaths[i] = new TreePath(selected[i].getLayerPath());
                }

                ignoreSelection = true;
                this.setSelection(selectedPaths);
                ignoreSelection = false;
        }

        public void setMapContext(EditableElement element) {

                // Remove the listeners
                if (this.mapContext != null) {
                        removeLayerListenerRecursively(this.mapContext.getLayerModel(), ll);
                        this.mapContext.removeMapContextListener(myMapContextListener);
                }

                if (element != null) {
                        this.mapContext = ((MapContext) element.getObject());
                        this.element = element;
                        // Add the listeners to the new MapContext
                        this.mapContext.addMapContextListener(myMapContextListener);
                        final ILayer root = this.mapContext.getLayerModel();
                        addLayerListenerRecursively(root, ll);

                        treeModel = new TocTreeModel(root, tree);

                        // Set model clears selection
                        ignoreSelection = true;
                        Toc.this.setModel(treeModel);
                        ignoreSelection = false;
                        setTocSelection(Toc.this.mapContext);
                        Toc.this.repaint();

                } else {
                        // Remove the references to the mapContext
                        DataManager dataManager = (DataManager) Services.getService(DataManager.class);
                        treeModel = new TocTreeModel(dataManager.createLayerCollection("root"), getTree()); //$NON-NLS-1$
                        ignoreSelection = true;
                        this.setModel(treeModel);
                        ignoreSelection = false;
                        this.mapContext = null;
                        this.element = null;

                        // Patch to remove any reference to the previous model
                        myTreeUI = new MyTreeUI();
                        ((MyTreeUI) tree.getUI()).dispose();
                        tree.setUI(myTreeUI);
                }

        }

        boolean isActive(ILayer layer) {
                if (mapContext != null) {
                        return layer == mapContext.getActiveLayer();
                } else {
                        return false;
                }
        }

        public void setMapContext(EditableElement element, MapEditorPlugIn mapEditor) {
                this.mapEditor = mapEditor;
                setMapContext(element);
        }
}
