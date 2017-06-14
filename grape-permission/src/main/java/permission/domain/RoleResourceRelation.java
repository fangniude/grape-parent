package permission.domain;

import io.ebean.Query;
import lombok.Getter;
import lombok.Setter;
import org.grape.GrapeModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "permission_role_resource_relation")
public class RoleResourceRelation extends GrapeModel {
    public static final Finder<RoleResourceRelation> finder = new Finder<>(RoleResourceRelation.class);

    @Column(name = "role_id", nullable = false, updatable = false)
    private Long roleId;

    @Column(name = "resource_cls", nullable = false, updatable = false)
    private String resourceCls;

    @Column(name = "resource_id", nullable = false, updatable = false)
    private Long resourceId;


    public ResourceModel resource() {
        return new ResourceModel(resourceCls, resourceId);
    }

    public static Set<ResourceModel> findResourceByAccount(Long id) {
        return findByAccount(id).stream()
                .map(RoleResourceRelation::resource)
                .collect(Collectors.toSet());
    }

    public static List<RoleResourceRelation> findByAccount(Long id) {
        return findByAccountQuery(id).findList();
    }

    public static Query<RoleResourceRelation> findByAccountQuery(Long accountId) {
        return findByClsRoleIds(AccountRoleRelation.findRoleIdsByAccount(accountId));
    }

    public static Query<RoleResourceRelation> findByClsRoleIds(Query<AccountRoleRelation> roleIds) {
        return RoleResourceRelation.finder.query().where().in("role_id", roleIds).query();
    }

    public static Query<RoleResourceRelation> findResourceIdsByClsAccount(String cls, Long accountId) {
        return findResourceIdsByClsRoleIds(cls, AccountRoleRelation.findRoleIdsByAccount(accountId));
    }

    public static Query<RoleResourceRelation> findResourceIdsByClsRoleIds(String cls, Query<AccountRoleRelation> roleIds) {
        return findByClsRoleIds(cls, roleIds).select("resource_id");
    }

    public static Query<RoleResourceRelation> findByClsRoleIds(String cls, Query<AccountRoleRelation> roleIds) {
        return RoleResourceRelation.finder.query().where().and().eq("resource_cls", cls).in("role_id", roleIds).query();
    }

}
