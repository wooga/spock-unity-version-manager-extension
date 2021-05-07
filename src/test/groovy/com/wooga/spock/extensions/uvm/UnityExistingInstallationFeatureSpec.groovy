package com.wooga.spock.extensions.uvm

import net.wooga.uvm.Installation
import net.wooga.uvm.UnityVersionManager
import spock.lang.Shared
import spock.lang.Specification

class UnityExistingInstallationFeatureSpec extends Specification {

    @UnityInstallation(version = "2018.4.14f1", cleanup = true)
    Installation installation
    @Shared
    File customUnityInstallDir = new File("build/unity/custom/Unity").absoluteFile

    def setupSpec() {
        UnityVersionManager.installUnityEditor("2018.4.14f1", customUnityInstallDir)
    }

    def cleanupSpec() {
        assert customUnityInstallDir.exists()
    }

    def "feature finds existing unity installation"() {
        expect:
        customUnityInstallDir.exists()
        installation.location == customUnityInstallDir
    }

}
