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
package domainapp.dom.impl;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "pets" )
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column ="version")
@javax.jdo.annotations.Unique(name="Owner_lastName_firstName_UNQ", members = {"lastName", "firstName"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class Owner implements Comparable<Owner> {

    public Owner(final String lastName, final String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Property(hidden = Where.EVERYWHERE)
    @Getter @Setter
    private String lastName;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Property(hidden = Where.EVERYWHERE)
    @Getter @Setter
    private String firstName;

    public String title() {
        return getLastName() + ", " + getFirstName().substring(0,1);
    }

    @Property(notPersisted = true)
    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    @javax.jdo.annotations.Column(allowsNull = "true", length = 4000)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private String notes;
    public String getNotes() { return notes; }
    public void setNotes(final String notes) { this.notes = notes; }


    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    public Owner updateName(
            @Parameter(maxLength = 40)
            final String lastName,
            @Parameter(maxLength = 40)
            final String firstName) {
        setLastName(lastName);
        setFirstName(firstName);
        return this;
    }
    public String default0UpdateName() {
        return getLastName();
    }
    public String default1UpdateName() {
        return getFirstName();
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.removeAndFlush(this);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(final Owner other) {
        return ComparisonChain.start()
                .compare(this.getLastName(), other.getLastName())
                .compare(this.getFirstName(), other.getFirstName())
                .result();
    }


    @javax.jdo.annotations.NotPersistent
    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.jdo.annotations.NotPersistent
    @javax.inject.Inject
    TitleService titleService;

    @javax.jdo.annotations.NotPersistent
    @javax.inject.Inject
    MessageService messageService;


}