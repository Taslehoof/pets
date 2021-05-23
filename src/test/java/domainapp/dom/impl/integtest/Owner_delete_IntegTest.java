package domainapp.dom.impl.integtest;

import domainapp.dom.impl.PetClinicModuleIntegTestAbstract;
import domainapp.dom.impl.pets.dom.Owner;
import domainapp.dom.impl.pets.fixture.Owner_enum;
import domainapp.dom.impl.visits.dom.Visit;
import domainapp.dom.impl.visits.dom.Visits;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Owner_delete_IntegTest extends PetClinicModuleIntegTestAbstract {

    @Test
    public void can_delete_if_there_are_no_unpaid_visits() {

        // given
        runFixtureScript(Owner_enum.FRED_HUGHES.builder());

        Owner owner = Owner_enum.FRED_HUGHES.findUsing(serviceRegistry);
        List<Visit> any = visits.findNotPaidBy(owner);
        assertThat(any).isEmpty();

        // when
        wrap(owner).delete();

        // then
        Owner ownerAfter = Owner_enum.FRED_HUGHES.findUsing(serviceRegistry);
        assertThat(ownerAfter).isNull();
    }

    @Test
    public void cannot_delete_with_unpaid_visits() {

        // given
        runFixtureScript(Owner_enum.MARY_JONES.builder());

        Owner owner = Owner_enum.MARY_JONES.findUsing(serviceRegistry);
        List<Visit> any = visits.findNotPaidBy(owner);
        assertThat(any).isNotEmpty();

        // expect
        expectedExceptions.expect(DisabledException.class);
        expectedExceptions.expectMessage("This owner still has unpaid visit(s)");

        // when
        wrap(owner).delete();
    }

    @Inject
    Visits visits;
}