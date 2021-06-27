pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'jenkins_hp', url: 'git@github.com:zaragozamartin91/Contrazt.git']]])
                sh 'chmod +x mvnw'
            }
        }
        stage('Compile') {
            steps {
                sh './mvnw clean compile'
            }
        }
        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Sonar') {
            steps {
                withCredentials([string(credentialsId: 'sonar_contrazt_token', variable: 'SONAR_TOKEN')]) {
                    sh './mvnw sonar:sonar -Dsonar.projectKey=contrazt -Dsonar.host.url=http://sonar-service:9000 -Dsonar.login=$SONAR_TOKEN'
                }
            }
        }
    }
}
