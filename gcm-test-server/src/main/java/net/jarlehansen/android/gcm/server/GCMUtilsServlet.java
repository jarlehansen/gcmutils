package net.jarlehansen.android.gcm.server;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import net.jarlehansen.android.gcm.GCMUtilsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/26/13
 * Time: 9:48 AM
 */
public class GCMUtilsServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(GCMUtilsServlet.class);
    private static final String PARAM_DELETE = "delete";
    private static final ConcurrentHashMap<String, String> connectedDevices = new ConcurrentHashMap<String, String>();

    private final String apiKey;

    public GCMUtilsServlet(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        logInputParams(request);
        String email = request.getParameter(GCMUtilsConstants.PARAM_KEY_EMAIL);
        String regId = request.getParameter(GCMUtilsConstants.PARAM_KEY_REGID);
        String unregId = request.getParameter(GCMUtilsConstants.PARAM_KEY_UNREGID);

        if (regId != null && !"".equals(regId)) {
            StringBuilder key = new StringBuilder();
            if (email != null && !"".equals(email))
                key.append("<strong>E-mail:</strong> ").append(email).append("<br/>");

            key.append("<strong>User-Agent:</strong> ").append(request.getHeader("User-Agent"));
            connectedDevices.putIfAbsent(regId, key.toString());
            logger.info("Registration id stored: {}", regId);
        } else if (unregId != null && !"".equals(unregId)) {
            if (connectedDevices.containsKey(unregId)) {
                connectedDevices.remove(unregId);
                logger.info("Registration id deleted: {}", unregId);
            }
        }
    }

    private void logInputParams(HttpServletRequest request) {
        Enumeration<String> params = request.getParameterNames();
        StringBuilder sb = new StringBuilder();
        while (params.hasMoreElements()) {
            sb.append(params.nextElement());
            if (params.hasMoreElements())
                sb.append(", ");
        }

        logger.info("POST input params: {}", sb.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String delete = request.getParameter(PARAM_DELETE);
        if (connectedDevices.size() > 0 && "true".equals(delete))
            connectedDevices.clear();

        StringBuilder sb = new StringBuilder();
        sb.append(connectedDevicesTable());

        String msg = request.getParameter(GCMUtilsConstants.DATA_KEY_MSG);
        sb.append(messageSentText(msg));
        sb.append(sendMessageForm(msg));
        sb.append(deleteRegisteredDevicesForm());

        PrintWriter writer = response.getWriter();
        writer.println(sb.toString());
    }

    private String connectedDevicesTable() {
        StringBuilder sb = new StringBuilder();
        if (connectedDevices.size() > 0) {
            sb.append("<!DOCTYPE html><html><body>");
            sb.append("<table border='1'><tr><th>Client</th><th>Registration id</th></tr>");
            for (Map.Entry<String, String> entry : connectedDevices.entrySet()) {
                sb.append("<tr><td>").append(entry.getValue()).append("</td>");
                sb.append("<td><small>").append(entry.getKey()).append("</small></td></tr>");
            }
            sb.append("</table>");
        } else {
            sb.append("<html><head><meta http-equiv='refresh' content='3;.'/></head><body>");
            sb.append("<p><strong>No devices connected</strong></p>");
        }

        return sb.toString();
    }

    private String messageSentText(String msg) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (msg != null && !"".equals(msg) && connectedDevices.size() > 0) {
            Sender sender = new Sender(apiKey);
            Message message = new Message.Builder().addData(GCMUtilsConstants.DATA_KEY_MSG, msg).build();
            Set<String> regIds = connectedDevices.keySet();
            MulticastResult result = sender.send(message, new ArrayList<String>(regIds), 0);
            logger.info("Message sent: '{}'", msg);
            logger.info(result.toString());

            if (result.getFailure() == 0)
                sb.append("<p><strong>- Message sent:</strong> ").append(msg).append("</p>");
            else
                sb.append("<p>Problem sending message: ").append(result.toString()).append("</p>");
        }

        return sb.toString();
    }

    private String sendMessageForm(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("&nbsp;<form action='.' method='GET'>");
        sb.append("<input type='text' name='msg' value='").append((msg == null) ? "" : msg).append("'/>");
        sb.append("<input type='submit' value='Send message'/>");
        sb.append("</form>");
        return sb.toString();
    }

    private String deleteRegisteredDevicesForm() {
        StringBuilder sb = new StringBuilder();
        if (connectedDevices.size() > 0) {
            sb.append("&nbsp;<form action='.' method='GET'>");
            sb.append("<input type='hidden' name='delete' value='true'/>");
            sb.append("<input type='submit' value='Remove registered devices'/>");
            sb.append("</form></body></html>");
        }
        return sb.toString();
    }
}
