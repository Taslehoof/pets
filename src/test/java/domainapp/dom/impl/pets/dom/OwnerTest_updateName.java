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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OwnerTest_updateName {

    @Test
    public void happy_case() throws Exception {
        // given
        final Owner object = new Owner("Smith","John");
        assertThat(object.getFirstName()).isEqualTo("John");
        assertThat(object.getLastName()).isEqualTo("Smith");

        // when
        object.updateName("Jones", "Chuck");

        // then
        assertThat(object.getFirstName()).isEqualTo("Chuck");
        assertThat(object.getLastName()).isEqualTo("Jones");
    }

}