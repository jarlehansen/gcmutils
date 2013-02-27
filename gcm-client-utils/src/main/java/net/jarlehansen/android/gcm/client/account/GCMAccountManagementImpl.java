package net.jarlehansen.android.gcm.client.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Patterns;
import net.jarlehansen.android.gcm.client.log.GCMUtilsLog;

import java.util.regex.Pattern;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/27/13
 * Time: 9:49 AM
 */
public class GCMAccountManagementImpl implements GCMAccountManagement {

    public String getMainAccount(Context context) {
        Account[] accounts = AccountManager.get(context).getAccounts();
        return getMainAccount(accounts, Patterns.EMAIL_ADDRESS);
    }

    String getMainAccount(Account[] accounts, Pattern emailPattern) {
        String mainAccount = "";
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                mainAccount = account.name;
            }
        }

        if ("".equals(mainAccount))
            GCMUtilsLog.w("No account found on device");

        return mainAccount;
    }
}
