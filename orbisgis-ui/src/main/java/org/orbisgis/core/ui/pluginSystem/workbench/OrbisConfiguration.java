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
package org.orbisgis.core.ui.pluginSystem.workbench;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import org.gdms.driver.Driver;

import org.gdms.driver.FileDriver;
import org.gdms.driver.driverManager.DriverManager;
import org.gdms.source.SourceManager;
import org.orbisgis.core.DataManager;
import org.orbisgis.core.Services;
import org.orbisgis.core.ui.editors.map.tool.Automaton;
import org.orbisgis.core.ui.editors.map.tools.AutoCompletePolygonTool;
import org.orbisgis.core.ui.editors.map.tools.CompassTool;
import org.orbisgis.core.ui.editors.map.tools.CutPolygonTool;
import org.orbisgis.core.ui.editors.map.tools.EditionSelectionTool;
import org.orbisgis.core.ui.editors.map.tools.FencePolygonTool;
import org.orbisgis.core.ui.editors.map.tools.InfoTool;
import org.orbisgis.core.ui.editors.map.tools.LineTool;
import org.orbisgis.core.ui.editors.map.tools.MesureLineTool;
import org.orbisgis.core.ui.editors.map.tools.MesurePolygonTool;
import org.orbisgis.core.ui.editors.map.tools.MultilineTool;
import org.orbisgis.core.ui.editors.map.tools.MultipointTool;
import org.orbisgis.core.ui.editors.map.tools.MultipolygonTool;
import org.orbisgis.core.ui.editors.map.tools.PanTool;
import org.orbisgis.core.ui.editors.map.tools.PickCoordinatesPointTool;
import org.orbisgis.core.ui.editors.map.tools.PointTool;
import org.orbisgis.core.ui.editors.map.tools.PolygonTool;
import org.orbisgis.core.ui.editors.map.tools.SelectionTool;
import org.orbisgis.core.ui.editors.map.tools.SplitLineStringTool;
import org.orbisgis.core.ui.editors.map.tools.SplitPolygonTool;
import org.orbisgis.core.ui.editors.map.tools.VertexAditionTool;
import org.orbisgis.core.ui.editors.map.tools.VertexDeletionTool;
import org.orbisgis.core.ui.editors.map.tools.ZoomInTool;
import org.orbisgis.core.ui.editors.map.tools.ZoomOutTool;
import org.orbisgis.core.ui.editors.map.tools.raster.RasterInfoTool;
import org.orbisgis.core.ui.editors.map.tools.raster.WandTool;
import org.orbisgis.core.ui.editors.map.tools.raster.WatershedTool;
import org.orbisgis.core.ui.pluginSystem.PlugInContext;
import org.orbisgis.core.ui.plugins.editors.mapEditor.AddDataBasePlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.AddFilePlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.AddFromFolderPlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.AddWMSLayerPlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.ClearMapSelectionPlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.CreateSourceFromMapSelectionPlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.DeleteMapSelectionPlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.ExportMapAsImagePlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.ExportMapAsPDFPlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.FullExtentPlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.RedoMapPlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.Reverse2DMapSelectionPlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.ShowXYPlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.UndoMapPlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.ZoomToSelectedFeaturesPlugIn;
import org.orbisgis.core.ui.plugins.editors.mapEditor.raster.RasterAlgebraPlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.AddFieldPlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.AddValuePlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.ChangeFieldNamePlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.ClearTableSelectionPlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.CreateSourceFromTableSelectionPlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.DeleteTableSelectionPlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.NewRowTablePlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.RedoTablePlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.RemoveFieldPlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.RevertSelectionTablePlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.SelectAllPlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.SelectEqualPlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.SelectNonePlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.SelectionTableUpPlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.SetNullPlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.ShowFieldInfoPlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.ShowFieldStatisticsPlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.UndoTablePlugIn;
import org.orbisgis.core.ui.plugins.editors.tableEditor.ZoomToLayerFromTable;
import org.orbisgis.core.ui.plugins.editors.tableEditor.ZoomToSelectedPlugIn;
import org.orbisgis.core.ui.plugins.orbisgisFrame.ExitPlugIn;
import org.orbisgis.core.ui.plugins.orbisgisFrame.configuration.ConfigurationPlugIn;
import org.orbisgis.core.ui.plugins.orbisgisFrame.help.AboutOrbisGISPlugIn;
import org.orbisgis.core.ui.plugins.orbisgisFrame.help.OnlineHelpOrbisGISPlugIn;
import org.orbisgis.core.ui.plugins.orbisgisFrame.status.FreeDefaultWorkspacePlugIn;
import org.orbisgis.core.ui.plugins.orbisgisFrame.status.WorkspaceNamePlugin;
import org.orbisgis.core.ui.plugins.orbisgisFrame.workspace.ChangeWorkspacePlugIn;
import org.orbisgis.core.ui.plugins.orbisgisFrame.workspace.SaveWorkspacePlugIn;
import org.orbisgis.core.ui.plugins.sql.SQLFunctionsPlugIn;
import org.orbisgis.core.ui.plugins.toc.CreateGroupPlugIn;
import org.orbisgis.core.ui.plugins.toc.EditLegendPlugIn;
import org.orbisgis.core.ui.plugins.toc.GroupLayersPlugIn;
import org.orbisgis.core.ui.plugins.toc.RemoveLayerPlugIn;
import org.orbisgis.core.ui.plugins.toc.RevertLayerPlugIn;
import org.orbisgis.core.ui.plugins.toc.SaveInDataBasePlugIn;
import org.orbisgis.core.ui.plugins.toc.SaveInFilePlugIn;
import org.orbisgis.core.ui.plugins.toc.SaveLayerPlugIn;
import org.orbisgis.core.ui.plugins.toc.SetActivePlugIn;
import org.orbisgis.core.ui.plugins.toc.SetInactivePlugIn;
import org.orbisgis.core.ui.plugins.toc.ShowInTablePlugIn;
import org.orbisgis.core.ui.plugins.toc.ZoomToLayerPlugIn;
import org.orbisgis.core.ui.plugins.toc.raster.nodata.SetnodataValuePlugIn;
import org.orbisgis.core.ui.plugins.toc.raster.style.RasterDefaultStylePlugIn;
import org.orbisgis.core.ui.plugins.toc.raster.threshold.RasterThresholdPlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.ConvertXYZDemGeocatalogPlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.GeocatalogClearPlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.GeocatalogCreateFileSourcePlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.GeocatalogDeleteSourcePlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.GeocatalogRevertSourcePlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.GeocatalogSaveChangesPlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.GeocatalogSaveInDataBasePlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.GeocatalogSaveInFilePlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.GeocatalogShowTablePlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.GeocatalogStartEditionPlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.GeocatalogStopEditionPlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.NewGeocatalogFilePlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.NewGeocatalogFolderPlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.NewGeocognitionDBPlugIn;
import org.orbisgis.core.ui.plugins.views.geocatalog.WMSGeocatalogPlugIn;
import org.orbisgis.core.ui.plugins.views.geocognition.GeocognitionAddMapPlugIn;
import org.orbisgis.core.ui.plugins.views.geocognition.GeocognitionClearPlugIn;
import org.orbisgis.core.ui.plugins.views.geocognition.GeocognitionNewFolderPlugIn;
import org.orbisgis.core.ui.plugins.views.geocognition.GeocognitionNewSymbolPlugIn;
import org.orbisgis.core.ui.plugins.views.geocognition.OpenGeocognitionPlugIn;
import org.orbisgis.core.ui.plugins.views.geocognition.RemoveGeocognitionPlugIn;
import org.orbisgis.core.ui.plugins.views.mapEditor.MapEditorPlugIn;
import org.orbisgis.core.ui.plugins.views.tableEditor.TableEditorPlugIn;
import org.orbisgis.core.ui.preferences.lookandfeel.OrbisGISIcon;
import org.orbisgis.core.ui.preferences.lookandfeel.images.IconLoader;
import org.orbisgis.core.ui.windows.mainFrame.OrbisGISFrame;

public class OrbisConfiguration implements Setup {
        // The list of extensions that are suported byt the software.
        // used to select the files that will be imported.

        public static ArrayList<String> SUPPORTED_EXTENSIONS = null;
        // OrbisGIS main ToolBar & OrbisGIS main menu
        private ExitPlugIn exitPlugIn = new ExitPlugIn();
        private SaveWorkspacePlugIn saveWorkspacePlugIn = new SaveWorkspacePlugIn();
        private ConfigurationPlugIn configuration = new ConfigurationPlugIn();
        private AboutOrbisGISPlugIn aboutOrbisGIS = new AboutOrbisGISPlugIn();
        private OnlineHelpOrbisGISPlugIn onlineHelpOrbisGIS = new OnlineHelpOrbisGISPlugIn();
        private AddFilePlugIn addFilePlugIn = new AddFilePlugIn();
        private AddDataBasePlugIn addDataBasePlugIn = new AddDataBasePlugIn();
        private AddFromFolderPlugIn addFromFolderPlugIn = new AddFromFolderPlugIn();
        private AddWMSLayerPlugIn addWMSLayerPlugIn = new AddWMSLayerPlugIn();
        //private BasicPerspectivePlugIn basicPerspectivePlugIn = new BasicPerspectivePlugIn();
        // OrbisGIS status
        private WorkspaceNamePlugin workspaceNamePlugin = new WorkspaceNamePlugin();
        private FreeDefaultWorkspacePlugIn freeDefaultWorkspacePlugIn = new FreeDefaultWorkspacePlugIn();
        private ChangeWorkspacePlugIn changeWorkspacePlugIn = new ChangeWorkspacePlugIn();
        // TOC
        private EditLegendPlugIn editLegendPlugIn = new EditLegendPlugIn();
        private RasterDefaultStylePlugIn rasterDefaultStylePlugIn = new RasterDefaultStylePlugIn();
        private SetnodataValuePlugIn setnodataValuePlugIn = new SetnodataValuePlugIn();
        private RasterThresholdPlugIn rasterThresholdPlugIn = new RasterThresholdPlugIn();
        private ShowInTablePlugIn showInTablePlugIn = new ShowInTablePlugIn();
        private SaveInFilePlugIn saveInFilePlugIn = new SaveInFilePlugIn();
        private SaveInDataBasePlugIn saveInDataBasePlugIn = new SaveInDataBasePlugIn();
        private GroupLayersPlugIn groupLayersPlugIn = new GroupLayersPlugIn();
        private RemoveLayerPlugIn removeLayerPlugIn = new RemoveLayerPlugIn();
        private SetActivePlugIn setActivePlugIn = new SetActivePlugIn();
        private SetInactivePlugIn setInactivePlugIn = new SetInactivePlugIn();
        private RevertLayerPlugIn revertLayerPlugIn = new RevertLayerPlugIn();
        private SaveLayerPlugIn saveLayerPlugIn = new SaveLayerPlugIn();
        private CreateGroupPlugIn createGroupPlugIn = new CreateGroupPlugIn();
        private ZoomToLayerPlugIn zoomToLayerPlugIn = new ZoomToLayerPlugIn();
        // DEMO
        // private MyTOCMenu myTOCMenu = new MyTOCMenu();
        // Geocognition popup
        private OpenGeocognitionPlugIn openGeocognitionPlugIn = new OpenGeocognitionPlugIn();
        private RemoveGeocognitionPlugIn removeGeocognitionPlugIn = new RemoveGeocognitionPlugIn();
        private GeocognitionAddMapPlugIn geocognitionAddMapPlugIn = new GeocognitionAddMapPlugIn();
        private GeocognitionNewFolderPlugIn geocognitionNewFolder = new GeocognitionNewFolderPlugIn();
        private GeocognitionNewSymbolPlugIn geocognitionNewSymbol = new GeocognitionNewSymbolPlugIn();
        // Geocatalog popup
        private NewGeocatalogFilePlugIn newGeocatalogFile = new NewGeocatalogFilePlugIn();
        private NewGeocatalogFolderPlugIn newGeocatalogFolder = new NewGeocatalogFolderPlugIn();
        private NewGeocognitionDBPlugIn newGeocognitionDB = new NewGeocognitionDBPlugIn();
        private ConvertXYZDemGeocatalogPlugIn convertXYZDemGeocatalogPlugIn = new ConvertXYZDemGeocatalogPlugIn();
        private WMSGeocatalogPlugIn wMSGeocatalogPlugIn = new WMSGeocatalogPlugIn();
        private GeocognitionClearPlugIn geocognitionClearPlugIn = new GeocognitionClearPlugIn();
        private GeocatalogCreateFileSourcePlugIn geocatalogCreateFileSource = new GeocatalogCreateFileSourcePlugIn();
        private GeocatalogDeleteSourcePlugIn geocatalogDeleteSource = new GeocatalogDeleteSourcePlugIn();
        private GeocatalogClearPlugIn geocatalogClear = new GeocatalogClearPlugIn();
        private GeocatalogShowTablePlugIn geocatalogShowTable = new GeocatalogShowTablePlugIn();
        private GeocatalogSaveInFilePlugIn geocatalogSaveInFilePlugIn = new GeocatalogSaveInFilePlugIn();
        private GeocatalogSaveInDataBasePlugIn geocatalogSaveInDataBasePlugIn = new GeocatalogSaveInDataBasePlugIn();
        private GeocatalogSaveChangesPlugIn geocatalogSaveChangesPlugIn = new GeocatalogSaveChangesPlugIn();
        private GeocatalogStartEditionPlugIn geocatalogStartEditionPligIn = new GeocatalogStartEditionPlugIn();
        private GeocatalogStopEditionPlugIn geocatalogStopEditionPlugIn = new GeocatalogStopEditionPlugIn();
        private GeocatalogRevertSourcePlugIn geocatalogRevertSourcePlugIn = new GeocatalogRevertSourcePlugIn();
        // Table editor Plugins
        // Row actions
        private ClearTableSelectionPlugIn clearTableSelectionPlugIn = new ClearTableSelectionPlugIn();
        private DeleteTableSelectionPlugIn deleteTableSelectionPlugIn = new DeleteTableSelectionPlugIn();
        private SelectionTableUpPlugIn selectionTableUpPlugIn = new SelectionTableUpPlugIn();
        private RevertSelectionTablePlugIn revertSelectionTablePlugIn = new RevertSelectionTablePlugIn();
        private SetNullPlugIn setNullPlugIn = new SetNullPlugIn();
        private SelectEqualPlugIn selectEqualPlugIn = new SelectEqualPlugIn();
        private SelectAllPlugIn selectAllPlugIn = new SelectAllPlugIn();
        private SelectNonePlugIn selectNonePlugIn = new SelectNonePlugIn();
        private ZoomToSelectedPlugIn zoomToSelectedPlugIn = new ZoomToSelectedPlugIn();
        private ZoomToLayerFromTable zoomToLayerFromTable = new ZoomToLayerFromTable();
        private CreateSourceFromTableSelectionPlugIn createSourceFromTableSelectionPlugIn = new CreateSourceFromTableSelectionPlugIn();
        // Column actions
        private ChangeFieldNamePlugIn changeFieldNamePlugIn = new ChangeFieldNamePlugIn();
        private RemoveFieldPlugIn removeFieldPlugIn = new RemoveFieldPlugIn();
        private AddFieldPlugIn addFieldPlugIn = new AddFieldPlugIn();
        private ShowFieldInfoPlugIn showFieldInfoPlugIn = new ShowFieldInfoPlugIn();
        private ShowFieldStatisticsPlugIn showFieldStatisticsPlugIn = new ShowFieldStatisticsPlugIn();
        private AddValuePlugIn addValuePlugIn = new AddValuePlugIn();
        // Popup and toolbars PlugIns for Table editor
        private UndoTablePlugIn undoTablePlugIn = new UndoTablePlugIn();
        private NewRowTablePlugIn newRowTablePlugIn = new NewRowTablePlugIn();
        private RedoTablePlugIn redoTablePlugIn = new RedoTablePlugIn();
        // Map editor PlugIn
        private FullExtentPlugIn fullExtentPlugIn = new FullExtentPlugIn();
        private ClearMapSelectionPlugIn clearMapSelectionPlugIn = new ClearMapSelectionPlugIn();
        private ZoomToSelectedFeaturesPlugIn zoomToSelectedFeaturesPlugIn = new ZoomToSelectedFeaturesPlugIn();
        private UndoMapPlugIn undoMapPlugIn = new UndoMapPlugIn();
        private RedoMapPlugIn redoMapPlugIn = new RedoMapPlugIn();
        private DeleteMapSelectionPlugIn deleteMapSelectionPlugIn = new DeleteMapSelectionPlugIn();
        private Reverse2DMapSelectionPlugIn reverse2DMapSelectionPlugIn = new Reverse2DMapSelectionPlugIn();
        private CreateSourceFromMapSelectionPlugIn createSourceFromMapSelectionPlugIn = new CreateSourceFromMapSelectionPlugIn();
        private RasterAlgebraPlugIn rasterAlgebraPlugIn = new RasterAlgebraPlugIn();
        // Tool bar on map
        private ShowXYPlugIn showXYPlugIn = new ShowXYPlugIn();
        // Scale panel plugin is a swing component to execute action on map editor
        // private ScalePlugIn scalePlugIn = new ScalePlugIn();
        // private CoordinateReferenceSystemPlugIn CRSPlugIn = new
        // CoordinateReferenceSystemPlugIn();
        // right click on Map
        private ExportMapAsImagePlugIn exportMasAsImagePlugIn = new ExportMapAsImagePlugIn();
        private ExportMapAsPDFPlugIn exportMapAsPDFPlugIn = new ExportMapAsPDFPlugIn();
        // Others
        SQLFunctionsPlugIn sQLFunctionsPlugIn = new SQLFunctionsPlugIn();

        public void setup(WorkbenchContext workbenchContext) throws Exception {
                // load Main Menu
                configureMainMenus(workbenchContext);
                // get Plugin context
                PlugInContext plugInContext = workbenchContext.createPlugInContext();
                // i18NTestPlugIn.initialize(plugInContext);
                // Initialize default tool for Map editor
                Automaton defaultTool = new ZoomInTool();
                // Record default tool
                Services.registerService(Automaton.class,
                        "Gives default tool to the editor", defaultTool);
                // Initialize one Map editor without geocognition elment
                MapEditorPlugIn mapEditorPlugIn = new MapEditorPlugIn();
                mapEditorPlugIn.initialize(plugInContext);
                // Initialize table editor
                TableEditorPlugIn tableEditorPlugIn = new TableEditorPlugIn();
                tableEditorPlugIn.initialize(plugInContext);
                // load toolbars (Main toolbar, table toolexceptionbar, Map toolbar)
                configureToolBar(plugInContext);
                // load main frame with default tool selected (Zoom in tool)
                OrbisGISFrame frame = workbenchContext.getWorkbench().getFrame();
                add(defaultTool, OrbisGISIcon.ZOOMIN, frame.getNavigationToolBar());
                // load views (Geocognition, Toc, editors, Beanshell ....)
                OrbisGISConfiguration.loadOrbisGISPlugIns(workbenchContext);
                // Initialize buttons PlugIns in toolbar & Mains menu
                configureTools(plugInContext);
                // load popup
                configurePopup(plugInContext);
                // why set TableEditor & MapEditor popup? Currently popup is build after
                // right click event!
                // Because there are several Map & Table editors so I record popup for
                // all instances
                frame.setTableMenuTreePopup();
                frame.setMapMenuTreePopup();
                // We must fill the file formats that we are able to process
                DataManager dataManager = (DataManager) Services.getService(DataManager.class);
                SourceManager sm = dataManager.getSourceManager();
                DriverManager driverMan = sm.getDriverManager();
                SUPPORTED_EXTENSIONS = new ArrayList<String>();
                for (String name : driverMan.getDriverNames()) {
                        Driver dri = driverMan.getDriver(name);
                        if (dri instanceof FileDriver) {
                                SUPPORTED_EXTENSIONS.addAll(Arrays.asList(((FileDriver) dri).getFileExtensions()));
                        }
                }
                //Add some usefull SQL functions to interact between gdms sql and OrbisGIS.
                sQLFunctionsPlugIn.initialize(plugInContext);
        }

        private void configurePopup(PlugInContext context) {
                try {
                        // TOC popup
                        editLegendPlugIn.initialize(context);
                        rasterDefaultStylePlugIn.initialize(context);
                        setnodataValuePlugIn.initialize(context);
                        rasterThresholdPlugIn.initialize(context);
                        showInTablePlugIn.initialize(context);
                        saveInFilePlugIn.initialize(context);
                        saveInDataBasePlugIn.initialize(context);
                        groupLayersPlugIn.initialize(context);
                        removeLayerPlugIn.initialize(context);
                        setActivePlugIn.initialize(context);
                        setInactivePlugIn.initialize(context);
                        createGroupPlugIn.initialize(context);
                        zoomToLayerPlugIn.initialize(context);
                        revertLayerPlugIn.initialize(context);
                        saveLayerPlugIn.initialize(context);

                        // DEMO

                        // myTOCMenu.initialize(context);

                        // Geocognition popup
                        openGeocognitionPlugIn.initialize(context);
                        removeGeocognitionPlugIn.initialize(context);
                        geocognitionClearPlugIn.initialize(context);
                        geocognitionAddMapPlugIn.initialize(context);
                        geocognitionNewFolder.initialize(context);
                        geocognitionNewSymbol.initialize(context);

                        // Geocatalog popup
                        newGeocatalogFile.initialize(context);
                        newGeocatalogFolder.initialize(context);
                        newGeocognitionDB.initialize(context);
                        wMSGeocatalogPlugIn.initialize(context);
                        convertXYZDemGeocatalogPlugIn.initialize(context);
                        wMSGeocatalogPlugIn.initialize(context);
                        geocatalogCreateFileSource.initialize(context);
                        geocatalogDeleteSource.initialize(context);
                        geocatalogClear.initialize(context);
                        geocatalogShowTable.initialize(context);
                        geocatalogSaveInFilePlugIn.initialize(context);
                        geocatalogSaveInDataBasePlugIn.initialize(context);
                        geocatalogSaveChangesPlugIn.initialize(context);
                        geocatalogStartEditionPligIn.initialize(context);
                        geocatalogStopEditionPlugIn.initialize(context);
                        geocatalogRevertSourcePlugIn.initialize(context);

                        // Table Editor PlugIn popup
                        setNullPlugIn.initialize(context);
                        selectEqualPlugIn.initialize(context);
                        selectAllPlugIn.initialize(context);
                        selectNonePlugIn.initialize(context);
                        zoomToSelectedPlugIn.initialize(context);
                        zoomToLayerFromTable.initialize(context);
                        changeFieldNamePlugIn.initialize(context);
                        removeFieldPlugIn.initialize(context);
                        addFieldPlugIn.initialize(context);
                        showFieldInfoPlugIn.initialize(context);
                        showFieldStatisticsPlugIn.initialize(context);
                        addValuePlugIn.initialize(context);
                        createSourceFromTableSelectionPlugIn.initialize(context);

                        // Map editor : right click on Map
                        exportMasAsImagePlugIn.initialize(context);
                        exportMapAsPDFPlugIn.initialize(context);

                        // testPlugIn.initialize(context);

                } catch (Exception e) {
                        Services.getErrorManager().error(Names.ERROR_POPUP_ADD, e);
                }
        }

        private void configureTools(PlugInContext plugInContext) throws Exception {

                OrbisGISFrame frame = plugInContext.getWorkbenchContext().getWorkbench().getFrame();

                // Main menus
                saveWorkspacePlugIn.initialize(plugInContext);
                configuration.initialize(plugInContext);
                exitPlugIn.initialize(plugInContext);
                addFilePlugIn.initialize(plugInContext);
                addFromFolderPlugIn.initialize(plugInContext);
                addDataBasePlugIn.initialize(plugInContext);
                addWMSLayerPlugIn.initialize(plugInContext);

                aboutOrbisGIS.initialize(plugInContext);
                onlineHelpOrbisGIS.initialize(plugInContext);
                //basicPerspectivePlugIn.initialize(plugInContext);

                // Status toolbar
                workspaceNamePlugin.initialize(plugInContext);
                changeWorkspacePlugIn.initialize(plugInContext);
                freeDefaultWorkspacePlugIn.initialize(plugInContext);

                // Map Toolbar
                clearMapSelectionPlugIn.initialize(plugInContext);
                undoMapPlugIn.initialize(plugInContext);
                redoMapPlugIn.initialize(plugInContext);
                deleteMapSelectionPlugIn.initialize(plugInContext);
                reverse2DMapSelectionPlugIn.initialize(plugInContext);
                createSourceFromMapSelectionPlugIn.initialize(plugInContext);
                add(new ZoomOutTool(), OrbisGISIcon.ZOOMOUT, frame.getNavigationToolBar());
                // ZoomIn/ZoomOut
                add(new PanTool(), OrbisGISIcon.PAN, frame.getNavigationToolBar());
                // Tools in Navigation toolbar
                fullExtentPlugIn.initialize(plugInContext); // after Zoom out for group
                // Tools in Info toolbar
                add(new InfoTool(), OrbisGISIcon.INFO, frame.getInfoToolBar());
                add(new RasterInfoTool(), OrbisGISIcon.RASTERINFO, frame.getInfoToolBar());
                add(new SelectionTool(), OrbisGISIcon.SELECT, frame.getInfoToolBar());
                add(new FencePolygonTool(), OrbisGISIcon.FENCE, frame.getDrawingToolBar());
                add(new PickCoordinatesPointTool(), OrbisGISIcon.PICKPOINT, frame.getDrawingToolBar());
                add(new MesurePolygonTool(), OrbisGISIcon.MESUREAREA, frame.getMesureToolBar());
                add(new MesureLineTool(), OrbisGISIcon.MESURELINE, frame.getMesureToolBar());
                add(new CompassTool(), OrbisGISIcon.MESUREANGLE, frame.getMesureToolBar());
                // Tool in Edition Map Toolbar
                add(new PolygonTool(), OrbisGISIcon.POLYGON, frame.getEditionMapToolBar());
                add(new CutPolygonTool(), IconLoader.getIcon("cutpolygon.png"), frame.getEditionMapToolBar());
                add(new AutoCompletePolygonTool(), IconLoader.getIcon("autocompletepolygon.png"), frame.getEditionMapToolBar());
                add(new SplitPolygonTool(), IconLoader.getIcon("splitpolygon.png"), frame.getEditionMapToolBar());
                add(new EditionSelectionTool(), IconLoader.getIcon("moveVertex.png"), frame.getEditionMapToolBar());
                add(new PointTool(), OrbisGISIcon.POINT, frame.getEditionMapToolBar());
                add(new MultipointTool(), OrbisGISIcon.MULTIPOINT, frame.getEditionMapToolBar());
                add(new LineTool(), OrbisGISIcon.LINE, frame.getEditionMapToolBar());
                add(new MultilineTool(), OrbisGISIcon.MULTILINE, frame.getEditionMapToolBar());
                add(new SplitLineStringTool(), IconLoader.getIcon("splitlinestring.png"), frame.getEditionMapToolBar());
                add(new MultipolygonTool(), OrbisGISIcon.MULTIPOLYGON, frame.getEditionMapToolBar());
                add(new VertexAditionTool(), OrbisGISIcon.VERTEX_ADD, frame.getEditionMapToolBar());
                add(new VertexDeletionTool(), OrbisGISIcon.VERTEX_DELETE, frame.getEditionMapToolBar());
                // Raster toolbar
                add(new WandTool(), OrbisGISIcon.WAND, frame.getRasterToolBar());
                add(new WatershedTool(), OrbisGISIcon.WATERSHED, frame.getRasterToolBar());

                // Info Toolbar
                clearTableSelectionPlugIn.initialize(plugInContext);
                zoomToSelectedFeaturesPlugIn.initialize(plugInContext);

                // Table Edition Toolbar
                undoTablePlugIn.initialize(plugInContext);
                redoTablePlugIn.initialize(plugInContext);
                selectionTableUpPlugIn.initialize(plugInContext);
                revertSelectionTablePlugIn.initialize(plugInContext);
                deleteTableSelectionPlugIn.initialize(plugInContext);
                newRowTablePlugIn.initialize(plugInContext);

                // Map tools
                // scalePlugIn.initialize(plugInContext);
                showXYPlugIn.initialize(plugInContext);
                // CRSPlugIn.initialize(plugInContext);

                // Raster Tool bar
                rasterAlgebraPlugIn.initialize(plugInContext);
        }

        /**
         * Create here all toolbars available in the plugincontext.
         *
         * @param plugInContext
         */
        private void configureToolBar(PlugInContext plugInContext) {
                // Frame tool bar
                OrbisGISFrame frame = plugInContext.getWorkbenchContext().getWorkbench().getFrame();
                WorkbenchContext wbContext = plugInContext.getWorkbenchContext();
                WorkbenchToolBar wbToolBar = frame.getWorkbenchToolBar();
                // Add Toolbars
                WorkbenchToolBar wbMain = new WorkbenchToolBar(wbContext,
                        Names.TOOLBAR_MAIN);
                wbToolBar.add(wbMain);
                WorkbenchToolBar wbNavigation = new WorkbenchToolBar(wbContext,
                        Names.TOOLBAR_NAVIGATION);
                wbToolBar.add(wbNavigation);
                WorkbenchToolBar wbInfo = new WorkbenchToolBar(wbContext,
                        Names.TOOLBAR_INFO);
                wbToolBar.add(wbInfo);
                WorkbenchToolBar wbSelection = new WorkbenchToolBar(wbContext,
                        Names.TOOLBAR_SELECTION);
                wbToolBar.add(wbSelection);

                WorkbenchToolBar wbMesureArea = new WorkbenchToolBar(wbContext,
                        Names.TOOLBAR_MESURE, OrbisGISIcon.MESURELINE, true);
                wbToolBar.add(wbMesureArea);
                WorkbenchToolBar wbDrawing = new WorkbenchToolBar(wbContext,
                        Names.TOOLBAR_DRAWING, OrbisGISIcon.FENCE, true);
                wbToolBar.add(wbDrawing);
                WorkbenchToolBar wbRasterToolBar = new WorkbenchToolBar(wbContext,
                        Names.TOOLBAR_RASTER, OrbisGISIcon.WAND, true);
                wbToolBar.add(wbRasterToolBar);
                WorkbenchToolBar wbEditionMap = new WorkbenchToolBar(wbContext,
                        Names.TOOLBAR_MAP);
                wbToolBar.add(wbEditionMap);
                WorkbenchToolBar wbEditionTable = new WorkbenchToolBar(wbContext,
                        Names.TOOLBAR_TABLE);
                wbToolBar.add(wbEditionTable);

                WorkbenchToolBar wbStatusMainToolBar = new WorkbenchToolBar(wbContext,
                        Names.MAIN_STATUS_TOOLBAR_MAIN);
                wbToolBar.add(wbStatusMainToolBar);

                WorkbenchToolBar wbViewToolBar = new WorkbenchToolBar(wbContext,
                        Names.VIEW_TOOLBAR);
                wbToolBar.add(wbViewToolBar);

        }

        private void configureMainMenus(final WorkbenchContext workbenchContext)
                throws Exception {
                JMenuBar menuBar = workbenchContext.getWorkbench().getFrame().getActionMenuBar();
                JMenu defaultMenu = (JMenu) FeatureInstaller.installMnemonic(new JMenu(
                        Names.FILE), menuBar);
                defaultMenu.addMenuListener(new MenuListener() {

                        public void menuCanceled(MenuEvent e) {
                        }

                        public void menuDeselected(MenuEvent e) {
                        }

                        public void menuSelected(MenuEvent e) {
                                workbenchContext.setLastAction(Names.ACTION_MENU_FILE);
                        }
                });
                menuBar.add(defaultMenu);
                JMenu viewMenu = (JMenu) FeatureInstaller.installMnemonic(new JMenu(
                        Names.VIEW), menuBar);
                viewMenu.addMenuListener(new MenuListener() {

                        public void menuCanceled(MenuEvent e) {
                        }

                        public void menuDeselected(MenuEvent e) {
                        }

                        public void menuSelected(MenuEvent e) {
                                workbenchContext.setLastAction(Names.ACTION_MENU_VIEW);
                        }
                });
                menuBar.add(viewMenu);

                JMenu helpMenu = (JMenu) FeatureInstaller.installMnemonic(new JMenu(
                        Names.HELP), menuBar);
                menuBar.add(helpMenu);
        }

        private AbstractButton add(Automaton tool, ImageIcon icon,
                WorkbenchToolBar wbToolbar) {
                return wbToolbar.addAutomaton(tool, icon);
        }

        /**
         * Checks that a file can be imported in GDMS
         *
         * @param file
         * @return
         */
        public static boolean isFileEligible(File file) {
                String fileName = file.getName();
                int index = fileName.lastIndexOf('.');
                if (index >= 0 && index < fileName.length()) {

                        String ext = fileName.substring(index + 1);
                        return OrbisConfiguration.SUPPORTED_EXTENSIONS.contains(ext.toLowerCase());
                }
                return false;
        }
}
