package permission.domain;

import lombok.Getter;
import lombok.Setter;
import org.grape.GrapeModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "permission_role")
public class Role extends GrapeModel {
    public static final Finder<Role> finder = new Finder<>(Role.class);
}
