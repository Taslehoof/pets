package domainapp.dom.impl.visits.dom;

import com.google.common.collect.ComparisonChain;
import domainapp.dom.impl.pets.dom.Pet;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.*;
import org.joda.time.LocalDateTime;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import java.math.BigDecimal;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "visits" )
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column ="version")
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class Visit implements Comparable<Visit> {

    @javax.jdo.annotations.Column(allowsNull = "false", name = "petId")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private Pet pet;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private LocalDateTime visitAt;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 4000)
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(multiLine = 5)
    @Getter
    @Setter
    private String reason;

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public Visit enterOutcome(
            @Parameter(maxLength = 4000)
            @ParameterLayout(multiLine = 5)
            final String diagnosis,
            final BigDecimal cost) {
        this.diagnosis = diagnosis;
        this.cost = cost;
        return this;
    }

    @javax.jdo.annotations.Column(allowsNull = "true", length = 4000)
    @Property(editing = Editing.DISABLED, editingDisabledReason = "Use 'enter outcome' action")
    @PropertyLayout(multiLine = 5)
    @Getter @Setter
    private String diagnosis;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 6, scale = 2)
    @Property(editing = Editing.DISABLED, editingDisabledReason = "Use 'enter outcome' action")
    @Getter @Setter
    private BigDecimal cost;

    public Visit(final Pet pet, final LocalDateTime visitAt, final String reason) {
        this.pet = pet;
        this.visitAt = visitAt;
        this.reason = reason;
    }

    public String title() {
        return String.format(
                "%s: %s (%s)",
                getVisitAt().toString("yyyy-MM-dd hh:mm"),
                getPet().getOwner().getName(),
                getPet().getName());
    }

    @Override
    public String toString() {
        return getVisitAt().toString("yyyy-MM-dd hh:mm");
    }

    @Override
    public int compareTo(final Visit other) {
        return ComparisonChain.start()
                .compare(this.getVisitAt(), other.getVisitAt())
                .compare(this.getPet(), other.getPet())
                .result();
    }
}
