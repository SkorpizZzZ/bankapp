pipeline {
    agent any
    options {
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    environment {
        DOCKER_VERSION = "0.1"
        KEYCLOAK_VERSION = "1.0"
    }

    stages {
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
                    docker build -t keycloak:${env.KEYCLOAK_VERSION} ./keycloak
                    minikube image load keycloak:${env.KEYCLOAK_VERSION}
                """
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    helm dependency update ./k8s/bank
                    helm upgrade --install bankapp ./k8s/bank
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
        sh 'chmod +x ./gradlew'
        sh './gradlew clean build'
    }
    sh "docker build -t ${imageName}:${version} ./${serviceName}"
    sh "minikube image load ${imageName}:${version}"
}