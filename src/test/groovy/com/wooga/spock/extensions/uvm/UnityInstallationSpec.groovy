package com.wooga.spock.extensions.uvm


import spock.lang.Shared
import spock.lang.Specification

class UnityInstallationSpec extends Specification {

    @Shared
    @UnityInstallation(version = "2019.3.3f1", cleanup = true)
    def installation2019

    def "installs unity with requested version"() {
        expect:
        installation2019.location.exists()
    }
}
