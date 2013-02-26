package net.jarlehansen.android.gcm;

import net.jarlehansen.android.gcm.manifest.GCMUtilsManifest;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * Generate the GCM tags in AndroidManifest.xml
 *
 * @goal gcmify
 * @requiresProject true
 * @requiresDirectInvocation true
 */
public class GCMUtilsManifestMojo extends AbstractMojo {

    /**
     * @parameter expression="${manifestPath}" default-value=AndroidManifest.xml
     * @required
     */
    private String manifestPath;


    /**
     * @parameter expression="${project.groupId}"
     * @required
     */
    private String groupId;

    /**
     * @parameter expression="${skipBackup} default-value=false
     */
    private boolean skipBackup;

    public void execute() throws MojoExecutionException {
        StaticLoggerBinder.getSingleton().setLog(getLog());

        getLog().info("GCMUtils maven plugin:");
        getLog().info("Manifest path: " + manifestPath);
        getLog().info("GroupId: " + groupId);
        getLog().info("skipBackup: " + skipBackup);
        getLog().info("------------------------------------------------------------------------");

        try {
            GCMUtilsManifest.start(manifestPath, groupId, skipBackup);
        } catch (Throwable t) {
            throw new MojoExecutionException("The GCM AndroidManifest.xml operation failed", t);
        }
    }
}
