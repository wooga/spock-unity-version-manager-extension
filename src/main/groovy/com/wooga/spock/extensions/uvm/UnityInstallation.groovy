package com.wooga.spock.extensions.uvm

import net.wooga.uvm.Component
import org.spockframework.runtime.extension.ExtensionAnnotation

import java.lang.annotation.*

@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.FIELD, ElementType.METHOD])
@ExtensionAnnotation(UnityInstallationExtension.class)
@interface UnityInstallation {
    /**
     * The version of unity to be installed.
     *
     * A valid unity version:
     * @{code {major}.{minor}.{patch}(f|p|b|a){release}}
     */
    String version();

    /**
     * An optional base path to install unity to.
     *
     * The final destination will be {@code ${basePath}/Unity-${version}}
     */
    String basePath() default "build/unity"

    /**
     * A list of {@code Component} to be installed.                                                                  |
     */
    Component[] modules() default [];

    /**
     * Delete the version after the test run.
     *
     * The version will always be installed in temporary location when the value is {@code true}
     */
    boolean cleanup() default false;
}
