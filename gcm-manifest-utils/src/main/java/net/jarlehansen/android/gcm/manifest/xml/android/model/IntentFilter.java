package net.jarlehansen.android.gcm.manifest.xml.android.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: 2/21/13
 * Time: 8:38 AM
 */
@Root(strict = false)
public class IntentFilter {

    @ElementList(name = "action", inline = true, required = false)
    private List<Action> actions = null;

    @Element
    private Category category = null;

    public List<Action> getActions() {
        return actions;
    }

    public void addAction(Action action) {
        if (actions == null)
            actions = new ArrayList<Action>();

        actions.add(action);
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "IntentFilter{" +
                "actions=" + actions +
                ", category=" + category +
                '}';
    }
}
