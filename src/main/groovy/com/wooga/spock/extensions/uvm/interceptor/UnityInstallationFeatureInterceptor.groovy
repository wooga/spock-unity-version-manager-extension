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

import com.wooga.spock.extensions.uvm.UnityInstallation
import groovy.transform.InheritConstructors
import net.wooga.uvm.Installation
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.FeatureInfo

import java.lang.reflect.Parameter

@InheritConstructors
class UnityInstallationFeatureInterceptor extends UnityInstallationManagingInterceptor<FeatureInfo> {

    UnityInstallationFeatureInterceptor(UnityInstallation metadata) {
        super(metadata)
    }

    Installation installation

    private static void injectInstallations(IMethodInvocation invocation, Installation installation) {
        Map<Parameter, Integer> parameters = [:]
        invocation.method.reflection.parameters.eachWithIndex { parameter, i ->
            parameters << [(parameter): i]
        }
        parameters = parameters.findAll { Installation.equals it.key.type}

        // enlarge arguments array if necessary
        def lastMyInjectableParameterIndex = parameters*.value.max()
        lastMyInjectableParameterIndex = lastMyInjectableParameterIndex == null ?
                0 :
                lastMyInjectableParameterIndex + 1

        if (invocation.arguments.length < lastMyInjectableParameterIndex) {
            def newArguments = new Object[lastMyInjectableParameterIndex]
            System.arraycopy invocation.arguments, 0, newArguments, 0, invocation.arguments.length
            invocation.arguments = newArguments
        }

        parameters.each { parameter, i ->
            if(!invocation.arguments[i]) {
                invocation.arguments[i] = installation
            }
        }
    }

    //execute feature
    @Override
    void interceptFeatureMethod(IMethodInvocation invocation) throws Throwable {
        injectInstallations(invocation, installation)
        invocation.proceed()
    }

    //NEW ITERATION
    @Override
    void interceptIterationExecution(IMethodInvocation invocation) throws Throwable {
        try {
            invocation.proceed()
        }
        finally {

        }
    }

    @Override
    void interceptSetupMethod(IMethodInvocation invocation) throws Throwable {
        invocation.proceed()
        installation = installUnity(invocation)

        invocation.spec.setupInterceptors.remove(this)
    }

    //SETUP FEATURE
    @Override
    void interceptFeatureExecution(IMethodInvocation invocation) throws Throwable {
        invocation.spec.addSetupInterceptor(this)

        try {
            invocation.proceed()
        }
        finally {
            deleteInstallation(invocation)
        }
    }

    @Override
    void install(FeatureInfo info) {
        super.install(info)
        info.addInterceptor(this)
        info.addIterationInterceptor(this)
        info.featureMethod.addInterceptor(this)
    }


    @Override
    void deleteInstallation(IMethodInvocation invocation) {
        installation.location.deleteDir()
    }
}