package domainapp.dom.impl.pets.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import domainapp.dom.impl.pets.dom.Owner;
import domainapp.dom.impl.pets.dom.Pet;

public class DeleteAllOwnersAndPets extends TeardownFixtureAbstract2 {
    @Override
    protected void execute(final ExecutionContext ec) {
        deleteFrom(Pet.class);
        deleteFrom(Owner.class);
    }
}
