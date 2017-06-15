package permission.domain;

import com.google.common.collect.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public final class ResourceModel {
    public final String cls;
    public final Long id;

    public static Set<ResourceModel> findByAccount(Long accountId) {
        return Sets.union(AccountResourceRelation.findResourceByAccount(accountId), RoleResourceRelation.findResourceByAccount(accountId));
    }

    public static Set<ResourceModel> findAllByAccount(Long accountId) {
        Set<ResourceModel> rms = findByAccount(accountId);
        List<ResourceRelation> all = ResourceRelation.findAll();
        return ResourceRelation.allChildren(all, rms);
    }

    public static Multimap<String, Long> findAllResourceByAccount(Long accountId) {
        SetMultimap<String, Long> multimap = Multimaps.newSetMultimap(Maps.newHashMap(), Sets::newHashSet);
        findAllByAccount(accountId).forEach(rm -> multimap.put(rm.cls, rm.id));
        return multimap;
    }

    public static Collection<Long> findAllResourceIdsByAccountCls(Long accountId, String cls) {
        return findAllResourceByAccount(accountId).get(cls);
    }
}
