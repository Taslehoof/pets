package domainapp.dom.impl.dashboard;

import domainapp.dom.impl.pets.dom.Owner;
import domainapp.dom.impl.pets.dom.Owners;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;

import java.util.List;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "dashboard.Dashboard"
)
public class Dashboard {

    public String title() { return getOwners().size() + " owners"; }

    @CollectionLayout(defaultView = "table")
    public List<Owner> getOwners() {
        return owners.listAll();
    }

    @javax.inject.Inject
    Owners owners;
}
