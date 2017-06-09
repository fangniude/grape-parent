package mail;

import com.google.common.collect.Sets;
import msg.MsgPlugin;
import org.grape.GrapePlugin;

import java.util.Set;

public class MailPlugin extends GrapePlugin {
    @Override
    public boolean hasEntity() {
        return false;
    }

    @Override
    public Set<GrapePlugin> dependencies() {
        return Sets.newHashSet(new MsgPlugin());
    }
}
