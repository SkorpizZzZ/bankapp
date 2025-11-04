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
        
        // Observability Stack versions
        ZIPKIN_VERSION = "3.4"
        PROMETHEUS_VERSION = "v2.48.0"
        GRAFANA_VERSION = "10.2.2"
        ELASTICSEARCH_VERSION = "8.11.0"
        KIBANA_VERSION = "8.11.0"
        LOGSTASH_VERSION = "8.11.0"
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

        stage('Prepare Observability Stack') {
            parallel {
                stage('Prepare Zipkin') {
                    steps {
                        sh """
                            docker pull openzipkin/zipkin:${env.ZIPKIN_VERSION}
                            minikube image load openzipkin/zipkin:${env.ZIPKIN_VERSION}
                        """
                    }
                }
                stage('Prepare Prometheus') {
                    steps {
                        sh """
                            docker pull prom/prometheus:${env.PROMETHEUS_VERSION}
                            minikube image load prom/prometheus:${env.PROMETHEUS_VERSION}
                        """
                    }
                }
                stage('Prepare Grafana') {
                    steps {
                        sh """
                            docker pull grafana/grafana:${env.GRAFANA_VERSION}
                            minikube image load grafana/grafana:${env.GRAFANA_VERSION}
                        """
                    }
                }
                stage('Prepare Elasticsearch') {
                    steps {
                        sh """
                            docker pull elasticsearch:${env.ELASTICSEARCH_VERSION}
                            minikube image load elasticsearch:${env.ELASTICSEARCH_VERSION}
                        """
                    }
                }
                stage('Prepare Kibana') {
                    steps {
                        sh """
                            docker pull kibana:${env.KIBANA_VERSION}
                            minikube image load kibana:${env.KIBANA_VERSION}
                        """
                    }
                }
                stage('Prepare Logstash') {
                    steps {
                        sh """
                            docker pull logstash:${env.LOGSTASH_VERSION}
                            minikube image load logstash:${env.LOGSTASH_VERSION}
                        """
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    helm dependency update ./k8s/bank
                    helm upgrade --install bank-app ./k8s/bank --namespace bankapp --create-namespace --timeout 15m
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    echo 'Waiting for pods to be ready...'
                    sh '''
                        kubectl wait --for=condition=ready pod -l app.kubernetes.io/instance=bank-app -n bankapp --timeout=300s || true
                        echo "=== Deployment Status ==="
                        kubectl get pods -n bankapp
                        echo ""
                        echo "=== Services ==="
                        kubectl get svc -n bankapp
                        echo ""
                        echo "=== Observability Stack Status ==="
                        kubectl get pods -n bankapp | grep -E "(zipkin|prometheus|grafana|elasticsearch|kibana|logstash)" || echo "Observability pods not found"
                    '''
                }
            }
        }
    }

    post {
        always {
            cleanWs()
            echo 'Build completed'
        }
        success {
            echo '=========================================='
            echo 'Application deployed successfully!'
            echo '=========================================='
            echo ''
            echo 'Observability Stack URLs:'
            echo '  Grafana:        minikube service bank-app-grafana-service -n bankapp'
            echo '  Prometheus:     minikube service bank-app-prometheus-service -n bankapp'
            echo '  Zipkin:         minikube service bank-app-zipkin-service -n bankapp'
            echo '  Kibana:         minikube service bank-app-kibana-service -n bankapp'
            echo ''
            echo 'Application Services:'
            echo '  Front Service:  minikube service bank-app-front-service -n bankapp'
            echo ''
            echo 'Useful Commands:'
            echo '  View pods:      kubectl get pods -n bankapp'
            echo '  View services:  kubectl get svc -n bankapp'
            echo '  View logs:      kubectl logs -f <pod-name> -n bankapp'
            echo '=========================================='
        }
        failure {
            echo 'Build failed!'
            sh 'kubectl get pods -n bankapp || true'
            sh 'kubectl get events -n bankapp --sort-by=.lastTimestamp | tail -20 || true'
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