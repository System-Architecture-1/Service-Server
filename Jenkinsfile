pipeline {
    agent any
    tools {
        gradle 'gradle'
    }
    environment {
            DOCKERHUB_CREDENTIALS = 'docker'  // DockerHub 자격 증명 ID
            DOCKER_IMAGE_NAME = 'kohyunchoel/todaynan_backend'
            GITHUB_CREDENTIALS = 'github'
            GITHUB_URL = 'https://github.com/KoRakunnn/todaynan_server'
            APPLICATION_PROPERTIES = 'application_properties'
            INSTANCE_SSH_CREDENTIALS = 'instance_ssh_key'
        }
    stages {
        stage('Git Clone') {
            steps {
                git branch: 'main', credentialsId: "$GITHUB_CREDENTIALS", url: "$GITHUB_URL"
            }
        }
        stage('Apply application properties') {
            steps {
                withCredentials([file(credentialsId: "$APPLICATION_PROPERTIES", variable: 'SECRET_FILE')]) {
                    sh 'cp $SECRET_FILE ./src/main/resources/application.properties'
                }
            }
        }

        stage('Build') {
            steps {
                sh "./gradlew clean build"
            }
        }

        stage('Docker Build') {
            steps {
                 dir("/var/jenkins_home/workspace/todaynan") {
                     sh '''
                         echo 'Building Docker image...'
                         docker build -t $DOCKER_IMAGE_NAME:latest .
                     '''
                 }
            }
        }

        stage('Docker Hub Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS}", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                    sh '''
                        echo 'Logging into DockerHub...'
                        echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                        docker push $DOCKER_IMAGE_NAME:latest
                    '''
                }
            }
        }

        stage('Deploy to Instance') {
            steps {
                sshagent(credentials: ["${INSTANCE_SSH_CREDENTIALS}"]) {
                    sh '''
                        echo 'Deploying Docker container on Oracle instance...'
                        ssh -o StrictHostKeyChecking=no ubuntu@{Private 또는 Public IP} "
                        docker pull $DOCKER_IMAGE_NAME:latest && \
                        docker stop spring || true && \
                        docker rm spring || true && \
                        docker run -d --name spring -p 8080:8080 $DOCKER_IMAGE_NAME:latest
                            "
                    '''
                }
            }
        }
    }
}
