package com.wooga.spock.extensions.uvm

import net.wooga.uvm.Component
import net.wooga.uvm.Installation
import spock.lang.Shared
import spock.lang.Specification

class UnityInstallationModulesSpec extends Specification {

    @Shared
    @UnityInstallation(version = "2019.3.3f1", cleanup = true, modules = [Component.android, Component.ios])
    Installation installationWithModules

    @Shared
    @UnityInstallation(version = "2018.4.18f1", cleanup = true)
    Installation installationWithoutModules


    def "installs unity with requested version"() {
        expect:
        installationWithModules.location.exists()
        installationWithoutModules.location.exists()
        installationWithModules.components.toList().containsAll([Component.android, Component.ios])
        !installationWithoutModules.components.toList().containsAll([Component.android, Component.ios])
    }
}
