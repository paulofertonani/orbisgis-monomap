//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.11 at 11:51:06 AM CET 
//


package net.opengis.ows._2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.opengis.ows._2 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IndividualName_QNAME = new QName("http://www.opengis.net/ows/2.0", "IndividualName");
    private final static QName _Language_QNAME = new QName("http://www.opengis.net/ows/2.0", "Language");
    private final static QName _Role_QNAME = new QName("http://www.opengis.net/ows/2.0", "Role");
    private final static QName _Title_QNAME = new QName("http://www.opengis.net/ows/2.0", "Title");
    private final static QName _Keywords_QNAME = new QName("http://www.opengis.net/ows/2.0", "Keywords");
    private final static QName _AvailableCRS_QNAME = new QName("http://www.opengis.net/ows/2.0", "AvailableCRS");
    private final static QName _Fees_QNAME = new QName("http://www.opengis.net/ows/2.0", "Fees");
    private final static QName _ContactInfo_QNAME = new QName("http://www.opengis.net/ows/2.0", "ContactInfo");
    private final static QName _SupportedCRS_QNAME = new QName("http://www.opengis.net/ows/2.0", "SupportedCRS");
    private final static QName _OutputFormat_QNAME = new QName("http://www.opengis.net/ows/2.0", "OutputFormat");
    private final static QName _Abstract_QNAME = new QName("http://www.opengis.net/ows/2.0", "Abstract");
    private final static QName _Metadata_QNAME = new QName("http://www.opengis.net/ows/2.0", "Metadata");
    private final static QName _PointOfContact_QNAME = new QName("http://www.opengis.net/ows/2.0", "PointOfContact");
    private final static QName _Identifier_QNAME = new QName("http://www.opengis.net/ows/2.0", "Identifier");
    private final static QName _AbstractMetaData_QNAME = new QName("http://www.opengis.net/ows/2.0", "AbstractMetaData");
    private final static QName _OrganisationName_QNAME = new QName("http://www.opengis.net/ows/2.0", "OrganisationName");
    private final static QName _WGS84BoundingBox_QNAME = new QName("http://www.opengis.net/ows/2.0", "WGS84BoundingBox");
    private final static QName _BoundingBox_QNAME = new QName("http://www.opengis.net/ows/2.0", "BoundingBox");
    private final static QName _AccessConstraints_QNAME = new QName("http://www.opengis.net/ows/2.0", "AccessConstraints");
    private final static QName _PositionName_QNAME = new QName("http://www.opengis.net/ows/2.0", "PositionName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.opengis.ows._2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BoundingBoxType }
     * 
     */
    public BoundingBoxType createBoundingBoxType() {
        return new BoundingBoxType();
    }

    /**
     * Create an instance of {@link DescriptionType }
     * 
     */
    public DescriptionType createDescriptionType() {
        return new DescriptionType();
    }

    /**
     * Create an instance of {@link OnlineResourceType }
     * 
     */
    public OnlineResourceType createOnlineResourceType() {
        return new OnlineResourceType();
    }

    /**
     * Create an instance of {@link IdentificationType }
     * 
     */
    public IdentificationType createIdentificationType() {
        return new IdentificationType();
    }

    /**
     * Create an instance of {@link LanguageStringType }
     * 
     */
    public LanguageStringType createLanguageStringType() {
        return new LanguageStringType();
    }

    /**
     * Create an instance of {@link ResponsiblePartySubsetType }
     * 
     */
    public ResponsiblePartySubsetType createResponsiblePartySubsetType() {
        return new ResponsiblePartySubsetType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link KeywordsType }
     * 
     */
    public KeywordsType createKeywordsType() {
        return new KeywordsType();
    }

    /**
     * Create an instance of {@link WGS84BoundingBoxType }
     * 
     */
    public WGS84BoundingBoxType createWGS84BoundingBoxType() {
        return new WGS84BoundingBoxType();
    }

    /**
     * Create an instance of {@link TelephoneType }
     * 
     */
    public TelephoneType createTelephoneType() {
        return new TelephoneType();
    }

    /**
     * Create an instance of {@link ContactType }
     * 
     */
    public ContactType createContactType() {
        return new ContactType();
    }

    /**
     * Create an instance of {@link CodeType }
     * 
     */
    public CodeType createCodeType() {
        return new CodeType();
    }

    /**
     * Create an instance of {@link MetadataType }
     * 
     */
    public MetadataType createMetadataType() {
        return new MetadataType();
    }

    /**
     * Create an instance of {@link BasicIdentificationType }
     * 
     */
    public BasicIdentificationType createBasicIdentificationType() {
        return new BasicIdentificationType();
    }

    /**
     * Create an instance of {@link ResponsiblePartyType }
     * 
     */
    public ResponsiblePartyType createResponsiblePartyType() {
        return new ResponsiblePartyType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "IndividualName")
    public JAXBElement<String> createIndividualName(String value) {
        return new JAXBElement<String>(_IndividualName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "Language")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createLanguage(String value) {
        return new JAXBElement<String>(_Language_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "Role")
    public JAXBElement<CodeType> createRole(CodeType value) {
        return new JAXBElement<CodeType>(_Role_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageStringType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "Title")
    public JAXBElement<LanguageStringType> createTitle(LanguageStringType value) {
        return new JAXBElement<LanguageStringType>(_Title_QNAME, LanguageStringType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KeywordsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "Keywords")
    public JAXBElement<KeywordsType> createKeywords(KeywordsType value) {
        return new JAXBElement<KeywordsType>(_Keywords_QNAME, KeywordsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "AvailableCRS")
    public JAXBElement<String> createAvailableCRS(String value) {
        return new JAXBElement<String>(_AvailableCRS_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "Fees")
    public JAXBElement<String> createFees(String value) {
        return new JAXBElement<String>(_Fees_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "ContactInfo")
    public JAXBElement<ContactType> createContactInfo(ContactType value) {
        return new JAXBElement<ContactType>(_ContactInfo_QNAME, ContactType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "SupportedCRS", substitutionHeadNamespace = "http://www.opengis.net/ows/2.0", substitutionHeadName = "AvailableCRS")
    public JAXBElement<String> createSupportedCRS(String value) {
        return new JAXBElement<String>(_SupportedCRS_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "OutputFormat")
    public JAXBElement<String> createOutputFormat(String value) {
        return new JAXBElement<String>(_OutputFormat_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LanguageStringType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "Abstract")
    public JAXBElement<LanguageStringType> createAbstract(LanguageStringType value) {
        return new JAXBElement<LanguageStringType>(_Abstract_QNAME, LanguageStringType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetadataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "Metadata")
    public JAXBElement<MetadataType> createMetadata(MetadataType value) {
        return new JAXBElement<MetadataType>(_Metadata_QNAME, MetadataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponsiblePartyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "PointOfContact")
    public JAXBElement<ResponsiblePartyType> createPointOfContact(ResponsiblePartyType value) {
        return new JAXBElement<ResponsiblePartyType>(_PointOfContact_QNAME, ResponsiblePartyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "Identifier")
    public JAXBElement<CodeType> createIdentifier(CodeType value) {
        return new JAXBElement<CodeType>(_Identifier_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "AbstractMetaData")
    public JAXBElement<Object> createAbstractMetaData(Object value) {
        return new JAXBElement<Object>(_AbstractMetaData_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "OrganisationName")
    public JAXBElement<String> createOrganisationName(String value) {
        return new JAXBElement<String>(_OrganisationName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WGS84BoundingBoxType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "WGS84BoundingBox", substitutionHeadNamespace = "http://www.opengis.net/ows/2.0", substitutionHeadName = "BoundingBox")
    public JAXBElement<WGS84BoundingBoxType> createWGS84BoundingBox(WGS84BoundingBoxType value) {
        return new JAXBElement<WGS84BoundingBoxType>(_WGS84BoundingBox_QNAME, WGS84BoundingBoxType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BoundingBoxType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "BoundingBox")
    public JAXBElement<BoundingBoxType> createBoundingBox(BoundingBoxType value) {
        return new JAXBElement<BoundingBoxType>(_BoundingBox_QNAME, BoundingBoxType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "AccessConstraints")
    public JAXBElement<String> createAccessConstraints(String value) {
        return new JAXBElement<String>(_AccessConstraints_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.opengis.net/ows/2.0", name = "PositionName")
    public JAXBElement<String> createPositionName(String value) {
        return new JAXBElement<String>(_PositionName_QNAME, String.class, null, value);
    }

}
