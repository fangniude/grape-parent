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

    private String name;

    protected String name() {
        return getClass().getPackage().getName();
    }

    protected boolean hasEntity() {
        return true;
    }

    protected Set<GrapePlugin> dependencies() {
        return Sets.newHashSet();
    }

    protected void inTheBeginning() {
    }

    protected void afterDataBaseInitial() {
    }

    protected void afterSpringInitial(ApplicationContext context) {
    }

    protected void afterStarted(ApplicationContext context) {
    }

    @Override
    public int compareTo(GrapePlugin o) {
        if (dependencies().contains(o)) {
            return -1;
        } else if (o.dependencies().contains(this)) {
            return 1;
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
