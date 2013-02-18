package net.jarlehansen.android.gcm.manifest;

import org.junit.Test;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 2:13 PM
 */
public class MainTest {

    @Test(expected = IllegalArgumentException.class)
    public void main_invalidFile() {
        Main.main(new String[]{"invalid-file"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void main_noArgs() {
        Main.main(new String[]{});
    }

    @Test
    public void main() {
        Main.main(new String[]{"src/test/resources/AndroidManifest.xml"});
    }
}
