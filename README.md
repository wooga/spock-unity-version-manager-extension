spock-unity-version-manager-extension
=====================================

[![Coverage Status](https://coveralls.io/repos/github/wooga/spock-unity-version-manager-extension/badge.svg?branch=master)](https://coveralls.io/github/wooga/spock-unity-version-manager-extension?branch=master)

@UnityInstallation
------------------

This spock extension allows to install Unity versions before test runs. The return value will be a `Installation` object with information about the location und path to the unity executable.
Be aware the installation process can be very time consuming.

### Example

```groovy
class ExampleSpec extends Specification {
    @Shared
    @UnityInstallation(version="2018.4.12f1")
    Installation unity

    @Shared
    @UnityInstallation(version = "2019.3.3f1", modules = [Component.android, Component.ios])
    Installation unityWithModules

    def "execute unity build"() {
        expect:
        unity.location.exists()
        unityWithModules.location.exists()
    }
}
```

It is also possible to inject a test repository directly into a feature method

```groovy
    @UnityInstallation(version="2018.4.12f1")
    @Unroll
    def "execute unity build"(Installation unity) {
        expect:
        unity.location.exists()
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
```

The `@GithubRepository` supports the following parameters:

| name                      | default                          | description                                                                                         |
| ------------------------- | -------------------------------- | --------------------------------------------------------------------------------------------------- |
| version                   |                                  | The version of unity to be installed                                                                | 
| basePath                  |                                  | An optional base path to install unity to. The final destination will be `${basePath}/Unity-${version}` |
| modules                   | []                               | A list of `Component` to be installed                                                                  |
| cleanup                   | false                            | Delete the version after the test run. The version will always be installed in temporary location when the value is `true` |

LICENSE
=======

Copyright 2020 Wooga GmbH

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


[unity-version-manager]: github.com/Larusso/unity-version-manager
