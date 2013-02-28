package net.jarlehansen.android.gcm.manifest.xml.android.parts;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.model.Application;
import net.jarlehansen.android.gcm.manifest.xml.android.model.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 1:01 PM
 */
public class ServiceImplementor implements XmlImplementor {

    public void populateXmlParts(XmlParts xmlParts, AndroidManifest androidManifest) {
        Application application = androidManifest.getApplication();
        if (application == null)
            application = new Application();

        List<Service> services = application.getServices();
        if (services == null)
            services = new ArrayList<Service>();

        boolean containsService = false;
        for (Service service : services) {
            if (service != null && service.getName() != null && service.getName().endsWith(XmlKeys.SERVICE)) {
                containsService = true;
                break;
            }
        }

        if (containsService)
            xmlParts.setService(containsService);
    }
}
