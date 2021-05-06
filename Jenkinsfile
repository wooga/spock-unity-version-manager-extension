#!groovy
@Library('github.com/wooga/atlas-jenkins-pipeline@1.x') _

withCredentials([string(credentialsId: 'spock_uvm_extension_coveralls_token', variable: 'coveralls_token')]) {
    buildJavaLibraryOSSRH coverallsToken: coveralls_token, testEnvironment: []
}
