pipeline {
    agent any

    stages {
        stage('Build JAR File') {
            steps {
                checkout scmGit(branches: [[name: 'refs/heads/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/JaimeRiquelme/Tingeso_Entrega_1']])
                dir("api/demo") {
                    sh "./gradlew clean build"
                }
            }
        }
        stage('Test') {
            steps {
                dir("api/demo") {
                    sh "./gradlew test"
                }
            }
        }
        stage('Deploy') {
            steps {
                dir("api/demo") {
                    script {
                        withDockerRegistry(credentialsId: 'docker-credenciales') {
                            sh "docker build -t jaimeriquelme/backend-imagen ."
                            sh "docker push jaimeriquelme/backend-imagen"
                        }
                    }
                }
            }
        }
    }
}
