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

import org.junit.Test;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.gdms.driver.DriverException;
import org.orbisgis.core.AbstractTest;
import org.orbisgis.core.Services;
import org.orbisgis.core.map.MapTransform;
import org.orbisgis.core.renderer.AllowAllRenderContext;
import org.orbisgis.core.renderer.RenderContext;
import org.orbisgis.core.renderer.symbol.collection.persistence.SymbolType;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import org.gdms.data.types.Type;
import static org.junit.Assert.*;

public class SymbolTest extends AbstractTest {

        private final class TestSymbol extends AbstractSymbol {

                @Override
                public String getId() {
                        return "org.new.symbol";
                }

                @Override
                public String getClassName() {
                        return "Test symbol";
                }

                @Override
                public Envelope draw(Graphics2D g, Geometry geom, MapTransform mt,
                        RenderContext permission) throws DriverException {
                        return null;
                }

                @Override
                public Symbol cloneSymbol() {
                        return new TestSymbol();
                }

                @Override
                public boolean acceptGeometryType(Type geometryType) {
                        return false;
                }

                @Override
                public boolean acceptGeometry(Geometry geom) {
                        return false;
                }

                @Override
                public Symbol deriveSymbol(Color color) {
                        return null;
                }
        }

        @Test
        public void testManagerClones() throws Exception {
                SymbolManager sv = getSymbolManager();
                Symbol polSym = SymbolFactory.createPolygonSymbol();
                Symbol sym = sv.createSymbol(polSym.getId());
                assertTrue(testEquals(sv.createSymbol(polSym.getId()), sym));
                ((StandardLineSymbol) sym).setOutlineColor(Color.pink);
                assertFalse(testEquals(sv.createSymbol(polSym.getId()), sym));
        }

        private SymbolManager getSymbolManager() {
                SymbolManager sm = (SymbolManager) Services.getService(SymbolManager.class);
                return sm;
        }

        @Test
        public void testAvailableSymbolsClone() throws Exception {
                SymbolManager sv = getSymbolManager();
                ArrayList<Symbol> symbols = sv.getAvailableSymbols();
                HashSet<Class<? extends Symbol>> classes = new HashSet<Class<? extends Symbol>>();
                for (Symbol symbol : symbols) {
                        Class<? extends Symbol> symbolClass = symbol.getClass();
                        assertFalse(classes.contains(symbolClass));
                        classes.add(symbol.getClass());
                        assertTrue(testClone(symbol));
                }
        }

        @Test
        public void testAvailableSymbolsPersistence() throws Exception {
                SymbolManager sv = getSymbolManager();
                ArrayList<Symbol> symbols = sv.getAvailableSymbols();
                for (Symbol symbol : symbols) {
                        testPersistent(symbol);
                }
        }

        private boolean testClone(Symbol symbol) throws DriverException {
                return testEquals(symbol, symbol.cloneSymbol());
        }

        private boolean testEquals(Symbol symbol1, Symbol symbol2)
                throws DriverException {
                return symbol1.getPersistentProperties().equals(
                        symbol2.getPersistentProperties());
        }

        @Test
        public void testTransparencyPersistence() throws Exception {
                Symbol sym = SymbolFactory.createPointCircleSymbol(new Color(10, 10,
                        10, 10), new Color(10, 10, 10, 10), 10);
                Map<String, String> props = sym.getPersistentProperties();
                Symbol sym2 = SymbolFactory.createPointCircleSymbol(new Color(10, 10,
                        10, 10), new Color(10, 10, 10, 10), 10);
                sym2.setPersistentProperties(props);

                assertTrue(testEquals(sym, sym2));
        }

        @Test
        public void testFillPersistence() throws Exception {
                Symbol sym = SymbolFactory.createPolygonSymbol(Color.black);
                testPersistent(sym);
        }

        private Symbol testPersistent(Symbol sym) {
                Symbol sym2 = getSymbolManager().createSymbol(sym.getId());
                sym2.setPersistentProperties(sym.getPersistentProperties());
                assertEquals(sym.getPersistentProperties(), sym2.getPersistentProperties());

                return sym2;
        }

        @Test
        public void testOutlinePersistence() throws Exception {
                Symbol sym = SymbolFactory.createPolygonSymbol(null, Color.black);
                testPersistent(sym);
        }

        @Test
        public void testMapUnitsPersistence() throws Exception {
                StandardPointSymbol sym = (StandardPointSymbol) SymbolFactory.createPointCircleSymbol(Color.black, Color.black, 10);
                sym.setMapUnits(true);
                testPersistent(sym);
                sym.setMapUnits(false);
                assertFalse(((StandardPointSymbol) testPersistent(sym)).isMapUnits());
        }

        @Test
        public void testLoadNonExistingSymbol() throws Exception {
                SymbolManager sm = getSymbolManager();
                TestSymbol symbol = new TestSymbol();
                SymbolType obj = sm.getJAXBSymbol(symbol);
                Symbol symbol2 = sm.getSymbolFromJAXB(obj);
                assertNull(symbol2);
                sm.addSymbol(symbol);
                symbol2 = sm.getSymbolFromJAXB(obj);
                assertTrue(testEquals(symbol, symbol2));
        }

        @Test
        public void testAlreadyExists() throws Exception {
                try {
                        getSymbolManager().addSymbol(SymbolFactory.createPolygonSymbol());
                        fail();
                } catch (IllegalArgumentException e) {
                }
        }

        @Test
        public void testDerivedNullInComposite() throws Exception {
                Symbol toDerive = SymbolFactory.createSymbolComposite(new NullDerivedSymbol());
                Symbol derived = toDerive.deriveSymbol(Color.pink);
                BufferedImage bi = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
                derived.draw(bi.createGraphics(), new GeometryFactory().createMultiPolygon(new Polygon[0]), new MapTransform(),
                        new AllowAllRenderContext());
        }

        private class NullDerivedSymbol extends AbstractPolygonSymbol implements
                Symbol {

                public NullDerivedSymbol(Color outline, int lineWidth, Color fillColor) {
                        super(outline, lineWidth, fillColor);
                }

                public NullDerivedSymbol() {
                        super(Color.black, 3, null);
                }

                @Override
                public Symbol cloneSymbol() {
                        return new NullDerivedSymbol(outline, lineWidth, fillColor);
                }

                @Override
                public Symbol deriveSymbol(Color color) {
                        return null;
                }

                @Override
                public Envelope draw(Graphics2D g, Geometry geom, MapTransform at,
                        RenderContext permission) throws DriverException {
                        return null;
                }

                @Override
                public String getClassName() {
                        return "null derived for tests";
                }

                @Override
                public String getId() {
                        return "org.orbisgis.symbol.test.NullDerived";
                }
        }
}
