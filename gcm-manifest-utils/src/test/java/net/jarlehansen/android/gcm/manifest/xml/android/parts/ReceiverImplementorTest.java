package net.jarlehansen.android.gcm.manifest.xml.android.parts;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.model.Application;
import net.jarlehansen.android.gcm.manifest.xml.android.model.Receiver;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 1:32 PM
 */
public class ReceiverImplementorTest {
    private ReceiverImplementor implementor;
    private AndroidManifest manifest;
    private XmlParts xmlParts;

    @Before
    public void setUp() {
        manifest = new AndroidManifest();
        xmlParts = new XmlParts();
        implementor = new ReceiverImplementor();
    }

    @Test
    public void populateXml_nullInput() {
        implementor.populateXmlParts(xmlParts, manifest);

        assertFalse(xmlParts.containsReceiver());
    }

    @Test
    public void populateXml_emptyReceiver() {
        Receiver receiver = new Receiver();

        Application application = new Application();
        application.addReceiver(receiver);

        manifest.setApplication(application);

        implementor.populateXmlParts(xmlParts, manifest);

        assertFalse(xmlParts.containsReceiver());
    }

    @Test
    public void populateXml_existingReceiver() {
        Receiver receiver = new Receiver();
        receiver.setName(XmlKeys.RECEIVER);

        Application application = new Application();
        application.addReceiver(receiver);

        manifest.setApplication(application);

        implementor.populateXmlParts(xmlParts, manifest);

        assertTrue(xmlParts.containsReceiver());
    }
}
