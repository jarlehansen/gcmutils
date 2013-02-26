package net.jarlehansen.android.gcm.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/26/13
 * Time: 9:48 AM
 */
public class GCMUtilsServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(GCMUtilsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.info("JA");
    }
}
