package net.jarlehansen.android.gcm.manifest;

import net.jarlehansen.android.gcm.manifest.xml.android.AndroidManifestAppender;
import net.jarlehansen.android.gcm.manifest.xml.android.AndroidManifestDeserializer;
import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import net.jarlehansen.android.gcm.manifest.xml.android.parts.*;

import java.io.File;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/22/13
 * Time: 7:31 AM
 */
public class AndroidManifestService {
    private final String inputFileName;
    private final String outputFileName;
    private final String packageName;

    public AndroidManifestService(String inputFileName, String outputFileName, String packageName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.packageName = packageName;
    }

    public void execute() {
        File inputFile = new File(inputFileName);
        AndroidManifestDeserializer deserializer = new AndroidManifestDeserializer(inputFile);
        AndroidManifest manifest = deserializer.deserialize();

        XmlParts xmlParts = populateXmlParts(manifest);

        File outputFile = new File(outputFileName);
        AndroidManifestAppender appender = new AndroidManifestAppender(inputFile, outputFile, packageName);
        appender.append(xmlParts);
    }

    XmlParts populateXmlParts(AndroidManifest manifest) {
        XmlParts xmlParts = new XmlParts();

        UsesSdkImplementor usesSdkImplementor = new UsesSdkImplementor();
        usesSdkImplementor.populateXmlParts(xmlParts, manifest);

        PermissionImplementor permissionImplementor = new PermissionImplementor();
        permissionImplementor.populateXmlParts(xmlParts, manifest);

        UsesPermissionImplementor usesPermissionImplementor = new UsesPermissionImplementor();
        usesPermissionImplementor.populateXmlParts(xmlParts, manifest);

        ServiceImplementor serviceImplementor = new ServiceImplementor();
        serviceImplementor.populateXmlParts(xmlParts, manifest);

        ReceiverImplementor receiverImplementor = new ReceiverImplementor();
        receiverImplementor.populateXmlParts(xmlParts, manifest);

        return xmlParts;
    }
}
