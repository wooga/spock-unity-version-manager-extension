package com.wooga.spock.extensions.uvm

import net.wooga.uvm.Installation
import net.wooga.uvm.UnityVersionManager
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class UnityInstallationFeatureSpec extends Specification {
    def setupSpec() {
        if (UnityVersionManager.locateUnityInstallation("2019.3.3f1")) {
            UnityVersionManager.locateUnityInstallation("2019.3.3f1").location.deleteDir()
        }
    }

    def "before feature spec (unity is not installed)"() {
        expect:
        !UnityVersionManager.locateUnityInstallation("2019.3.3f1")
    }

    @UnityInstallation(version = "2019.3.3f1", cleanup = true)
    def "feature has unity installed before execution"(Installation installation) {
        expect:
        installation.location.exists()
        UnityVersionManager.locateUnityInstallation("2019.3.3f1")
    }

    def "after feature spec (unity is not installed)"() {
        expect:
        !UnityVersionManager.locateUnityInstallation("2019.3.3f1")
    }

    @UnityInstallation(version = "2019.3.3f1", cleanup = true)
    def "feature has unity installed before execution"(String a, String b, Installation installation) {
        expect:
        installation.location.exists()
        UnityVersionManager.locateUnityInstallation("2019.3.3f1")

        where:
        a      | b
        "test" | "test2"
        installation = null
    }
}
