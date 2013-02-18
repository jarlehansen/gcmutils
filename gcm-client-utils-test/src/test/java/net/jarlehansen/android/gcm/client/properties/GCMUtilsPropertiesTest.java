package net.jarlehansen.android.gcm.client.properties;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import net.jarlehansen.android.gcm.GCMUtilsConstants;
import net.jarlehansen.android.gcm.client.AbstractTestSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/18/13
 * Time: 7:43 AM
 */
@RunWith(RobolectricTestRunner.class)
public class GCMUtilsPropertiesTest extends AbstractTestSetup {

    @Before
    public void setUp() throws IOException {
        super.openTestFile();
        GCMUtilsProperties.GCMUTILS.reset();
    }

    @After
    public void tearDown() throws IOException {
        super.closeTestFile();
    }

    @Test
    public void init() {
        boolean initialized = GCMUtilsProperties.GCMUTILS.init(context);
        assertTrue(initialized);
    }

    @Test
    public void init_exceptionThrown() throws IOException {
        when(assetManager.open("gcmutils.properties")).thenThrow(new IOException("test exception"));

        boolean initialized = GCMUtilsProperties.GCMUTILS.init(context);
        assertFalse(initialized);
    }

    @Test
    public void init_alreadyInitialized() {
        GCMUtilsProperties.GCMUTILS.init(context);
        boolean initialized = GCMUtilsProperties.GCMUTILS.init(context);
        assertTrue(initialized);
    }

    @Test
    public void get() {
        GCMUtilsProperties.GCMUTILS.init(context);

        String receiverUrl = GCMUtilsProperties.GCMUTILS.get(GCMUtilsConstants.PROPS_KEY_RECEIVERURL);
        String senderId = GCMUtilsProperties.GCMUTILS.get(GCMUtilsConstants.PROPS_KEY_SENDERID);
        String backoffMillis = GCMUtilsProperties.GCMUTILS.get(GCMUtilsConstants.PROPS_KEY_BACKOFF);
        String numOfRetries = GCMUtilsProperties.GCMUTILS.get(GCMUtilsConstants.PROPS_KEY_RETRIES);

        assertEquals("http://localhost", receiverUrl);
        assertEquals("test-project", senderId);
        assertEquals("2000", backoffMillis);
        assertEquals("5", numOfRetries);
    }

    @Test
    public void get_invalidKey() {
        String value = GCMUtilsProperties.GCMUTILS.get("invalid key");
        assertEquals("", value);
    }
}
