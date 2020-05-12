@NonCPS
def printParams() {
  env.getEnvironment().each { name, value -> println "Name: $name -> Value $value" }
}

node {
    tool name: 'Gradle 5.6.2', type: 'gradle'
    stage('Preparation') {
        // printParams() // For Debugging if needed
        // Get some code from a GitHub repository
        checkout scm
    }
    buildStep('Build') {
        // Run the build
        if (isUnix()) {
            sh 'pwd && scl_source enable devtoolset-7 && ./gradlew clean build assemble --scan'
        } else {
            bat(/gradlew.bat clean build assemble/)
        }
    }
    buildStep('Results') {
        junit testResults: '**/target/surefire-reports/TEST-*.xml', allowEmptyResults: true
        archiveArtifacts '**/build/libs/*.war'
    }
    buildStep('Deploy') {
        if ("master".equalsIgnoreCase(env.BRANCH_NAME) || "buildtest".equalsIgnoreCase(env.BRANCH_NAME)) {
            withCredentials([usernamePassword(credentialsId: 'f127a3ae-2e5c-4add-b9fc-fbf569082ac2', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                sh 'scl_source enable devtoolset-7 && ./gradlew deployDev -Dorg.gradle.project.wildflyUsername=$USERNAME -Dorg.gradle.project.wildflyPassword=$PASSWORD'
            }
        } else if ("prod".equalsIgnoreCase(env.BRANCH_NAME)) {
            withCredentials([usernamePassword(credentialsId: 'f127a3ae-2e5c-4add-b9fc-fbf569082ac2', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                sh 'scl_source enable devtoolset-7 && ./gradlew deployProd -Dorg.gradle.project.wildflyUsername=$USERNAME -Dorg.gradle.project.wildflyPassword=$PASSWORD'
            }
        } else {
            println("Not deploying Branch: " + env.BRANCH_NAME)
        }
    }
    findBuildScans()

    setBuildStatus("Build complete", "SUCCESS");
}

void buildStep(String message, Closure closure) {
    stage(message) {
        try {
            setBuildStatus(message, "PENDING");
            closure();
        } catch (Exception e) {
            setBuildStatus(message, "FAILURE");
            throw e
        }
    }
}

def getCommitHash() {
    sh(script: "git rev-parse HEAD", returnStdout: true).trim()
}

void setBuildStatus(message, state) {
    step([
        $class: "GitHubCommitStatusSetter",
        commitShaSource: [$class: "ManuallyEnteredShaSource", sha: getCommitHash()],
        reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/bdaroz/teamy-main"],
        errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
        statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
    ]);
}