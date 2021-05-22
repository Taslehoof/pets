package domainapp.dom.impl.dashboard;

import domainapp.dom.impl.pets.dom.Owner;
import domainapp.dom.impl.pets.dom.Owners;
import domainapp.dom.impl.visits.dom.Visit;
import domainapp.dom.impl.visits.dom.Visits;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.tablecol.TableColumnOrderService;
import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "dashboard.Dashboard"
)
public class Dashboard {

    public String title() { return getOwners().size() + " owners"; }

    @CollectionLayout(defaultView = "table")
    public List<Owner> getOwners() {
        return owners.listAll();
    }

    @CollectionLayout(defaultView = "table")
    public List<Visit> getOverdue() {
        List<Visit> notPaid = visits.findNotPaid();
        LocalDateTime thirtyDaysAgo = clockService.nowAsLocalDateTime().minusDays(30);
        return notPaid.stream()
                .filter(x -> x.getVisitAt().isBefore(thirtyDaysAgo))
                .collect(Collectors.toList());
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, associateWith = "overdue")
    public Dashboard paid(List<Visit> visits) {
        for (Visit visit : visits) {
            if(visit.getPaidOn() == null) {
                visit.paid();
            }
        }
        return this;
    }

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class RemovePaidOnFromOverdue extends TableColumnOrderService.Default {
        @Override
        public List<String> orderParented(
                final Object parent,
                final String collectionId,
                final Class<?> collectionType,
                final List<String> propertyIds) {
            if (parent instanceof Dashboard && "overdue".equalsIgnoreCase(collectionId)) {
                propertyIds.remove("paidOn");
            }
            return propertyIds;
        }
    }

    @javax.inject.Inject
    Visits visits;

    @javax.inject.Inject
    ClockService clockService;
    @javax.inject.Inject
    Owners owners;
}
