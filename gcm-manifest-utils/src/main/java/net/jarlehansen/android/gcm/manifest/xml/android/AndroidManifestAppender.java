package net.jarlehansen.android.gcm.manifest.xml.android;

import net.jarlehansen.android.gcm.manifest.xml.android.parts.XmlKeys;
import net.jarlehansen.android.gcm.manifest.xml.android.parts.XmlParts;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 9:14 AM
 */
public class AndroidManifestAppender implements AndroidAppender {
    private final File inputFile;
    private final File outputFile;
    private final String packageName;

    public AndroidManifestAppender(File inputFile, File outputFile, String packageName) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.packageName = packageName;
    }

    @Override
    public void append(XmlParts xmlParts) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(inputFile);
            Element manifestElement = document.getDocumentElement();

            createReceiver(xmlParts, document);
            createService(xmlParts, document);

            createUsesPermission(xmlParts, document, manifestElement, XmlKeys.USES_PERMISSION_5);
            createUsesPermission(xmlParts, document, manifestElement, XmlKeys.USES_PERMISSION_4);
            createUsesPermission(xmlParts, document, manifestElement, XmlKeys.USES_PERMISSION_3);
            createUsesPermission(xmlParts, document, manifestElement, XmlKeys.USES_PERMISSION_2);
            createUsesPermission(xmlParts, document, manifestElement, packageName + XmlKeys.USES_PERMISSION_1);

            createPermission(xmlParts, document, manifestElement);
            createUsesSdk(xmlParts, document, manifestElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(outputFile);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            throwXmlException(e);
        } catch (SAXException e) {
            throwXmlException(e);
        } catch (IOException e) {
            throwXmlException(e);
        } catch (TransformerConfigurationException e) {
            throwXmlException(e);
        } catch (TransformerException e) {
            throwXmlException(e);
        }
    }

    private void createUsesSdk(XmlParts xmlParts, Document document, Element manifestElement) {
        if (!xmlParts.containsUsesSdk()) {
            Element usesSdk = document.createElement("uses-sdk");
            usesSdk.setAttribute("android:minSdkVersion", XmlKeys.USES_SDK);
            manifestElement.insertBefore(usesSdk, manifestElement.getFirstChild());
        }
    }

    private void createPermission(XmlParts xmlParts, Document document, Element manifestElement) {
        if (!xmlParts.containsPermission()) {
            Element permission = document.createElement("permission");
            permission.setAttribute("android:name", packageName + XmlKeys.PERMISSION);
            permission.setAttribute("android:protectionLevel", "signature");
            manifestElement.insertBefore(permission, manifestElement.getFirstChild());
        }
    }

    private void createUsesPermission(XmlParts xmlParts, Document document, Element manifestElement, String permission) {
        if (!xmlParts.containsUsesPermission5()) {
            Element usesPermission5 = document.createElement("uses-permission");
            usesPermission5.setAttribute("android:name", permission);
            manifestElement.insertBefore(usesPermission5, manifestElement.getFirstChild());
        }
    }

    private void createService(XmlParts xmlParts, Document document) {
        if (!xmlParts.containsService()) {
            Element service = document.createElement("service");
            service.setAttribute("android:name", XmlKeys.SERVICE);

            NodeList tags = document.getElementsByTagName("application");
            Node application = tags.item(0);
            application.insertBefore(service, application.getFirstChild());
        }
    }

    private void createReceiver(XmlParts xmlParts, Document document) {
        if (!xmlParts.containsReceiver()) {
            Element receiver = document.createElement("receiver");
            receiver.setAttribute("android:name", XmlKeys.RECEIVER);
            receiver.setAttribute("android:permission", XmlKeys.RECEIVER_PERMISSION);

            Element intentFilter = document.createElement("intent-filter");
            receiver.appendChild(intentFilter);

            Element action1 = document.createElement("action");
            action1.setAttribute("android:name", XmlKeys.RECEIVER_ACTION1);
            intentFilter.appendChild(action1);

            Element action2 = document.createElement("action");
            action2.setAttribute("android:name", XmlKeys.RECEIVER_ACTION2);
            intentFilter.appendChild(action2);

            Element category = document.createElement("category");
            category.setAttribute("android:name", packageName);
            intentFilter.appendChild(category);

            NodeList tags = document.getElementsByTagName("application");
            Node application = tags.item(0);
            application.insertBefore(receiver, application.getFirstChild());
        }
    }

    private void throwXmlException(Throwable t) {
        throw new IllegalStateException("Unable to write the xml file: " + outputFile.getAbsolutePath(), t);
    }
}
