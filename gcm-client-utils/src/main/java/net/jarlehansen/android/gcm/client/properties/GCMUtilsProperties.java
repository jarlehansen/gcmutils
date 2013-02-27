package net.jarlehansen.android.gcm.client.properties;

import android.content.Context;
import android.content.res.AssetManager;
import net.jarlehansen.android.gcm.GCMUtilsConstants;
import net.jarlehansen.android.gcm.client.log.GCMUtilsLog;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/18/13
 * Time: 7:32 AM
 */
public enum GCMUtilsProperties {
    GCMUTILS("gcmutils.properties");

    private final String fileName;
    private Properties properties = null;

    private GCMUtilsProperties(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    // For testing
    public void reset() {
        properties = null;
    }

    public boolean init(Context context) {
        if (properties == null) {
            AssetManager assetManager = context.getAssets();
            try {
                InputStream inputStream = assetManager.open(fileName);
                properties = new Properties();
                properties.load(inputStream);
                GCMUtilsLog.i("Loaded file '", fileName, "'");
                return true;
            } catch (IOException io) {
                GCMUtilsLog.i("Unable to open '" + fileName + "'", io);
                return false;
            }
        } else
            return true;
    }

    public String get(String key) {
        if (properties == null)
            return "";
        else {
            String value = properties.getProperty(key);
            GCMUtilsLog.d("Loaded property ", key, "=", value);
            return value == null ? "" : value;
        }
    }

    public String getReceiverUrl() {
        return get(GCMUtilsConstants.PROPS_KEY_RECEIVERURL);
    }

    public String getSenderId() {
        return get(GCMUtilsConstants.PROPS_KEY_SENDERID);
    }
}
