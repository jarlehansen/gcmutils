package net.jarlehansen.android.gcm.manifest.xml.android.parts;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.model.Application;
import net.jarlehansen.android.gcm.manifest.xml.android.model.Receiver;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 1:25 PM
 */
public class ReceiverImplementor implements XmlImplementor {

    @Override
    public void populateXmlParts(XmlParts xmlParts, AndroidManifest androidManifest) {
        Application application = androidManifest.getApplication();
        if (application == null) {
            application = new Application();
            androidManifest.setApplication(application);
        }

        List<Receiver> receivers = application.getReceivers();
        if (receivers == null)
            receivers = new ArrayList<Receiver>();

        Receiver receiver = getReceiver(receivers);
        xmlParts.setReceiver((receiver != null));
    }

    Receiver getReceiver(List<Receiver> receivers) {
        for (Receiver receiver : receivers) {
            if (receiver != null && XmlKeys.RECEIVER.equals(receiver.getName()))
                return receiver;
        }

        return null;
    }
}
