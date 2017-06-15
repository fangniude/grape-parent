package org.grape;

import io.ebean.Model;
import io.ebean.annotation.Cache;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.UpdatedTimestamp;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by Lewis
 * 2017-05-21.
 */
@Getter
@Setter
@Cache(naturalKey = "primary_key")
@MappedSuperclass
public abstract class GrapeModel extends Model {
    @Id
    protected Long id;

    @Column(name = "primary_key", unique = true, nullable = false, length = 64)
    protected String key;

    protected String name;

    protected String remark;

//    protected Boolean auth; todo may be used in data permission

    @Version
    protected Long version;

    @CreatedTimestamp
    protected LocalDateTime whenCreated;

    @UpdatedTimestamp
    protected LocalDateTime whenUpdated;

    protected GrapeModel() {
    }

    public GrapeModel(String key) {
        this.key = key;
    }

    public GrapeModel(String key, String name) {
        this.key = key;
        this.name = name;
    }

    @NotNull
    public abstract String key();

    @Override
    public void save() {
        this.key = key();
        super.save();
    }

    @Override
    public void update() {
        this.key = key();
        super.update();
    }

    @Override
    public void insert() {
        this.key = key();
        super.insert();
    }

    public static class Finder<T> extends io.ebean.Finder<Long, T> {
        protected final Class<T> type;

        public Finder(Class<T> type) {
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
}
