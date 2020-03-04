/*
 * Copyright 2019 Wooga GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.wooga.spock.extensions.uvm.interceptor


import groovy.transform.InheritConstructors
import net.wooga.uvm.Installation
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.FieldInfo
import spock.lang.Specification

@InheritConstructors
abstract class UnityInstallationFieldInterceptor extends UnityInstallationManagingInterceptor<FieldInfo> {

    @Override
    Installation installUnity(IMethodInvocation invocation) {
        def installation = super.installUnity(invocation)
        def spec = getSpec(invocation)
        info.writeValue(spec, installation)
        installation
    }

    @Override
    void deleteInstallation(IMethodInvocation invocation) {
        Installation installation = getInstallation(invocation)
        installation.location.deleteDir()
    }

    protected Specification getSpec(IMethodInvocation invocation) {
        ((info.shared) ? invocation.sharedInstance : invocation.instance) as Specification
    }

    protected Installation getInstallation(IMethodInvocation invocation) {
        final specInstance = getSpec(invocation)
        info.readValue(specInstance) as Installation
    }
}
