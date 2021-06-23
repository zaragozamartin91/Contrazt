pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'jenkins_hp', url: 'git@github.com:zaragozamartin91/Contrazt.git']]])
                sh 'chmod +x mvnw'
            }
        }
        stage('Clean') {
            steps {
                sh './mvnw clean'
            }
        }
        stage('Compile') {
            steps {
                sh './mvnw compile'
            }
        }
        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }
    }
}
