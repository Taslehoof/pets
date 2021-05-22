package domainapp.dom.impl.visits;

import domainapp.dom.impl.PetClinicModuleIntegTestAbstract;
import domainapp.dom.impl.pets.dom.Owner;
import domainapp.dom.impl.pets.dom.Pet;
import domainapp.dom.impl.pets.fixture.Owner_enum;
import domainapp.dom.impl.visits.contributions.Pet_visits;
import domainapp.dom.impl.visits.dom.Visit;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.isisaddons.module.fakedata.dom.FakeDataService;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class Visit_enterOutcome_IntegTest extends PetClinicModuleIntegTestAbstract {

    Visit visit;

    @Before
    public void setup() {
        // given
        Owner owner = runBuilderScript(Owner_enum.JOHN_SMITH);
        Pet pet = owner.getPets().first();
        visit = wrap(mixin(Pet_visits.class, pet)).coll().iterator().next();
    }

    @Test
    public void happy_case() {

        // when
        String diagnosis = someRandomDiagnosis();
        BigDecimal cost = someRandomCost();

        wrap(visit).enterOutcome(diagnosis, cost);

        // then
        assertThat(visit.getDiagnosis()).isEqualTo(diagnosis);
        assertThat(visit.getCost()).isEqualTo(cost);
    }

    @Test
    public void cannot_edit_outcome_directly() {

        // expecting
        expectedExceptions.expect(DisabledException.class);
        expectedExceptions.expectMessage("Use 'enter outcome' action");

        // when
        String diagnosis = someRandomDiagnosis();
        wrap(visit).setDiagnosis(diagnosis);
    }

    @Test
    public void cannot_edit_cost_directly() {

        // expecting
        expectedExceptions.expect(DisabledException.class);
        expectedExceptions.expectMessage("Use 'enter outcome' action");

        // when
        BigDecimal cost = someRandomCost();

        wrap(visit).setCost(cost);
    }

    private BigDecimal someRandomCost() {
        return new BigDecimal(20.00 + fakeDataService.doubles().upTo(30.00d));
    }

    private String someRandomDiagnosis() {
        return fakeDataService.lorem().paragraph(3);
    }

    @Inject
    FakeDataService fakeDataService;
}