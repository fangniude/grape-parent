package permission.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.grape.GrapeModel;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "permission_role")
public final class Role extends GrapeModel {
    public static final Finder<Role> finder = new Finder<>(Role.class);

    public Role(String key, String name) {
        super(key, name);
    }

    @NotNull
    @Override
    public String key() {
        return super.key;
    }
}
