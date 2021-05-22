package domainapp.dom.impl.pets.fixture;

import domainapp.dom.impl.pets.dom.Owner;
import domainapp.dom.impl.pets.dom.Owners;
import domainapp.dom.impl.pets.dom.PetSpecies;
import lombok.AllArgsConstructor;
import org.apache.isis.applib.fixturescripts.PersonaWithBuilderScript;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import java.util.Arrays;

import static domainapp.dom.impl.pets.fixture.OwnerBuilderScript.PetData;

@AllArgsConstructor
public enum Owner_enum
        implements PersonaWithBuilderScript<Owner, OwnerBuilderScript> {

    JOHN_SMITH("John", "Smith", null, new PetData[]{
            new PetData("Rover", PetSpecies.Dog, 3)
    }),
    MARY_JONES("Mary","Jones", "+353 1 555 1234", new PetData[] {
            new PetData("Tiddles", PetSpecies.Cat, 1),
            new PetData("Harry", PetSpecies.Budgerigar, 2)
    }),
    FRED_HUGHES("Fred","Hughes", "07777 987654", new PetData[] {
            new PetData("Jemima", PetSpecies.Hamster, 0)
    });


    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final PetData[] petData;

    //@Override
    public Owner findUsing(final ServiceRegistry2 serviceRegistry) {
        return serviceRegistry.lookupService(Owners.class)
                .findByLastNameAndFirstName(lastName, firstName);
    }

    @Override
    public OwnerBuilderScript builder() {
        return new OwnerBuilderScript()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPhoneNumber(phoneNumber)
                .setPetData(Arrays.asList(petData));
    }
}