package net.jarlehansen.android.gcm.client;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import net.jarlehansen.android.gcm.client.log.GCMUtilsLog;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 11:16 AM
 */
enum GCMUtilsVerifier {
    ;

    private static final String NO_GCM_SERVICE = "No GCMIntentService is configured";
    private static final String INVALID_CONFIG_GCM_SERVICE = "The GCMIntentService is not configured correctly";

    static void checkExtended(Context context) {
        GCMRegistrar.checkManifest(context);

        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SERVICES);
            checkService(context.getPackageName(), packageInfo);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(NO_GCM_SERVICE);
        }
    }

    static void checkService(String packageName, PackageInfo packageInfo) {
        ServiceInfo[] services = packageInfo.services;
        String serviceName = packageName + ".GCMIntentService";
        boolean includesGCMService = false;

        if (services != null && services.length > 0) {
            for (ServiceInfo serviceInfo : services) {
                if (serviceInfo != null && serviceInfo.name != null && serviceInfo.name.equals(serviceName)) {
                    checkServiceClass(serviceName);
                    includesGCMService = true;
                    break;
                }
            }
        }

        if (!includesGCMService)
            throw new IllegalStateException(NO_GCM_SERVICE);
    }

    static void checkServiceClass(String serviceName) {
        try {
            Object o = Class.forName(serviceName).newInstance();
            if (o instanceof GCMBaseIntentService)
                GCMUtilsLog.i("Found GCMIntentService: ", serviceName);
            else
                throw new IllegalStateException(o.getClass().getName() + " is not extending " + GCMBaseIntentService.class.getName());
        } catch (InstantiationException e) {
            throw new IllegalStateException(INVALID_CONFIG_GCM_SERVICE);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(INVALID_CONFIG_GCM_SERVICE);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(INVALID_CONFIG_GCM_SERVICE);
        }
    }
}
