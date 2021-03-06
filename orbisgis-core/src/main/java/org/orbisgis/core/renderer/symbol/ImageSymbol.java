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
package org.orbisgis.core.renderer.symbol;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.gdms.driver.DriverException;
import org.orbisgis.core.map.MapTransform;
import org.orbisgis.core.renderer.RenderContext;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.Point;
import org.gdms.data.types.Type;

public class ImageSymbol extends AbstractSymbol implements Symbol {

	protected Image img;
	protected URL url;

	public ImageSymbol() {
		setName(getClassName());
		setErrorImage();
	}

        @Override
	public boolean acceptGeometry(Geometry geom) {
		return (geom instanceof Point) || (geom instanceof MultiPoint);
	}

        @Override
	public boolean acceptGeometryType(Type geometryTypeConstraint) {
		if (geometryTypeConstraint == null || geometryTypeConstraint.getTypeCode() == Type.NULL) {
			return true;
		} else {
			int gt = geometryTypeConstraint.getTypeCode();
			switch (gt) {
                                case Type.POINT:
                                case Type.MULTIPOINT:
                                        return true;
                                default:
                                        return false;
			}
		}
	}

        @Override
	public Symbol cloneSymbol() {
		ImageSymbol ret = new ImageSymbol();
		ret.img = this.img;
		ret.url = this.url;
		return ret;
	}

        @Override
	public Envelope draw(Graphics2D g, Geometry geom, MapTransform mt,
			RenderContext permission) throws DriverException {
		// LiteShape ls = new LiteShape(geom, at, false);
		Shape ls = mt.getShapeWriter().toShape(geom);
		PathIterator pi = ls.getPathIterator(null);
		double[] coords = new double[6];
		while (!pi.isDone()) {
			pi.currentSegment(coords);
			g.drawImage(img, (int) coords[0] - img.getWidth(null) / 2,
					(int) coords[1] - img.getHeight(null) / 2, null);
			pi.next();
		}

		return null;
	}

        @Override
	public String getClassName() {
		return "Image on point";
	}

        @Override
	public String getId() {
		return "org.orbisgis.symbols.point.Image";
	}

        @Override
	public Map<String, String> getPersistentProperties() {
		HashMap<String, String> ret = new HashMap<String, String>();
		ret.putAll(super.getPersistentProperties());
		ret.put("url", url.toString());

		return ret;
	}

        @Override
	public void setPersistentProperties(Map<String, String> props) {
		super.setPersistentProperties(props);
		try {
			setImageURL(new URL(props.get("url")));
		} catch (MalformedURLException e) {
			setErrorImage();
		} catch (IOException e) {
			setErrorImage();
		}
	}

	private void setErrorImage() {
		BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		FontMetrics fm = g.getFontMetrics();
		String str = "Image not available";
		Rectangle2D bounds = fm.getStringBounds(str, g);
		bi = new BufferedImage((int) bounds.getWidth(), (int) bounds
				.getHeight(), BufferedImage.TYPE_INT_ARGB);
		g = bi.createGraphics();
		g.setColor(Color.black);
		g.drawString(str, 0, (int) bounds.getHeight());
		img = bi;
	}

	/**
	 * Sets the URL of the image to display
	 * 
	 * @param url
	 * @throws IOException
	 *             If the image cannot be loaded
	 */
	public void setImageURL(URL url) throws IOException {
		this.url = url;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			setErrorImage();
			throw e;
		}
	}

	/**
	 * Get the URL of the image used to draw this symbol
	 * 
	 * @return
	 */
	public URL getImageURL() {
		return url;
	}

	@Override
	public Symbol deriveSymbol(Color color) {
		return null;
	}

}
