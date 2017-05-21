package org.grape;

import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.UpdatedTimestamp;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.sql.Timestamp;

/**
 * Created by Lewis
 * 2017-05-21.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class GrapeModel extends Model {
    @Id
    private Long id;

    private String key;

    private String name;

    private String remark;

    private Boolean auth;

    @Version
    private Long version;

    @CreatedTimestamp
    private Timestamp whenCreated;

    @UpdatedTimestamp
    private Timestamp whenUpdated;
}
