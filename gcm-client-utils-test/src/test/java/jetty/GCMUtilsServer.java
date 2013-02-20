package jetty;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import net.jarlehansen.android.gcm.GCMUtilsConstants;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/13/13
 * Time: 2:59 PM
 */
public class GCMUtilsServer {
    private static final Logger logger = LoggerFactory.getLogger(GCMUtilsServer.class);

    private final String apiKey;
    private String regId = "";

    private GCMUtilsServer(String apiKey) {
        this.apiKey = apiKey;
    }

    private void startServer() throws Exception {
        Server server = new Server(10246);

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new HttpServlet() {

            // Send messages: http://localhost:10246/?msg=
            @Override
            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                String msg = request.getParameter("msg");
                if (regId != null && !"".equals(regId) && msg != null && !"".equals(msg)) {
                    send(msg);
                }
            }

            @Override
            protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
                StringBuilder sb = new StringBuilder();
                Enumeration<String> parameters = request.getParameterNames();
                while (parameters.hasMoreElements()) {
                    sb.append(parameters.nextElement());

                    if (parameters.hasMoreElements())
                        sb.append(", ");
                }
                logger.info("Request parameter names: {}", sb.toString());

                String regIdParam = request.getParameter(GCMUtilsConstants.PARAM_KEY_REGID);
                String unregIdParam = request.getParameter(GCMUtilsConstants.PARAM_KEY_UNREGID);
                if (regIdParam != null) {
                    logger.info("Received registration id: {}", regIdParam);
                    regId = regIdParam;
                    send("Test message from " + GCMUtilsServer.class.getSimpleName());
                } else if (unregIdParam != null) {
                    logger.info("Received unregistration id: {}", unregIdParam);
                    regId = "";
                }
            }
        }), "/*");

        server.start();
        server.join();
    }

    private void send(String msg) throws IOException {
        Sender sender = new Sender(apiKey);
        Message message = new Message.Builder().addData(GCMUtilsConstants.DATA_KEY_MSG, msg).build();
        Result result = sender.send(message, regId, 0);
        logger.info("Push message result: {}", result);
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1)
            new GCMUtilsServer(args[0]).startServer();
        else
            throw new IllegalArgumentException("Send in the API_KEY to main()");
    }


}
