package group.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.grape.GrapeModel;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "group_group_type")
public class GroupType extends GrapeModel {
    @Column(name = "root", nullable = false)
    private boolean root;

    @Column(name = "cls", updatable = false)
    private String cls;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private GroupType parent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    @JoinColumn(name = "parent_id")
    private List<GroupType> children;

    public GroupType(String key, String name) {
        super(key, name);
    }

    public GroupType(String key, String name, boolean root) {
        super(key, name);
        this.root = root;
    }

    public GroupType(String key, String name, boolean root, GroupType parent) {
        super(key, name);
        this.root = root;
        this.parent = parent;
    }

    @NotNull
    @Override
    public String key() {
        return super.key;
    }
}
