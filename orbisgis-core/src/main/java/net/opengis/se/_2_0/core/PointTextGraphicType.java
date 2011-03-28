//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.17 at 12:33:03 PM CET 
//


package net.opengis.se._2_0.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PointTextGraphicType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PointTextGraphicType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/se/2.0/core}GraphicType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/se/2.0/core}PointPosition" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/se/2.0/core}PointLabel" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="uom" type="{http://www.opengis.net/se/2.0/core}UomType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PointTextGraphicType", propOrder = {
    "pointPosition",
    "pointLabel"
})
public class PointTextGraphicType
    extends GraphicType
{

    @XmlElement(name = "PointPosition")
    protected PointPositionType pointPosition;
    @XmlElement(name = "PointLabel")
    protected PointLabelType pointLabel;
    @XmlAttribute
    protected String uom;

    /**
     * Gets the value of the pointPosition property.
     * 
     * @return
     *     possible object is
     *     {@link PointPositionType }
     *     
     */
    public PointPositionType getPointPosition() {
        return pointPosition;
    }

    /**
     * Sets the value of the pointPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link PointPositionType }
     *     
     */
    public void setPointPosition(PointPositionType value) {
        this.pointPosition = value;
    }

    /**
     * Gets the value of the pointLabel property.
     * 
     * @return
     *     possible object is
     *     {@link PointLabelType }
     *     
     */
    public PointLabelType getPointLabel() {
        return pointLabel;
    }

    /**
     * Sets the value of the pointLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link PointLabelType }
     *     
     */
    public void setPointLabel(PointLabelType value) {
        this.pointLabel = value;
    }

    /**
     * Gets the value of the uom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUom() {
        return uom;
    }

    /**
     * Sets the value of the uom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUom(String value) {
        this.uom = value;
    }

}
