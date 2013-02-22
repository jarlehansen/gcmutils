package net.jarlehansen.android.gcm.manifest.xml.android.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 8:33 AM
 */
@Root(strict = false)
@Namespace(prefix = "android")
public class Application {

    @ElementList(entry = "service", inline = true, required = false)
    private List<Service> services = null;

    @ElementList(entry = "receiver", inline = true, required = false)
    private List<Receiver> receivers = null;

    public List<Service> getServices() {
        return services;
    }

    public void addService(Service service) {
        if (services == null)
            services = new ArrayList<Service>();

        services.add(service);
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Receiver> getReceivers() {
        return receivers;
    }

    public void addReceiver(Receiver receiver) {
        if (receivers == null)
            receivers = new ArrayList<Receiver>();

        receivers.add(receiver);
    }

    public void setReceivers(List<Receiver> receivers) {
        this.receivers = receivers;
    }

    @Override
    public String toString() {
        return "Application{" +
                "services=" + services +
                ", receivers=" + receivers +
                '}';
    }
}
