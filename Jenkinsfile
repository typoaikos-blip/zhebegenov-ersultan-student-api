pipeline {
    agent any

    environment {
        STUDENT_NAME    = 'Ersultan'
        STUDENT_SURNAME = 'Zhebegenov'
        IMAGE_NAME      = 'zhebegenov-ersultan-student-api'
        IMAGE_TAG       = 'v1.0-zhebegenov-ersultan'
    }

    triggers {
        pollSCM('H/2 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "Student: ${STUDENT_NAME} ${STUDENT_SURNAME}"
                sh 'git log -1 --oneline'
            }
        }
        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build -x test --no-daemon'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test --no-daemon'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
                }
            }
        }
        stage('Docker Build') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }
        stage('Verify') {
            steps {
                sh "docker image inspect ${IMAGE_NAME}:${IMAGE_TAG} > /dev/null"
                sh "docker images ${IMAGE_NAME}"
                echo "Image ${IMAGE_NAME}:${IMAGE_TAG} built for ${STUDENT_NAME} ${STUDENT_SURNAME}"
            }
        }
    }

    post {
        success { echo "Pipeline of ${STUDENT_SURNAME} ${STUDENT_NAME} finished successfully" }
        failure { echo "Pipeline of ${STUDENT_SURNAME} ${STUDENT_NAME} FAILED" }
    }
}
