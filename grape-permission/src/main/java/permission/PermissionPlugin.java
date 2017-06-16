package permission;

import account.AccountPlugin;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import org.grape.GrapePlugin;

import java.util.Set;

@AutoService(GrapePlugin.class)
public class PermissionPlugin extends GrapePlugin {
    @Override
    protected Set<GrapePlugin> dependencies() {
        return ImmutableSet.of(new AccountPlugin());
    }
}
