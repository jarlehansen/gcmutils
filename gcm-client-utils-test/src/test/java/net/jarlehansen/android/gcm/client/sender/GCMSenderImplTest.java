package net.jarlehansen.android.gcm.client.sender;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import net.jarlehansen.android.gcm.GCMUtilsConstants;
import net.jarlehansen.android.gcm.client.AbstractTestSetup;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 10:25 AM
 */
@RunWith(RobolectricTestRunner.class)
public class GCMSenderImplTest extends AbstractTestSetup {

    @Test(expected = IllegalArgumentException.class)
    public void send_invalidReceiverUrl() {
        new GCMSenderImpl("invalid url", new ArrayList<BasicNameValuePair>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void send_noArguments() {
        GCMSender sender = new GCMSenderImpl("http://localhost", new ArrayList<BasicNameValuePair>());
        sender.send();
    }

    @Test
    public void buildUrl() {
        List<BasicNameValuePair> requestParams = new ArrayList<BasicNameValuePair>();
        requestParams.add(new BasicNameValuePair("regId", "my-test-regid"));
        requestParams.add(new BasicNameValuePair("someParam", "someValue"));

        GCMSenderImpl sender = new GCMSenderImpl("http://localhost", requestParams);
        assertEquals("regId=my-test-regid&someParam=someValue", sender.createRequestBody());
    }

    @Test
    public void buildRegUrl() {
        List<BasicNameValuePair> requestParams = new ArrayList<BasicNameValuePair>();
        requestParams.add(new BasicNameValuePair(GCMUtilsConstants.PARAM_KEY_REGID, "my-test-regid"));

        GCMSenderImpl sender = new GCMSenderImpl("http://localhost", requestParams);
        assertEquals("regId=my-test-regid", sender.createRequestBody());
    }

    @Test
    public void buildUnregUrl() {
        List<BasicNameValuePair> requestParams = new ArrayList<BasicNameValuePair>();
        requestParams.add(new BasicNameValuePair(GCMUtilsConstants.PARAM_KEY_UNREGID, "my-test-regid"));

        GCMSenderImpl sender = new GCMSenderImpl("http://localhost", requestParams);
        assertEquals("unregId=my-test-regid", sender.createRequestBody());
    }

}
