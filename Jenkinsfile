pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                sh "cd events-service;chmod +x gradlew;./gradlew test"
            }
        }
	}
     post {
            always {
                cleanWs()
            }
     }
}