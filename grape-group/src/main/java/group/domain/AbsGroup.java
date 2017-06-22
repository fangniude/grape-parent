package group.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import io.ebean.Ebean;
import io.ebean.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.grape.GrapeException;
import org.grape.GrapeResource;
import org.springframework.util.ClassUtils;
import permission.domain.ResourceRelation;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbsGroup extends GrapeResource {
    @ManyToOne(cascade = CascadeType.REFRESH)
    protected GroupType type;
    protected String leader;

    public AbsGroup(GroupType type) {
        this.type = type;
    }

    public AbsGroup(GroupType type, String key) {
        super(key);
        this.type = type;
    }

    public AbsGroup(GroupType type, String key, String name) {
        super(key, name);
        this.type = type;
    }

    public void addChild(AbsGroup group) {
        if (this.type.getChildren().contains(group.type)) {
            ResourceRelation rr = new ResourceRelation(type.getCls(), this.id, group.type.getCls(), group.id);
            rr.save();
        } else {
            throw new GrapeException("type is invalid.");
        }
    }

    public void removeChild(AbsGroup group) {
        if (this.type.getChildren().contains(group.type)) {
            ResourceRelation rr = new ResourceRelation(type.getCls(), this.id, group.type.getCls(), group.id);
            Optional<ResourceRelation> rro = ResourceRelation.finder.byKey(rr.key());
            rro.ifPresent(Model::delete);
        } else {
            throw new GrapeException("type is invalid.");
        }
    }

    public List<AbsGroup> children() {
        List<AbsGroup> result = Lists.newArrayList();

        Multimap<String, Long> clsIds = ResourceRelation.findByParent(this.getClass().getName(), this.id);

        Set<String> childClsSet = this.type.getChildren().stream().map(GroupType::getCls).collect(Collectors.toSet());

        clsIds.asMap().entrySet().stream()
                .filter(entry -> childClsSet.contains(entry.getKey()))
                .forEach(entry -> {
                    Class<?> aClass = ClassUtils.resolveClassName(entry.getKey(), AbsGroup.class.getClassLoader());
                    if (AbsGroup.class.isAssignableFrom(aClass)) {
                        List<? extends AbsGroup> list = Ebean.find((Class<? extends AbsGroup>) aClass).where().idIn(entry.getValue()).findList();
                        result.addAll(list);
                    }
                });
        return result;
    }
}
