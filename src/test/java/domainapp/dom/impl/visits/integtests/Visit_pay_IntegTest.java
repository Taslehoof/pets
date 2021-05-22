package domainapp.dom.impl.visits.integtests;

import domainapp.dom.impl.PetClinicModuleIntegTestAbstract;
import domainapp.dom.impl.pets.fixture.Owner_enum;
import domainapp.dom.impl.visits.dom.Visit;
import domainapp.dom.impl.visits.dom.Visits;
import org.apache.isis.applib.fixturescripts.setup.PersonaEnumPersistAll;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Visit_pay_IntegTest extends PetClinicModuleIntegTestAbstract {

    Visit visit;

    @Before
    public void setup() {
        runFixtureScript(new PersonaEnumPersistAll<>(Owner_enum.class));
        visit = visits.findNotPaid().get(0);
    }

    @Test
    public void happy_case() {

        // given
        assertThat(visit.getPaidOn()).isNull();

        // when
        wrap(visit).paid();

        // then
        assertThat(visit.getPaidOn()).isNotNull();
        assertThat(visit.getPaidOn()).isEqualTo(clockService.now());
    }

    @Test
    public void cannot_edit_paidOn_directly() {

        // expecting
        expectedExceptions.expect(DisabledException.class);
        expectedExceptions.expectMessage("Use 'paid on' action");

        // when
        wrap(visit).setPaidOn(clockService.now());
    }

    @Test
    public void cannot_pay_more_than_once() {

        // given
        wrap(visit).paid();
        assertThat(visits.findNotPaid()).asList().doesNotContain(visit);

        // expecting
        expectedExceptions.expect(DisabledException.class);
        expectedExceptions.expectMessage("Already paid");

        // when
        wrap(visit).paid();

    }

    @Inject
    Visits visits;

    @Inject
    ClockService clockService;
}