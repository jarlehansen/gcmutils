package net.jarlehansen.android.gcm.client;

import android.os.AsyncTask;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import net.jarlehansen.android.gcm.client.sender.GCMSenderCallback;
import net.jarlehansen.android.gcm.client.sender.GCMSenderImpl;
import net.jarlehansen.android.gcm.client.sender.GCMSenderResponse;
import org.apache.http.message.BasicNameValuePair;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 11:19 AM
 */
@RunWith(RobolectricTestRunner.class)
public class GCMUtilsIntegrationTest extends AbstractTestSetup {
    private static Server server;

    @BeforeClass
    public static void setUp() {
        server = new Server(10245);
        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String s, Request baseRequest, HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException {
                response.setStatus(HttpServletResponse.SC_OK);
                baseRequest.setHandled(true);
            }
        });
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (!server.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @AfterClass
    public static void tearDown() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void send() {
        GCMSenderCallback mock = mock(GCMSenderCallback.class);
        GCMUtils.createRegSender("http://localhost:10245", "my regid").setCallback(mock).send();

        verify(mock).onRequestSent(any(GCMSenderResponse.class));
    }

    @Test
    public void doInBackground() throws ExecutionException, InterruptedException {
        List<BasicNameValuePair> requestParams = new ArrayList<BasicNameValuePair>();
        requestParams.add(new BasicNameValuePair("regId", "test-value"));

        GCMSenderImpl regIdSender = new GCMSenderImpl("http://localhost:10245", requestParams);
        AsyncTask<Void, Void, GCMSenderResponse> response = regIdSender.execute(null, null);
        GCMSenderResponse GCMSenderResponse = response.get();

        assertTrue(GCMSenderResponse.ok());
        assertEquals(200, GCMSenderResponse.getCode());
        assertEquals("OK", GCMSenderResponse.getMessage());
    }

    @Test
    public void send_connectionRefused() throws ExecutionException, InterruptedException {
        List<BasicNameValuePair> requestParams = new ArrayList<BasicNameValuePair>();
        requestParams.add(new BasicNameValuePair("regId", "test-value"));

        GCMSenderImpl regIdSender = new GCMSenderImpl("http://localhost:8080", requestParams);
        regIdSender.setRetries(1);
        regIdSender.setBackoffMillis(100);

        AsyncTask<Void, Void, GCMSenderResponse> response = regIdSender.execute(null, null);
        GCMSenderResponse GCMSenderResponse = response.get();

        assertFalse(GCMSenderResponse.ok());
        assertEquals(-1, GCMSenderResponse.getCode());
        assertNotNull(GCMSenderResponse.getThrowable());
    }
}
