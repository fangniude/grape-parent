package permission.domain;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.grape.GrapeModel;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permission_resource_relation")
public final class ResourceRelation extends GrapeModel {
    public static final Finder<ResourceRelation> finder = new Finder<>(ResourceRelation.class);

    private String parentCls;
    private Long parentId;
    private String childCls;
    private Long childId;

    @NotNull
    @Override
    public String key() {
        return String.format("parentCls:%s,id:%d,childCls:%s,id:%d", parentCls, parentId, childCls, childId);
    }

    public ResourceModel parent() {
        return new ResourceModel(parentCls, parentId);
    }

    public ResourceModel child() {
        return new ResourceModel(childCls, childId);
    }

    public static List<ResourceRelation> findAll() {
        return finder.query().setUseQueryCache(true).findList();
    }

    public static Set<ResourceModel> parents(List<ResourceRelation> allRR, ResourceModel rm) {
        return allRR.stream()
                .filter(rr -> rr.child().equals(rm))
                .map(ResourceRelation::parent)
                .collect(Collectors.toSet());
    }

    public static Set<ResourceModel> allParents(List<ResourceRelation> allRR, ResourceModel rm) {
        Set<ResourceModel> parents = parents(allRR, rm);

        Set<ResourceModel> subParents = parents.stream()
                .map(crm -> ResourceRelation.allParents(allRR, rm))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        return Sets.union(parents, subParents);
    }

    public static Set<ResourceModel> children(List<ResourceRelation> allRR, ResourceModel rm) {
        return allRR.stream()
                .filter(rr -> rr.parent().equals(rm))
                .map(ResourceRelation::child)
                .collect(Collectors.toSet());
    }

    public static Set<ResourceModel> allChildren(List<ResourceRelation> allRR, Set<ResourceModel> rms) {
        if (rms.isEmpty()) {
            return Sets.newHashSet();
        } else {
            Set<ResourceModel> children = rms.stream()
                    .map(rm -> ResourceRelation.children(allRR, rm))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());

            if (children.isEmpty()) {
                return Sets.newHashSet();
            } else {
                Set<ResourceModel> allChildren = ResourceRelation.allChildren(allRR, children);

                return Sets.union(rms, allChildren);
            }
        }
    }
}
