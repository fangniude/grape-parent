package permission.domain;

import io.ebean.Query;
import lombok.Getter;
import lombok.Setter;
import org.grape.GrapeModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

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

    public static List<AccountResourceRelation> findByAccount(String cls, Long id) {
        return findByAccountQuery(cls, id).findList();
    }

    public static Query<AccountResourceRelation> findResourceIdsByAccountQuery(String cls, Long id) {
        return findByAccountQuery(cls, id).select("resource_id");
    }

    public static Query<AccountResourceRelation> findByAccountQuery(String cls, Long id) {
        return AccountResourceRelation.finder.query().where().and().eq("resource_cls", cls).eq("account_id", id).query();
    }
}
