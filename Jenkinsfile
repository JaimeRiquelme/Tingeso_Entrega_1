pipeline{
    agent any
    tools{
        gradle 'gradle'
    }

    stages{
        stage('Build JAR File'){
            steps{
                checkout scmGit(branches: [[name: '/main']], extensions:[], userRemoteConfigs: [[url: 'https://github.com/JaimeRiquelme/Tingeso_Entrega_1']])
                dir("api"){
                    bat "gradle build"
                }
            }
        }
        stage('Test'){
            steps{
                dir("api"){
                    bat "gradle test"
                }
            }
        }
        stage('Deploy'){
            steps{
                dir("api"){
                    script{
                        withDockerRegistry(credentialsId: 'docker-credenciales'){
                            bat "docker build -t jaimeriquelme/backend-imagen ."
                            bat "docker push jaimeriquelme/backend-imagen"
                        }
                    }
                }
            }
        }
    }
}