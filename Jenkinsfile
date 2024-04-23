pipeline {
    agent any

    stages {
        stage('Build JAR File') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/JaimeRiquelme/Tingeso_Entrega_1']])
                dir("api/demo") {
                    bat ".\\gradlew clean build"  // Cambia a bat y usa las barras invertidas para Windows
                }
            }
        }
        stage('Test') {
            steps {
                dir("api/demo") {
                    bat ".\\gradlew test"  // Cambia a bat y usa las barras invertidas para Windows
                }
            }
        }
        stage('Deploy') {
            steps {
                dir("api/demo") {
                    script {
                        withDockerRegistry(credentialsId: 'docker-credenciales') {
                            bat "docker build -t jaimeriquelme/backend-imagen ."
                            bat "docker push jaimeriquelme/backend-imagen"
                        }
                    }
                }
            }
        }
    }
}
