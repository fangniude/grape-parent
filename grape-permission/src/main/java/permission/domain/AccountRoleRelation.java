package permission.domain;

import io.ebean.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.grape.GrapeModel;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permission_account_role_relation")
public final class AccountRoleRelation extends GrapeModel {
    public static final Finder<AccountRoleRelation> finder = new Finder<>(AccountRoleRelation.class);

    @Column(name = "account_id", nullable = false, updatable = false)
    private Long accountId;

    @Column(name = "role_id", nullable = false, updatable = false)
    private Long roleId;

    @NotNull
    @Override
    public String key() {
        return String.format("account:%d,role:%d", accountId, roleId);
    }

    public static Query<AccountRoleRelation> findRoleIdsByAccount(Long id) {
        return findByAccount(id).select("role_id");
    }

    public static Query<AccountRoleRelation> findByAccount(Long id) {
        return AccountRoleRelation.finder.query().where().eq("account_id", id).query();
    }
}
