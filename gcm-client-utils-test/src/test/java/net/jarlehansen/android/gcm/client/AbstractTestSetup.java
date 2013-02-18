package net.jarlehansen.android.gcm.client;

import android.content.Context;
import android.content.res.AssetManager;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowLog;

import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/18/13
 * Time: 7:51 AM
 */
public abstract class AbstractTestSetup {
    static {
        ShadowLog.stream = System.out;
        Robolectric.bindShadowClass(ShadowLog.class);
    }

    private FileInputStream inputStream = null;
    protected Context context;
    protected AssetManager assetManager;

    protected void openTestFile() throws IOException {
        inputStream = new FileInputStream("src/test/resources/gcmutils-test.properties");
        assetManager = mock(AssetManager.class);
        context = mock(Context.class);
        when(context.getAssets()).thenReturn(assetManager);
        when(assetManager.open("gcmutils.properties")).thenReturn(inputStream);
    }

    protected void closeTestFile() throws IOException {
        if (inputStream != null)
            inputStream.close();
    }
}
