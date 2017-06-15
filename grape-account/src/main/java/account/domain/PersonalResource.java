package account.domain;

import lombok.Getter;
import lombok.Setter;
import org.grape.GrapeModel;
import org.grape.GrapeResource;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public abstract class PersonalResource extends GrapeResource {
    @Column(name = "account_id", nullable = false, updatable = false)
    private Long accountId;

    public static class Finder<T> extends GrapeModel.Finder<T> {

        public Finder(Class type) {
            super(type);
        }

        public List<T> byCurrentAccount() {
            return db().find(super.type).where().eq("account_id", Account.current()).findList();
        }

        public List<T> byAccountId(Long id) {
            return db().find(super.type).where().eq("account_id", id).findList();
        }
    }
}
