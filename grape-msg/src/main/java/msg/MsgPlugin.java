package msg;

import com.google.auto.service.AutoService;
import org.grape.GrapePlugin;

@AutoService(GrapePlugin.class)
public final class MsgPlugin extends GrapePlugin {
    @Override
    public boolean hasEntity() {
        return false;
    }
}
