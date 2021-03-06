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
package domainapp.dom;

import com.google.common.collect.Sets;
import domainapp.dom.impl.pets.fixture.DeleteAllOwnersAndPets;
import domainapp.dom.impl.visits.fixture.DeleteAllVisits;
import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.isisaddons.module.fakedata.FakeDataModule;

import java.util.Set;

public class PetClinicModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.newHashSet(new FakeDataModule());
    }

    @Override
    public FixtureScript getRefDataSetupFixture() {
        // nothing currently
        return null;
    }

    @Override
    public FixtureScript getTeardownFixture() {
        return new FixtureScript() {
            @Override
            protected void execute(final ExecutionContext ec) {
                ec.executeChild(this, new DeleteAllVisits());
                ec.executeChild(this, new DeleteAllOwnersAndPets());
            }
        };
    }

}
