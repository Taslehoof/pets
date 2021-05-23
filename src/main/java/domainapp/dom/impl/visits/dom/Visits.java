package domainapp.dom.impl.visits.dom;


import domainapp.dom.impl.pets.dom.Pet;
import domainapp.dom.impl.pets.dom.Owner;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.datanucleus.query.typesafe.TypesafeQuery;
import java.time.LocalDateTime;

@DomainService(nature = NatureOfService.DOMAIN)
public class Visits {

    @Programmatic
    public java.util.List<Visit> findByPet(Pet pet) {
        TypesafeQuery<Visit> q = isisJdoSupport.newTypesafeQuery(Visit.class);
        final domainapp.dom.impl.visits.dom.QVisit cand = domainapp.dom.impl.visits.dom.QVisit.candidate();
        q = q.filter(
                cand.pet.eq((q.parameter("pet", Pet.class))
                )
        );
        return q.setParameter("pet", pet)
                .executeList();
    }

    @Programmatic
    public java.util.List<Visit> findNotPaid() {
        TypesafeQuery<Visit> q = isisJdoSupport.newTypesafeQuery(Visit.class);
        final QVisit cand = QVisit.candidate();
        q = q.filter(
                cand.paidOn.eq(q.parameter("paidOn", LocalDateTime.class)
                )
        );
        return q.setParameter("paidOn", null)
                .executeList();
    }

    @Programmatic
    public java.util.List<Visit> findNotPaidBy(Owner owner) {
        TypesafeQuery<Visit> q = isisJdoSupport.newTypesafeQuery(Visit.class);
        final QVisit cand = QVisit.candidate();
        q = q.filter(
                cand.paidOn.eq(q.parameter("paidOn", LocalDateTime.class)
                ).and(
                        cand.pet.owner.eq(q.parameter("owner", Owner.class))
                )
        );
        return q.setParameter("paidOn", null)
                .setParameter("owner", owner)
                .executeList();
    }

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}