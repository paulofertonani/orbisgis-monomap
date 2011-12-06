/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.orbisgis.core.renderer.se.parameter.real;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import net.opengis.se._2_0.core.AreaSymbolizerType;
import net.opengis.se._2_0.core.CategorizeType;
import net.opengis.se._2_0.core.StyleType;
import net.opengis.se._2_0.thematic.DensityFillType;
import org.orbisgis.core.AbstractTest;
import org.orbisgis.core.renderer.se.Style;

/**
 *
 * @author alexis
 */
public class Categorize2RealTest extends AbstractTest {
        
        private String xml = "src/test/resources/org/orbisgis/core/renderer/se/Districts/density_hatch_classif.se";

        public void testFromJAXB() throws Exception {
                JAXBContext jaxbContext = JAXBContext.newInstance(StyleType.class);
                Unmarshaller u = jaxbContext.createUnmarshaller();
                JAXBElement<StyleType> ftsElem = (JAXBElement<StyleType>) u.unmarshal(
                        new FileInputStream(xml));
                AreaSymbolizerType ast = (AreaSymbolizerType) (ftsElem.getValue().getRule().get(0).getSymbolizer().getValue());
                DensityFillType dft = (DensityFillType) (ast.getFill().getValue());
                JAXBElement je = (JAXBElement) (dft.getPercentage().getContent().get(1));
                Categorize2Real c2r = new Categorize2Real((CategorizeType) je.getValue());
                assertTrue(c2r.getClassValue(0).getValue(null, 0) == 0.3);
                assertTrue(c2r.getClassValue(1).getValue(null, 0) == 0.4);
                assertTrue(c2r.getClassValue(2).getValue(null, 0) == 0.45);
                assertTrue(c2r.getClassValue(3).getValue(null, 0) == 0.5);
                assertTrue(c2r.getClassValue(4).getValue(null, 0) == 0.55);
                assertTrue(c2r.getClassValue(5).getValue(null, 0) == 0.6);
                assertTrue(c2r.getClassValue(6).getValue(null, 0) == 0.7);
                assertTrue(c2r.getClassValue(7).getValue(null, 0) == 0.8);
        }

        public void testMarshalAndUnmarshal() throws Exception {
                JAXBContext jaxbContext = JAXBContext.newInstance(StyleType.class);
                Unmarshaller u = jaxbContext.createUnmarshaller();
                JAXBElement<StyleType> ftsElem = (JAXBElement<StyleType>) u.unmarshal(
                        new FileInputStream(xml));
                Marshaller m = jaxbContext.createMarshaller();
                m.marshal(ftsElem, new FileOutputStream("target/c2routput.se"));
                Style st = new Style(ftsElem, null);
                JAXBElement<StyleType> elem = st.getJAXBElement();
                m.marshal(elem, new FileOutputStream("target/c2routput.se"));
                assertTrue(true);

        }
}