package domainapp.dom.impl.visits.contributions;

import domainapp.dom.impl.pets.dom.Pet;
import domainapp.dom.impl.visits.dom.Visit;
import org.apache.isis.applib.annotation.*;
import domainapp.dom.impl.visits.dom.Visits;

@Mixin(method = "coll")
public class Pet_visits {

    private final Pet pet;
    public Pet_visits(Pet pet) {
        this.pet = pet;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(defaultView = "table")
    public java.util.Collection<Visit> coll() {
        return visits.findByPet(pet);
    }

    @javax.inject.Inject
    Visits visits;

}