/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information. OrbisGIS is
 * distributed under GPL 3 license. It is produced by the "Atelier SIG" team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/> CNRS FR 2488.
 *
 *
 *  Team leader Erwan BOCHER, scientific researcher,
 *
 *  User support leader : Gwendall Petit, geomatic engineer.
 *
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * Copyright (C) 2010 Erwan BOCHER, Pierre-Yves FADET, Alexis GUEGANNO, Maxence LAURENT
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
 *
 * or contact directly:
 * erwan.bocher _at_ ec-nantes.fr
 * gwendall.petit _at_ ec-nantes.fr
 */
package org.orbisgis.core.renderer.se;

import com.vividsolutions.jts.geom.Geometry;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import java.io.IOException;
import javax.xml.bind.JAXBElement;
import org.gdms.data.SpatialDataSourceDecorator;
import net.opengis.se._2_0.core.ExtensionType;
import net.opengis.se._2_0.core.ObjectFactory;
import net.opengis.se._2_0.core.PointSymbolizerType;

import org.gdms.driver.DriverException;
import org.orbisgis.core.Services;
import org.orbisgis.core.map.MapTransform;
import org.orbisgis.core.renderer.RenderContext;
import net.opengis.se._2_0.core.ExtensionParameterType;
import org.orbisgis.core.renderer.se.SeExceptions.InvalidStyle;
import org.orbisgis.core.renderer.se.common.Uom;
import org.orbisgis.core.renderer.se.graphic.GraphicCollection;
import org.orbisgis.core.renderer.se.graphic.MarkGraphic;

import org.orbisgis.core.renderer.se.parameter.ParameterException;
import org.orbisgis.core.renderer.se.transform.Transform;

public final class PointSymbolizer extends VectorSymbolizer implements GraphicNode {

    private static final String MODE_VERTEX = "vertex";
    /*
     * Create a default pointSymbolizer: Square 10mm
     *
     *
     */

    public PointSymbolizer() {
        super();
        this.name = "Point symbolizer";
        setGraphicCollection(new GraphicCollection());
        uom = Uom.MM;

        MarkGraphic mark = new MarkGraphic();
        mark.setTo3mmCircle();
        graphic.addGraphic(mark);
        onVertex = false;
    }

    public PointSymbolizer(JAXBElement<PointSymbolizerType> st) throws InvalidStyle {
        super(st);
        PointSymbolizerType pst = st.getValue();

        if (pst.getGeometry() != null) {
            // TODO load GeometryFunction !
        }


        onVertex = false;
        if (pst.getExtension() != null) {
            for (ExtensionParameterType param : pst.getExtension().getExtensionParameter()) {
                if (param.getName().equalsIgnoreCase("mode")) {
                    //level = Integer.parseInt(param.getContent());
                    onVertex = param.getContent().equalsIgnoreCase(MODE_VERTEX);
                    break;
                }
            }
        }

        if (pst.getUom() != null) {
            Uom u = Uom.fromOgcURN(pst.getUom());
            this.setUom(u);
        }

        if (pst.getTransform() != null) {
            this.setTransform(new Transform(pst.getTransform()));
        }

        if (pst.getGraphic() != null) {
            this.setGraphicCollection(new GraphicCollection(pst.getGraphic(), this));

        }
    }

    @Override
    public GraphicCollection getGraphicCollection() {
        return graphic;
    }

    @Override
    public void setGraphicCollection(GraphicCollection graphic) {
        this.graphic = graphic;
        graphic.setParent(this);
    }

    @Override
    public void draw(Graphics2D g2, SpatialDataSourceDecorator sds, long fid,
            boolean selected, MapTransform mt, Geometry the_geom, RenderContext perm)
            throws IOException, DriverException {
        if (graphic != null && graphic.getNumGraphics() > 0) {

            try {
                double x = 0, y = 0;

                if (onVertex) {
                    for (Point2D pt : getPoints(sds, fid, mt, the_geom)) {
                        x = pt.getX();
                        y = pt.getY();

                        graphic.draw(g2, sds, fid, selected, mt, AffineTransform.getTranslateInstance(x, y));
                    }
                } else {
                    Point2D pt = getPointShape(sds, fid, mt, the_geom);


                    x = pt.getX();
                    y = pt.getY();

                    // Draw the graphic right over the point !
                    graphic.draw(g2, sds, fid, selected, mt, AffineTransform.getTranslateInstance(x, y));
                }
            } catch (ParameterException ex) {
                Services.getErrorManager().error("Could not render feature ", ex);
            }
        }
    }

    @Override
    public JAXBElement<PointSymbolizerType> getJAXBElement() {
        ObjectFactory of = new ObjectFactory();
        PointSymbolizerType s = of.createPointSymbolizerType();

        this.setJAXBProperty(s);


        if (this.uom != null) {
            s.setUom(this.getUom().toURN());
        }

        if (transform != null) {
            s.setTransform(transform.getJAXBType());
        }

        if (graphic != null) {
            s.setGraphic(graphic.getJAXBElement());
        }

        if (onVertex) {
            ExtensionType exts = s.getExtension();
            ExtensionParameterType param = of.createExtensionParameterType();
            param.setName("mode");
            param.setContent(MODE_VERTEX);
            exts.getExtensionParameter().add(param);
        }

        return of.createPointSymbolizer(s);
    }

    public boolean isOnVertex() {
        return onVertex;
    }

    public void setOnVertex(boolean onVertex) {
        this.onVertex = onVertex;
    }
    private GraphicCollection graphic;
    private boolean onVertex;
}