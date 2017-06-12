package mail;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import msg.MsgPlugin;
import org.grape.GrapePlugin;

import java.util.Set;

@AutoService(GrapePlugin.class)
public final class MailPlugin extends GrapePlugin {
    @Override
    public boolean hasEntity() {
        return false;
    }

    @Override
    public Set<GrapePlugin> dependencies() {
        return Sets.newHashSet(new MsgPlugin());
    }
}
