package org.grape;

import io.ebean.Finder;

import java.util.Optional;

public final class GrapeFinder<T> extends Finder<Long, T> {
    private final Class<T> type;

    public GrapeFinder(Class<T> type) {
        super(type);
        this.type = type;
    }

    public Optional<T> byIdo(Long id) {
        return Optional.ofNullable(byId(id));
    }

    public Optional<T> byKey(String key) {
        return db().find(type).where().eq("primary_key", key).findOneOrEmpty();
    }
}
