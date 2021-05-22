package domainapp.dom.impl.visits.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import domainapp.dom.impl.visits.dom.Visit;

public class DeleteAllVisits extends TeardownFixtureAbstract2 {
    @Override
    protected void execute(final ExecutionContext ec) {
        deleteFrom(Visit.class);
    }
}