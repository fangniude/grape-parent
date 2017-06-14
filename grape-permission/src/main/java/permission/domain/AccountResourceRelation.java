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
@Table(name = "permission_account_resource_relation")
public class AccountResourceRelation extends GrapeModel {
    public static final Finder<AccountResourceRelation> finder = new Finder<>(AccountResourceRelation.class);

    @Column(name = "account_id", nullable = false, updatable = false)
    private Long accountId;

    @Column(name = "resource_cls", nullable = false, updatable = false)
    private String resourceCls;

    @Column(name = "resource_id", nullable = false, updatable = false)
    private Long resourceId;

    public ResourceModel resource() {
        return new ResourceModel(resourceCls, resourceId);
    }

    public static Set<ResourceModel> findResourceByAccount(Long id) {
        return findByAccount(id).stream()
                .map(AccountResourceRelation::resource)
                .collect(Collectors.toSet());
    }

    public static List<AccountResourceRelation> findByAccount(Long id) {
        return findByAccountQuery(id).findList();
    }

    public static Query<AccountResourceRelation> findResourceIdsByAccountQuery(Long id) {
        return findByAccountQuery(id).select("resource_id");
    }

    public static Query<AccountResourceRelation> findByAccountQuery(Long id) {
        return AccountResourceRelation.finder.query().where().eq("account_id", id).query();
    }

    public static List<AccountResourceRelation> findByClsAccount(String cls, Long id) {
        return findByClsAccountQuery(cls, id).findList();
    }

    public static Query<AccountResourceRelation> findResourceIdsByClsAccountQuery(String cls, Long id) {
        return findByClsAccountQuery(cls, id).select("resource_id");
    }

    public static Query<AccountResourceRelation> findByClsAccountQuery(String cls, Long id) {
        return AccountResourceRelation.finder.query().where().and().eq("resource_cls", cls).eq("account_id", id).query();
    }
}
