package domainapp.dom.impl.visits;


import org.joda.time.LocalDateTime;

import org.junit.Test;

import org.apache.isis.applib.services.wrapper.InvalidException;

import domainapp.dom.impl.PetClinicModuleIntegTestAbstract;
import domainapp.dom.impl.pets.dom.Owner;
import domainapp.dom.impl.pets.dom.Pet;

import domainapp.dom.impl.pets.fixture.Owner_enum;
import domainapp.dom.impl.visits.contributions.Pet_bookVisit;
import domainapp.dom.impl.visits.dom.Visit;

import static org.assertj.core.api.Assertions.assertThat;

public class Pet_bookVisit_IntegTest extends PetClinicModuleIntegTestAbstract {

    @Test
    public void happy_case() {

        // given
        runFixtureScript(Owner_enum.FRED_HUGHES.builder());

        Owner owner = Owner_enum.FRED_HUGHES.findUsing(serviceRegistry);
        Pet pet = owner.getPets().first();
        Pet_bookVisit mixin = factoryService.mixin(Pet_bookVisit.class, pet);

        // when
        LocalDateTime default0Act = mixin.default0Act();
        String reason = "off her food";
        Visit visit = wrap(mixin).act(default0Act, reason);

        // then
        assertThat(visit.getPet()).isEqualTo(pet);
        assertThat(visit.getVisitAt()).isEqualTo(default0Act);
        assertThat(visit.getReason()).isEqualTo(reason);
    }

    @Test
    public void reason_is_required() {

        // given
        runFixtureScript(Owner_enum.FRED_HUGHES.builder());

        Owner owner = Owner_enum.FRED_HUGHES.findUsing(serviceRegistry);
        Pet pet = owner.getPets().first();
        Pet_bookVisit mixin = factoryService.mixin(Pet_bookVisit.class, pet);

        // expect
        expectedExceptions.expect(InvalidException.class);
        expectedExceptions.expectMessage("Mandatory");

        // when
        LocalDateTime default0Act = mixin.default0Act();
        String reason = null;
        wrap(mixin).act(default0Act, reason);
    }
}
