package net.jarlehansen.android.gcm.client;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import net.jarlehansen.android.gcm.client.sender.GCMSender;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 8:57 AM
 */
@RunWith(RobolectricTestRunner.class)
public class GCMUtilsTest extends AbstractTestSetup {

    @Test
    public void createRegSender() {
        GCMSender sender = GCMUtils.createRegSender("http://localhost:8080", "test-regid");
        assertNotNull(sender);
    }

    @Test
    public void createRegSender_withContext() throws IOException {
        super.openTestFile();

        try {
            GCMSender sender = GCMUtils.createRegSender(context, "test-regid");
            assertNotNull(sender);
        } finally {
            super.closeTestFile();
        }
    }

    @Test
    public void createUnregSender() {
        GCMSender sender = GCMUtils.createUnregSender("http://localhost:8080", "test-regid");
        assertNotNull(sender);
    }

    @Test
    public void createSender_list() {
        GCMSender sender = GCMUtils.createSender("http://localhost:8080", new BasicNameValuePair("", ""));
        assertNotNull(sender);
    }

    @Test
    public void isCheckExtendedEnabled() {
        assertTrue(GCMUtils.isCheckExtendedEnabled("enabled", 2, 2));
    }

    @Test
    public void isCheckExtendedEnabled_disabled() {
        assertFalse(GCMUtils.isCheckExtendedEnabled("disabled", 2, 2));
    }

    @Test
    public void isCheckExtendedEnabled_debuggable() {
        assertTrue(GCMUtils.isCheckExtendedEnabled("", 2, 2));
    }

    @Test
    public void isCheckExtendedEnabled_notDebuggable() {
        assertFalse(GCMUtils.isCheckExtendedEnabled("", 0, 2));
    }

    @Test
    public void isCheckExtendedEnabled_zeroFlags() {
        assertFalse(GCMUtils.isCheckExtendedEnabled("", 2, 0));
    }

    @Test
    public void isCheckExtendedEnabled_bothFlagsDisabled() {
        assertFalse(GCMUtils.isCheckExtendedEnabled("", 0, 0));
    }
}
