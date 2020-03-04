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

package com.wooga.spock.extensions.uvm

import com.wooga.spock.extensions.uvm.interceptor.SharedUnityInstallationInterceptor
import com.wooga.spock.extensions.uvm.interceptor.UnityInstallationFeatureInterceptor
import com.wooga.spock.extensions.uvm.interceptor.UnityInstallationInterceptor
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.FieldInfo

class UnityInstallationExtension extends AbstractAnnotationDrivenExtension<UnityInstallation> {

    @Override
    void visitFeatureAnnotation(UnityInstallation annotation, FeatureInfo feature) {
        def interceptor

        interceptor = new UnityInstallationFeatureInterceptor(annotation)
        interceptor.install(feature)
    }

    @Override
    void visitFieldAnnotation(UnityInstallation annotation, FieldInfo field) {
        def interceptor

        if (field.isShared()) {
            interceptor = new SharedUnityInstallationInterceptor(annotation)
        } else {
            interceptor = new UnityInstallationInterceptor(annotation)
        }

        interceptor.install(field)
    }
}



