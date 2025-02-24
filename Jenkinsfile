pipeline {
    agent any
    environment {
        IMAGE_NAME = "faserver/store-0.0.1:latest"
        IMAGE_TAG = "latest"
        KUBE_DEPLOYMENT = "k8s-deployment.yaml"
    }
    stages {
        stage('Checkout Code') {
            steps {
               git branch 'main' 'https://github.com/Fadi-Alzoubi-1/jenkins.git'
            }
        }
        stage('Build Maven Project') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }
        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'Fastserverdd@25', url: 'docker.io']) {
                    sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                sh "kubectl apply -f ${KUBE_DEPLOYMENT}"
            }
        }
    }
}
