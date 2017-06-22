package group.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "group_group")
public class DefaultGroup extends AbsGroup {

    public DefaultGroup(GroupType type, String name) {
        super(type, name);
    }

    @NotNull
    @Override
    public String key() {
        return String.format("%s_%s", super.type.key(), super.name);
    }
}
