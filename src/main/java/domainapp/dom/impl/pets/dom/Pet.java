package domainapp.dom.impl.pets.dom;

import com.google.common.collect.ComparisonChain;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ObjectRemovingEvent;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "pets" )
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column ="version")
@javax.jdo.annotations.Unique(name="Pet_owner_name_UNQ", members = {"owner","name"})
@DomainObject(
        auditing = Auditing.ENABLED,
        removingLifecycleEvent = Pet.RemovingEvent.class
)
@DomainObjectLayout()  // causes UI events to be triggered
public class Pet implements Comparable<Pet> {

    public static class  RemovingEvent extends ObjectRemovingEvent<Pet> {}

    public Pet(final Owner owner, final String name, final PetSpecies petSpecies) {
        this.owner = owner;
        this.name = name;
        this.petSpecies = petSpecies;
    }

    public String title() {
        return String.format(
                "%s (%s owned by %s)",
                getName(), getPetSpecies().name().toLowerCase(), getOwner().getName());
    }

    public String iconName() {
        return getPetSpecies().name().toLowerCase();
    }

    @javax.jdo.annotations.Column(allowsNull = "false", name = "ownerId")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private Owner owner;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private String name;

    @Override
    public String toString() {
        return getName();
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private PetSpecies petSpecies;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 4000)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private String notes;

    @Override
    public int compareTo(final Pet other) {
        return ComparisonChain.start()
                .compare(this.getOwner(), other.getOwner())
                .compare(this.getName(), other.getName())
                .result();
    }

}