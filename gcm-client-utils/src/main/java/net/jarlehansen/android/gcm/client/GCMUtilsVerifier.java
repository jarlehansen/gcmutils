package net.jarlehansen.android.gcm.client;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import net.jarlehansen.android.gcm.GCMUtilsConstants;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 11:16 AM
 */
enum GCMUtilsVerifier {
    ;

    static void checkExtended(Context context) {
        GCMRegistrar.checkManifest(context);

        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SERVICES);
            checkService(context.getPackageName(), packageInfo);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("No GCMIntentService is configured");
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
            throw new IllegalStateException("No GCMIntentService is configured");
    }

    static void checkServiceClass(String serviceName) {
        try {
            Object o = Class.forName(serviceName).newInstance();
            if (o instanceof GCMBaseIntentService)
                Log.i(GCMUtilsConstants.TAG, "Found GCMIntentService: " + serviceName);
            else
                throw new IllegalStateException(o.getClass().getName() + " is not extending " + GCMBaseIntentService.class.getName());
        } catch (InstantiationException e) {
            throw new IllegalStateException("The GCMIntentService is not configured correctly");
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("The GCMIntentService is not configured correctly");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("The GCMIntentService is not configured correctly");
        }
    }
}
