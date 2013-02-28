package net.jarlehansen.android.gcm;

import net.jarlehansen.android.gcm.server.GCMUtilsTestServer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * Run GCM test server
 *
 * @goal run-server
 * @requiresProject true
 * @requiresDirectInvocation true
 */
public class GCMUtilsTestServerMojo extends AbstractMojo {

    /**
     * @parameter expression="${apiKey}" default-value=""
     * @required
     */
    private String apiKey;

    /**
     * @parameter expression="${port}" default-value=9595
     * @required
     */
    private int port;

    /**
     * @parameter expression="${contextRoot}" default-value="/"
     * @required
     */
    private String contextRoot;

    public void execute() throws MojoExecutionException, MojoFailureException {
        StaticLoggerBinder.getSingleton().setLog(getLog());
        try {
            GCMUtilsTestServer.startServer(apiKey, port, contextRoot);
        } catch (Throwable t) {
            throw new MojoExecutionException("Unable to start the GCM test server", t);
        }
    }
}
