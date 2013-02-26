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
    private static final Map<String, String> connectedDevices = new ConcurrentHashMap<String, String>();

    private final String apiKey;

    public GCMUtilsServlet(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String regId = request.getParameter(GCMUtilsConstants.PARAM_KEY_REGID);
        String unregId = request.getParameter(GCMUtilsConstants.PARAM_KEY_UNREGID);

        if (regId != null && !"".equals(regId)) {
            if (!connectedDevices.containsKey(regId)) {
                String userAgent = request.getHeader("User-Agent");
                connectedDevices.put(regId, userAgent);
                logger.info("Registration id stored: {}", regId);
            }
        } else if (unregId != null && !"".equals(unregId)) {
            if (connectedDevices.containsKey(unregId)) {
                connectedDevices.remove(unregId);
                logger.info("Registration id deleted: {}", unregId);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String msg = request.getParameter(GCMUtilsConstants.DATA_KEY_MSG);
        if (msg != null && !"".equals(msg) && connectedDevices.size() > 0) {
            Sender sender = new Sender(apiKey);
            Message message = new Message.Builder().addData(GCMUtilsConstants.DATA_KEY_MSG, msg).build();
            Set<String> regIds = connectedDevices.keySet();
            MulticastResult result = sender.send(message, new ArrayList<String>(regIds), 0);
            logger.info("Message sent: '{}'", msg);
            logger.info(result.toString());
        }

        StringBuilder sb = new StringBuilder();
        if (connectedDevices.size() > 0) {
            sb.append("<html><body>");
            sb.append("<table border='1'><tr><th>User-Agent</th><th>Registration id</th></tr>");
            for (Map.Entry<String, String> entry : connectedDevices.entrySet()) {
                sb.append("<tr><td>").append(entry.getValue()).append("</td>");
                sb.append("<td><small>").append(entry.getKey()).append("</small></td></tr>");
            }
            sb.append("</table>");
        } else {
            sb.append("<html><head><meta http-equiv='refresh' content='3'/></head><body>");
            sb.append("<p><strong>No devices connected</strong></p>");
        }

        sb.append("&nbsp;<form action='.' method='GET'>");
        sb.append("<input type='text' name='msg' value='").append((msg == null) ? "" : msg).append("'/>");
        sb.append("<input type='submit' value='Send message'/>");
        sb.append("</form></body></html>");

        PrintWriter writer = response.getWriter();
        writer.println(sb.toString());
    }
}
