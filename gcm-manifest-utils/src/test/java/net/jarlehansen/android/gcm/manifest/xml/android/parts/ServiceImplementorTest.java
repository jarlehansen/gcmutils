package net.jarlehansen.android.gcm.manifest.xml.android.parts;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.model.Application;
import net.jarlehansen.android.gcm.manifest.xml.android.model.Service;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 1:01 PM
 */
public class ServiceImplementorTest {
    private ServiceImplementor implementor;
    private AndroidManifest manifest;
    private XmlParts xmlParts;

    @Before
    public void setUp() {
        manifest = new AndroidManifest();
        xmlParts = new XmlParts();
        implementor = new ServiceImplementor();
    }

    @Test
    public void populateXml_nullApplicationAndService() {
        implementor.populateXmlParts(xmlParts, manifest);

        assertFalse(xmlParts.containsService());
    }

    @Test
    public void populateXml_nullService() {
        Application application = new Application();

        manifest.setApplication(application);

        implementor.populateXmlParts(xmlParts, manifest);

        assertFalse(xmlParts.containsService());
    }

    @Test
    public void populateXml_wrongServiceExists() {
        Application application = new Application();
        application.addService(new Service("test-service"));

        manifest.setApplication(application);

        implementor.populateXmlParts(xmlParts, manifest);

        assertFalse(xmlParts.containsService());
    }

    @Test
    public void populateXml_serviceExist() {
        Application application = new Application();
        application.addService(new Service("test-service"));
        application.addService(new Service(XmlKeys.SERVICE));

        manifest.setApplication(application);

        implementor.populateXmlParts(xmlParts, manifest);

        assertTrue(xmlParts.containsService());
    }
}
