package org.orbisgis.geoview.toc;
import org.orbisgis.core.resourceTree.ResourceTreeActionExtensionPointHelper;
import org.orbisgis.geoview.GeoView2D;
import org.orbisgis.geoview.layerModel.ILayer;
import org.orbisgis.pluginManager.ExtensionPointManager;


public class EPTocLayerActionHelper extends ResourceTreeActionExtensionPointHelper {

	public static void execute(GeoView2D geoview, String actionId, ILayer[] selectedResources) {
		ExtensionPointManager<ILayerAction> epm = new ExtensionPointManager<ILayerAction>(
				"org.orbisgis.geoview.toc.LayerAction");
		ILayerAction action = epm.instantiateFrom("/extension/action[@id='"
				+ actionId + "']", "class");
		if (selectedResources.length == 0) {
			action.execute(geoview, null);
		} else {
			for (ILayer resource : selectedResources) {
				action.execute(geoview, resource);
			}
		}
		action.executeAll(geoview, selectedResources);

	}

}
