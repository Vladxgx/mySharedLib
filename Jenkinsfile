@Library('mySharedLib') _

pipeline {
    agent any
    stages {
        stage('SCM pull') {
            steps {
                script {
                    sourceControl.gitPull()
                }
            }
        }
        stage('Parallel Tests') {
            parallel {
                stage('build') {
                    steps {
                        script {
                            dockering.imageBuild()
                        }
                    }
                }
                stage('trivy test') {
                    steps {
                        script {
                            tests.trivyTest()
                        }
                    }
                }
                stage('bandit test') {
                    steps {
                        script {
                            tests.banditTest()
                        }
                    }
                }
                stage('sonarqube') {
                    steps {
                        script {
                            tests.sonarQubeTest()
                        }
                        sh "exit 0" // Or exit 1 to breake the pipeline
                    }
                }
            }
        }

        stage('Parallel 2') {
            parallel {
                stage('docker push') {
                    steps {
                        script {
                            dockering.imagePush()
                        }
                    }
                }
                stage('unit test') {
                    steps {
                        script {
                            tests.unitTest()
                        }
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    deployment.deployK8s()
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    tests.qaTest()
                }
            }
        }
    }
    post {
        always {
            echo 'Cleaning up workspace...'
            echo 'Sending email...'
        }
    }
}
