pipeline {
    agent any
    options {
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    environment {
        PATH = "/opt/homebrew/bin:/usr/local/bin:${env.PATH}"
        DOCKER_VERSION = "1.0"
        KEYCLOAK_VERSION = "1.0"
    }

    stages {

        stage('Checkout SCM') {
                    steps {
                        checkout scm
                    }
                }

        stage('Build All Services') {
            parallel {
                stage('Account Service') {
                    steps {
                        script {
                            buildService('account', 'account-service', env.DOCKER_VERSION)
                        }
                    }
                }
                stage('Blocker Service') {
                    steps {
                        script {
                            buildService('blocker', 'blocker-service', env.DOCKER_VERSION)
                        }
                    }
                }
                stage('Cash Service') {
                    steps {
                        script {
                            buildService('cash', 'cash-service', env.DOCKER_VERSION)
                        }
                    }
                }
                stage('Exchange Service') {
                    steps {
                        script {
                            buildService('exchange', 'exchange-service', env.DOCKER_VERSION)
                        }
                    }
                }
                stage('Exchange Generator') {
                    steps {
                        script {
                            buildService('exchangeGenerator', 'exchange-generator-service', env.DOCKER_VERSION)
                        }
                    }
                }
                stage('Front Service') {
                    steps {
                        script {
                            buildService('front', 'front-service', env.DOCKER_VERSION)
                        }
                    }
                }
                stage('Notification Service') {
                    steps {
                        script {
                            buildService('notification', 'notification-service', env.DOCKER_VERSION)
                        }
                    }
                }
                stage('Transfer Service') {
                    steps {
                        script {
                            buildService('transfer', 'transfer-service', env.DOCKER_VERSION)
                        }
                    }
                }
            }
        }

        stage('Build Keycloak') {
            steps {
                sh """
                    docker build -t keycloak-service:${env.KEYCLOAK_VERSION} ./keycloak
                    minikube image load keycloak-service:${env.KEYCLOAK_VERSION}
                """
            }
        }

        stage('Prepare Database') {
            steps {
                sh """
                    docker pull docker.io/bitnamilegacy/postgresql:16.2.0-debian-12-r5
                    minikube image load docker.io/bitnamilegacy/postgresql:16.2.0-debian-12-r5
                """
            }
        }

        stage('Prepare Zookeeper') {
            steps {
                sh """
                    docker pull docker.io/bitnamilegacy/zookeeper:3.9.3-debian-12-r22
                    minikube image load docker.io/bitnamilegacy/zookeeper:3.9.3-debian-12-r22
                """
            }
        }

        stage('Prepare Kafka') {
            steps {
                sh """
                    docker pull docker.io/bitnamilegacy/kafka:3.3.2
                    minikube image load docker.io/bitnamilegacy/kafka:3.3.2
                """
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    helm dependency update ./k8s/bank
                    helm upgrade --install bank-app ./k8s/bank
                '''
            }
        }
    }

    post {
        always {
            cleanWs()
            echo 'Build completed'
        }
        success {
            echo 'Application deployed successfully!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}

def buildService(serviceName, imageName, version) {
    dir(serviceName) {
        sh '../gradlew clean build'
    }
    sh "docker build -t ${imageName}:${version} ./${serviceName}"
    sh "minikube image load ${imageName}:${version}"
}