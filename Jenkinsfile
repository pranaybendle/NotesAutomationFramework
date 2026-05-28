```groovy
pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/pranaybendle/NotesAutomationFramework.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {

                script {

                    try {

                        bat 'mvn test'

                    } catch (Exception e) {

                        currentBuild.result = 'UNSTABLE'

                        echo 'Some tests failed. Marking build as UNSTABLE.'

                    }

                }

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
            archiveArtifacts artifacts: 'target/screenshots/*.png',
            allowEmptyArchive: true
        }

        success {
            echo 'Build Successful!'
        }

        unstable {
            echo 'Build Unstable - Some tests failed!'
        }

        failure {
            echo 'Build Failed!'
        }
    }
}
```
