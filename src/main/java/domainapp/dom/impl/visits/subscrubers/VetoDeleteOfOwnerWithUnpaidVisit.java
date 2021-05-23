package domainapp.dom.impl.visits.subscrubers;

import domainapp.dom.impl.pets.dom.Owner;
import domainapp.dom.impl.visits.dom.Visit;
import domainapp.dom.impl.visits.dom.Visits;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import java.util.Collection;

@DomainService(nature = NatureOfService.DOMAIN)
class VetoDeleteOfOwnerWithUnpaidVisits extends org.apache.isis.applib.AbstractSubscriber {

    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(Owner.Delete ev) {

        switch (ev.getEventPhase()) {
            case DISABLE:
                Collection<Visit> visitsForPet = visits.findNotPaidBy(ev.getSource());
                if (!visitsForPet.isEmpty()) {
                    ev.veto("This owner still has unpaid visit(s)");
                }
                break;
        }
    }

    @javax.inject.Inject
    Visits visits;
}