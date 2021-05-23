package domainapp.dom.impl.pets.fixture;

import domainapp.dom.impl.pets.dom.Owner;
import domainapp.dom.impl.pets.dom.Pet;
import domainapp.dom.impl.visits.dom.Visit;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

public class DeleteAllOwnersAndPets extends TeardownFixtureAbstract2 {
    @Override
    protected void execute(final ExecutionContext ec) {
        deleteFrom(Visit.class);
        deleteFrom(Pet.class);
        deleteFrom(Owner.class);
    }
}
