package net.jarlehansen.android.gcm.manifest.xml.android;

import net.jarlehansen.android.gcm.manifest.xml.android.model.AndroidManifest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.Assert.assertNotNull;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 9:09 AM
 */
public class AndroidManifestDeserializerTest {
    private static final Logger logger = LoggerFactory.getLogger(AndroidManifestDeserializerTest.class);

    @Test
    public void deserialize() {
        AndroidManifestDeserializer deserializer = new AndroidManifestDeserializer(new File("src/test/resources/AndroidManifest.xml"));
        AndroidManifest manifest = deserializer.deserialize();
        logger.info(manifest.toString());

        assertNotNull(manifest);
    }
}
