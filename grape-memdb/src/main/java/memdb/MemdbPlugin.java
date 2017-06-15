package memdb;

import com.google.auto.service.AutoService;
import org.grape.GrapePlugin;

@AutoService(GrapePlugin.class)
public class MemdbPlugin extends GrapePlugin {
    @Override
    public boolean hasEntity() {
        return false;
    }
}
