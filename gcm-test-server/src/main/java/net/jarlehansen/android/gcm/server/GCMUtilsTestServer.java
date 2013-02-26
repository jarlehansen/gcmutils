package net.jarlehansen.android.gcm.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/26/13
 * Time: 9:42 AM
 */
public class GCMUtilsTestServer {
    private static final Logger logger = LoggerFactory.getLogger(GCMUtilsTestServer.class);

    private static final int DEFAULT_PORT = 9595;
    private static final String DEFAULT_CONTEXTROOT = "/";

    public static void main(String[] args) throws Exception {
        String apiKey;
        int port = 0;
        String contextRoot = null;

        int numOfArsgs = args.length;
        switch (numOfArsgs) {
            case 3:
                contextRoot = args[2];
            case 2:
                port = Integer.parseInt(args[1]);
            case 1:
                apiKey = args[0];
                break;
            default:
                throw new IllegalArgumentException("Invalid number of input arguments, start with: java -jar gcm-test-server.jar api-key port-number context-root");
        }

        GCMUtilsTestServer.startServer(apiKey, port, contextRoot);
    }

    public static void startServer(String apiKey, int port, String root) throws Exception {
        logger.info("Starting GCMUtilsTestServer");

        final int portNum;
        if (port <= 0)
            portNum = DEFAULT_PORT;
        else
            portNum = port;

        logger.info("Port number: {}", portNum);
        Server server = new Server(portNum);
        ServletContextHandler contextHandler = new ServletContextHandler();
        String contextRoot;
        if (root == null)
            contextRoot = DEFAULT_CONTEXTROOT;
        else
            contextRoot = root;

        logger.info("Context root: {}", contextRoot);
        contextHandler.setContextPath(contextRoot);
        server.setHandler(contextHandler);
        logger.info("API-KEY: {}", apiKey);
        contextHandler.addServlet(new ServletHolder(new GCMUtilsServlet(apiKey)), "/");
        logger.info("#########################################");
        logger.info("SERVER URL: http://localhost:{}{}", portNum, contextRoot);
        logger.info("#########################################");

        server.start();
        server.join();
    }


}
