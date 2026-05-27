pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/pranaybendle/NotesAutomationFramework.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Generate Allure Report') {
            steps {
                allure includeProperties: false,
                jdk: '',
                results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/screenshots/*.png', allowEmptyArchive: true
        }

        success {
            echo 'Build Successful!'
        }

        failure {
            echo 'Build Failed!'
        }
    }
}