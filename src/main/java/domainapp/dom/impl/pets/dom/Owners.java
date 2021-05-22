/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.dom.impl.pets.dom;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.datanucleus.query.typesafe.TypesafeQuery;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "pets.Owners"
)
public class Owners {

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @MemberOrder(sequence = "1")
    public Owner create(
            @Parameter(maxLength = 40)
            final String lastName,
            @Parameter(maxLength = 40)
            final String firstName,
            @Parameter(
                    mustSatisfy = Owner.PhoneNumberSpec.class,
                    maxLength = 15,
                    optionality = Optionality.OPTIONAL
            )
            final String phoneNumber) {
        Owner owner = new Owner(lastName, firstName);
        owner.setPhoneNumber(phoneNumber);
        return repositoryService.persist(owner);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @MemberOrder(sequence = "2")
    public List<Owner> findByName(
            final String name) {
        TypesafeQuery<Owner> q = isisJdoSupport.newTypesafeQuery(Owner.class);
        final domainapp.dom.impl.pets.dom.QOwner cand = domainapp.dom.impl.pets.dom.QOwner.candidate();
        q = q.filter(
                cand.lastName.indexOf(q.stringParameter("name")).ne(-1).or(
                cand.firstName.indexOf(q.stringParameter("name")).ne(-1)
                )
        );
        return q.setParameter("name", name)
                .executeList();
    }

    @Programmatic
    public Owner findByLastNameAndFirstName(
            final String lastName,
            final String firstName) {
        TypesafeQuery<Owner> q = isisJdoSupport.newTypesafeQuery(Owner.class);
        final domainapp.dom.impl.pets.dom.QOwner cand = domainapp.dom.impl.pets.dom.QOwner.candidate();
        q = q.filter(
                cand.lastName.eq(q.stringParameter("lastName")).and(
                        cand.firstName.eq(q.stringParameter("firstName"))
                )
        );
        return q.setParameter("lastName", lastName)
                .setParameter("firstName", firstName)
                .executeUnique();
    }

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    @MemberOrder(sequence = "3")
    public List<Owner> listAll() {
        return repositoryService.allInstances(Owner.class);
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
