package net.jarlehansen.android.gcm.client;

import android.content.Context;
import android.content.res.AssetManager;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import net.jarlehansen.android.gcm.GCMUtilsConstants;
import net.jarlehansen.android.gcm.client.properties.GCMUtilsProperties;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/27/13
 * Time: 1:12 PM
 */
@RunWith(RobolectricTestRunner.class)
public class GCMUtilityTest extends AbstractTestSetup {

    @After
    public void tearDown() {
        GCMUtilsProperties.GCMUTILS.reset();
    }

    @Test
    public void getProperty_invalidKey() throws IOException {
        try {
            super.openTestFile();
            String value = GCMUtility.getProperty("asd", context);
            assertEquals("", value);
        } finally {
            super.closeTestFile();
        }
    }

    @Test
    public void getProperty_senderId() throws IOException {
        try {
            super.openTestFile();
            String value = GCMUtility.getProperty(GCMUtilsConstants.PROPS_KEY_SENDERID, context);
            assertEquals("test-project", value);
        } finally {
            super.closeTestFile();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getProperty_invalidFile() throws IOException {
        AssetManager am = mock(AssetManager.class);
        Context c = mock(Context.class);
        when(c.getAssets()).thenReturn(am);
        when(am.open("gcmutils.properties")).thenThrow(new IOException("test exception"));

        GCMUtility.getProperty(GCMUtilsConstants.PROPS_KEY_SENDERID, c);
    }

    @Test
    public void getReceiverUrl() throws IOException {
        try {
            super.openTestFile();
            String receiverUrl = GCMUtility.getReceiverUrl(context);
            assertEquals("http://localhost", receiverUrl);
        } finally {
            super.closeTestFile();
        }
    }

    @Test
    public void getSenderId() throws IOException {
        try {
            super.openTestFile();
            String receiverUrl = GCMUtility.getSenderId(context);
            assertEquals("test-project", receiverUrl);
        } finally {
            super.closeTestFile();
        }
    }
}
