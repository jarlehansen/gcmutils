package net.jarlehansen.android.gcm.client;

import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 10:39 AM
 */
@RunWith(RobolectricTestRunner.class)
public class GCMUtilsVerifierTest {

    @Test
    public void checkService() {
        PackageInfo packageInfo = new PackageInfo();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.name = GCMIntentService.class.getName();
        packageInfo.services = new ServiceInfo[]{serviceInfo};
        GCMUtilsVerifier.checkService(GCMIntentService.class.getPackage().getName(), packageInfo);
    }

    @Test(expected = IllegalStateException.class)
    public void checkService_nullServices() {
        GCMUtilsVerifier.checkService("invalid.package", new PackageInfo());
    }

    @Test(expected = IllegalStateException.class)
    public void checkService_noServices() {
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.services = new ServiceInfo[]{new ServiceInfo()};
        GCMUtilsVerifier.checkService("invalid.package", packageInfo);
    }

    @Test(expected = IllegalStateException.class)
    public void checkService_noGCMService() {
        PackageInfo packageInfo = new PackageInfo();
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.name = "MyService";
        packageInfo.services = new ServiceInfo[]{serviceInfo};
        GCMUtilsVerifier.checkService("invalid.package", packageInfo);
    }

    @Test
    public void checkServiceClass() {
        GCMUtilsVerifier.checkServiceClass(GCMIntentService.class.getName());
    }

    @Test(expected = IllegalStateException.class)
    public void checkServiceClass_invalidType() {
        GCMUtilsVerifier.checkServiceClass(String.class.getName());
    }
}
