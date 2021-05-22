package domainapp.dom.impl.integtest;

import domainapp.dom.impl.PetClinicModuleIntegTestAbstract;
import domainapp.dom.impl.dashboard.Dashboard;
import domainapp.dom.impl.dashboard.HomePageProvider;
import domainapp.dom.impl.pets.fixture.Owner_enum;
import domainapp.dom.impl.visits.dom.Visit;
import org.apache.isis.applib.fixturescripts.setup.PersonaEnumPersistAll;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Dashboard_paid_IntegTest extends PetClinicModuleIntegTestAbstract {

    Dashboard dashboard;

    @Before
    public void setup() {
        // given
        runFixtureScript(new PersonaEnumPersistAll<>(Owner_enum.class));
        dashboard = homePageProvider.dashboard();
    }

    @Test
    public void happy_case() {

        // given
        List<Visit> overdue = dashboard.getOverdue();
        assertThat(overdue).isNotEmpty();

        // when
        wrap(dashboard).paid(overdue);

        // then
        List<Visit> overdueAfter = dashboard.getOverdue();
        assertThat(overdueAfter).isEmpty();

        for (Visit visit : overdue) {
            assertThat(visit.getDiagnosis()).isNotNull();
            assertThat(visit.getPaidOn()).isNotNull();
        }
    }

    @Inject
    HomePageProvider homePageProvider;
}