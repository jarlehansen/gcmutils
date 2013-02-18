package net.jarlehansen.android.gcm.client.log;

import android.util.Log;

import static net.jarlehansen.android.gcm.GCMUtilsConstants.TAG;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/18/13
 * Time: 12:33 PM
 */
public class GCMUtilsLog {
    private static final boolean DEBUG_ENABLED = Log.isLoggable(TAG, Log.DEBUG);
    private static final boolean INFO_ENABLED = Log.isLoggable(TAG, Log.INFO);
    private static final boolean WARN_ENABLED = Log.isLoggable(TAG, Log.WARN);
    private static final boolean ERROR_ENABLED = Log.isLoggable(TAG, Log.ERROR);

    public static void d(String msg) {
        if (DEBUG_ENABLED)
            Log.d(TAG, msg);
    }

    public static void d(String... msgs) {
        if (DEBUG_ENABLED) {
            StringBuilder sb = new StringBuilder();
            for (String msg : msgs)
                sb.append(msg);

            Log.d(TAG, sb.toString());
        }
    }

    public static void i(String msg) {
        if (INFO_ENABLED)
            Log.i(TAG, msg);
    }

    public static void i(String... msgs) {
        if (INFO_ENABLED) {
            StringBuilder sb = new StringBuilder();
            for (String msg : msgs)
                sb.append(msg);

            Log.i(TAG, sb.toString());
        }
    }

    public static void i(String msg, Throwable t) {
        if (INFO_ENABLED)
            Log.i(TAG, msg, t);
    }

    public static void w(String msg) {
        if (WARN_ENABLED)
            Log.w(TAG, msg);
    }

    public static void w(String msg, Throwable t) {
        if (WARN_ENABLED)
            Log.w(TAG, msg, t);
    }

    public static void e(String msg) {
        if (ERROR_ENABLED)
            Log.e(TAG, msg);
    }

    public static void e(String... msgs) {
        if (ERROR_ENABLED) {
            StringBuilder sb = new StringBuilder();
            for (String msg : msgs)
                sb.append(msg);

            Log.e(TAG, sb.toString());
        }
    }

    public static void e(String msg, Throwable t) {
        if (ERROR_ENABLED)
            Log.e(TAG, msg, t);
    }
}
