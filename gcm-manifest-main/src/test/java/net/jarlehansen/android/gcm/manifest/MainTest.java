package net.jarlehansen.android.gcm.manifest;

import org.junit.Test;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/14/13
 * Time: 2:13 PM
 */
public class MainTest {

    @Test(expected = IllegalArgumentException.class)
    public void main_noArgs() {
        Main.main(new String[]{});
    }


    @Test(expected = IllegalArgumentException.class)
    public void main_invalidNumberOfArguments() {
        Main.main(new String[]{""});
    }
}
