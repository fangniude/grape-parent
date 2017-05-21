package org.grape;

import com.google.common.collect.Sets;
import org.springframework.context.ApplicationContext;

import java.util.Objects;
import java.util.Set;

/**
 * Created by Lewis
 * 2017-05-20.
 */
public abstract class GrapePlugin implements Comparable<GrapePlugin> {

    public String name() {
        return getClass().getPackage().getName();
    }

    public boolean hasEntity() {
        return true;
    }

    public Set<GrapePlugin> dependencies() {
        return Sets.newHashSet();
    }

    public final Set<GrapePlugin> allDependencies() {
        Set<GrapePlugin> deps = dependencies();
        if (deps.isEmpty()) {
            return deps;
        } else {
            Set<GrapePlugin> all = Sets.newHashSet(deps);
            for (GrapePlugin dep : deps) {
                all.addAll(dep.allDependencies());
            }
            return all;
        }
    }

    public void inTheBeginning() {
    }

    public void afterDataBaseInitial() {
    }

    public void afterSpringInitial(ApplicationContext context) {
    }

    public void afterStarted(ApplicationContext context) {
    }

    @Override
    public int compareTo(GrapePlugin o) {
        if (allDependencies().contains(o)) {
            return 1;
        } else if (o.allDependencies().contains(this)) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrapePlugin that = (GrapePlugin) o;
        return Objects.equals(name(), that.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name());
    }
}
