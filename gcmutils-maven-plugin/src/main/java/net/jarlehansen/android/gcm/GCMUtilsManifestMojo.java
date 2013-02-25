package net.jarlehansen.android.gcm;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import net.jarlehansen.android.gcm.manifest.GCMUtilsManifest;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * Goal which touches a timestamp file.
 *
 * @goal manifest
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
