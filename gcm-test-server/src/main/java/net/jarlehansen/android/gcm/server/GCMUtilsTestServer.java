package net.jarlehansen.android.gcm.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/26/13
 * Time: 9:42 AM
 */
public class GCMUtilsTestServer {
    private static final int DEFAULT_PORT = 9595;
    private static final String DEFAULT_CONTEXTROOT = "/";

    public static void main(String[] args) throws Exception {
        int port = 0;
        String contextRoot = null;
        if (args.length == 2) {
            port = Integer.parseInt(args[0]);
            contextRoot = args[1];
        } else if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        GCMUtilsTestServer.startServer(port, contextRoot);
    }

    public static void startServer(int port, String contextRoot) throws Exception {
        Server server;
        if (port <= 0)
            server = new Server(DEFAULT_PORT);
        else
            server = new Server(port);

        ServletContextHandler contextHandler = new ServletContextHandler();
        if (contextRoot == null)
            contextHandler.setContextPath(DEFAULT_CONTEXTROOT);
        else
            contextHandler.setContextPath(contextRoot);

        server.setHandler(contextHandler);

        contextHandler.addServlet(new ServletHolder(new GCMUtilsServlet()), "/");

        server.start();
        server.join();
    }
}
