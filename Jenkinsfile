pipeline {
    agent any
    tools {
        gradle 'gradle'
    }
    stages {
        stage('저장소 복제') {
            steps {
                git branch: 'main', credentialsId: 'github', url: 'https://github.com/KoRakunnn/todaynan_server'
            }
        }
        stage('application properties 적용') {
            steps {
                withCredentials([file(credentialsId: 'application_properties', variable: 'SECRET_FILE')]) {
                    sh 'cp $SECRET_FILE ./src/main/resources/application.properties'
                }
            }
        }
        stage('빌드') {
            steps {
                sh "./gradlew clean build"
            }
        }
    }
}
