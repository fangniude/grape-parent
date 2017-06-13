package permission.domain;

import io.ebean.Query;
import lombok.Getter;
import lombok.Setter;
import org.grape.GrapeModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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


    public static Query<RoleResourceRelation> findResourceIdsByAccount(String cls, Long accountId) {
        return findByRoleIds(cls, AccountRoleRelation.findRoleIdsByAccount(accountId));
    }

    public static Query<RoleResourceRelation> findResourceIdsByRoleIds(String cls, Query<AccountRoleRelation> roleIds) {
        return findByRoleIds(cls, roleIds).select("resource_id");
    }

    public static Query<RoleResourceRelation> findByRoleIds(String cls, Query<AccountRoleRelation> roleIds) {
        return RoleResourceRelation.finder.query().where().and().eq("resource_cls", cls).in("role_id", roleIds).query();
    }

}
