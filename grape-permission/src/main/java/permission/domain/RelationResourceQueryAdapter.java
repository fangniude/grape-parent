package permission.domain;


import account.domain.Account;
import account.domain.PersonalResource;
import io.ebean.Junction;
import io.ebean.Query;
import io.ebean.event.BeanQueryAdapter;
import io.ebean.event.BeanQueryRequest;
import org.grape.GrapeResource;
import org.grape.RelationResource;

public final class RelationResourceQueryAdapter implements BeanQueryAdapter {

    @Override
    public boolean isRegisterFor(Class<?> cls) {
        return GrapeResource.class.isAssignableFrom(cls) && cls.getAnnotation(RelationResource.class) != null;
    }

    @Override
    public int getExecutionOrder() {
        return 1;
    }

    @Override
    public void preQuery(BeanQueryRequest<?> request) {
        if (Account.current().equals(Account.getAdminId())) {
            return;
        }

        Query<?> query = request.getQuery();
        Class<?> aClass = query.getBeanType();
        if (!GrapeResource.class.isAssignableFrom(aClass)) {
            return;
        }

        Junction<?> jun = query.where().or();

        // 0. if 1,2 not match, return empty
        jun.eq("id", -1);

        // 1. if personal resource, add his(her) own data
        if (PersonalResource.class.isAssignableFrom(aClass)) {
            jun.eq("account_id", Account.current());
        }

        // 2. by resource relation
        jun.in("id", ResourceModel.findAllResourceIdsByAccountCls(Account.current(), aClass.getName()));

    }
}
