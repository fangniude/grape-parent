package org.grape;

import com.google.common.collect.Sets;
import org.jetbrains.annotations.NotNull;
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

    protected Set<GrapePlugin> dependencies() {
        return Sets.newHashSet();
    }

    private Set<GrapePlugin> allDependencies() {
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

    protected void inTheBeginning() {
    }

    protected void afterDataBaseInitial() {
    }

    protected void afterStarted(ApplicationContext context) {
    }

    @Override
    public int compareTo(@NotNull GrapePlugin o) {
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GrapePlugin that = (GrapePlugin) o;
        return Objects.equals(name(), that.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name());
    }
}
