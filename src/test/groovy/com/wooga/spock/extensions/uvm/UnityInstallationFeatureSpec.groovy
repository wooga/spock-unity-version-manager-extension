package com.wooga.spock.extensions.uvm

import net.wooga.uvm.Installation
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class UnityInstallationFeatureSpec extends Specification {
    def setupSpec() {
        if (unityInstallDir.exists()) {
            unityInstallDir.deleteDir()
        }
    }

    @Shared
    File unityInstallDir = new File("build/unity/Unity-2019.3.3f1").absoluteFile

    def "before feature spec (unity is not installed)"() {
        expect:
        !unityInstallDir.exists()
    }

    @UnityInstallation(version = "2019.3.3f1", cleanup = true)
    def "feature has unity installed before execution"(Installation installation) {
        expect:
        unityInstallDir.exists()
        installation.location == unityInstallDir
    }

    def "after feature spec (unity is not installed)"() {
        expect:
        !unityInstallDir.exists()
    }

    @UnityInstallation(version = "2019.3.3f1", cleanup = true)
    def "feature has unity installed before execution"(String a, String b, Installation installation) {
        expect:
        unityInstallDir.exists()
        installation.location == unityInstallDir
        where:
        a      | b
        "test" | "test2"
        installation = null
    }
}
