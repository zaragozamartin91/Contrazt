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
                sh './mvnw sonar:sonar -Dsonar.projectKey=contrazt -Dsonar.host.url=http://sonar-service:9000 -Dsonar.login=36060419677b4c37197d1d6688d79e7a90495757'
            }
        }
    }
}
