package org.orbisgis.core.renderer.se;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import java.io.IOException;
import javax.media.jai.RenderableGraphics;
import javax.xml.bind.JAXBElement;
import org.gdms.data.feature.Feature;
import org.orbisgis.core.renderer.persistance.se.ObjectFactory;
import org.orbisgis.core.renderer.persistance.se.PointSymbolizerType;

import org.gdms.driver.DriverException;
import org.orbisgis.core.renderer.se.common.MapEnv;
import org.orbisgis.core.renderer.se.common.Uom;
import org.orbisgis.core.renderer.se.graphic.GraphicCollection;
import org.orbisgis.core.renderer.se.graphic.MarkGraphic;

import org.orbisgis.core.renderer.se.parameter.ParameterException;
import org.orbisgis.core.renderer.se.transform.Transform;

public class PointSymbolizer extends VectorSymbolizer {

	/*
	 * Create a default pointSymbolizer: Square 10mm
	 *
	 *
	 */
	public PointSymbolizer() {
		graphic = new GraphicCollection();
		graphic.setParent(this);
		uom = Uom.MM;

		MarkGraphic mark = new MarkGraphic();
		mark.setParent(graphic);
		mark.setToSquare10();
		graphic.addGraphic(mark);
	}

	public PointSymbolizer(JAXBElement<PointSymbolizerType> st) {
		PointSymbolizerType ast = st.getValue();

		if (ast.getGeometry() != null) {
			// TODO
		}

		if (ast.getUnitOfMeasure() != null) {
			Uom u = Uom.fromOgcURN(ast.getUnitOfMeasure());
			System.out.println("This is the UOM: " + u);
			this.setUom(u);
		}

		if (ast.getTransform() != null) {
			this.setTransform(new Transform(ast.getTransform()));
		}

		if (ast.getGraphic() != null) {
			this.setGraphic(new GraphicCollection(ast.getGraphic(), this));

		}
	}

	public GraphicCollection getGraphic() {
		return graphic;
	}

	public void setGraphic(GraphicCollection graphic) {
		this.graphic = graphic;
		graphic.setParent(this);
	}

	@Override
	public void draw(Graphics2D g2, Feature feat, boolean selected) throws ParameterException, IOException, DriverException {
		if (graphic != null && graphic.getNumGraphics() > 0) {
			Point2D pt = this.getPointShape(feat);
			
			//Point2D pt = this.getFirstPointShape(sds, fid); // This is to emulate ExtractFirstPoint geom function !!!
			RenderableGraphics rg = graphic.getGraphic(feat, selected);
			//RenderedImage cache = graphic.getCache(sds, fid, selected);

			//if (cache != null) {
			if (rg != null) {
				double x = 0, y = 0;

				x = pt.getX();
				y = pt.getY();

				// Draw the graphic right over the point !
				g2.drawRenderedImage(rg.createRendering(MapEnv.getCurrentRenderContext()), AffineTransform.getTranslateInstance(x, y));
				//g2.drawRenderedImage(cache, AffineTransform.getTranslateInstance(x, y));
			}
		}
	}

	@Override
	public JAXBElement<PointSymbolizerType> getJAXBElement() {
		ObjectFactory of = new ObjectFactory();
		PointSymbolizerType s = of.createPointSymbolizerType();

		this.setJAXBProperty(s);


		if (this.uom != null) {
			s.setUnitOfMeasure(this.getUom().toURN());
		}

		if (transform != null) {
			s.setTransform(transform.getJAXBType());
		}


		if (graphic != null) {
			s.setGraphic(graphic.getJAXBElement());
		}

		return of.createPointSymbolizer(s);
	}
	private GraphicCollection graphic;
}