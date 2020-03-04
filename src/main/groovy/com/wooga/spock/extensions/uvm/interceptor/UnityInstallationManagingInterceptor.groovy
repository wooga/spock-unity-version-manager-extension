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
import net.wooga.uvm.UnityVersionManager
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.NodeInfo

@InheritConstructors
abstract class UnityInstallationManagingInterceptor<T extends NodeInfo> extends AbstractMethodInterceptor {

    protected final UnityInstallation metadata

    private T info

    T getInfo() {
        this.info
    }

    UnityInstallationManagingInterceptor(UnityInstallation metadata) {
        super()
        this.metadata = metadata
    }

    void install(T info) {
        this.info = info
    }

    Installation installUnity(IMethodInvocation invocation) {
        File destination = null
        if(metadata.basePath() != "") {
            destination = new File(metadata.basePath(), "Unity-${metadata.version()}")
        } else if (metadata.cleanup()) {
            destination = File.createTempDir(this.metadata.version(), "")
        }

        if(destination) {
            UnityVersionManager.installUnityEditor(metadata.version(), destination, metadata.modules())
        } else {
            UnityVersionManager.installUnityEditor(metadata.version(), this.metadata.modules())
        }

    }

    abstract void deleteInstallation(IMethodInvocation invocation)
}