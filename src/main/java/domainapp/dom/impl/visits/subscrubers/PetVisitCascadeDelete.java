package domainapp.dom.impl.visits.subscrubers;

import domainapp.dom.impl.pets.dom.Pet;
import domainapp.dom.impl.visits.dom.Visit;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.repository.RepositoryService;
import domainapp.dom.impl.visits.dom.Visits;

import java.util.Collection;

@DomainService(nature = NatureOfService.DOMAIN)
public class PetVisitCascadeDelete
        extends org.apache.isis.applib.AbstractSubscriber {

    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(Pet.RemovingEvent ev) {
        Collection<Visit> visitsForPet = visits.findByPet(ev.getSource());
        for (Visit visit : visitsForPet) {
            repositoryService.removeAndFlush(visit);
        }
    }

    @javax.inject.Inject
    Visits visits;
    @javax.inject.Inject
    RepositoryService repositoryService;
}
