package net.jarlehansen.android.gcm.client.account;

import android.accounts.Account;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import net.jarlehansen.android.gcm.client.AbstractTestSetup;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/27/13
 * Time: 9:50 AM
 */
@RunWith(RobolectricTestRunner.class)
public class GCMAccountManagementImplTest extends AbstractTestSetup {

    @Test
    public void getAccount() {
        Account account = new Account("hansjar@gmail.com", "com.google");

        GCMAccountManagementImpl accountManagement = new GCMAccountManagementImpl();
        String mainAccount = accountManagement.getMainAccount(new Account[]{account}, Pattern.compile("[\\S]*"));

        assertEquals("hansjar@gmail.com", mainAccount);
    }
}
