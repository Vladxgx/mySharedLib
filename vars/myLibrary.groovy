def buildApp() {
    if (fileExists('Dockerfile')) {
        sh 'docker build -t "${JOB_NAME}:${BUILD_NUMBER}" .'
    } else {
        sh 'python3 -m py_compile *.py || true'
    }
}

def deployApp(String branchName) {
    sh "git checkout ${branchName}"
    if (fileExists('devops-template.yaml')) {
        sh 'kubectl apply -f devops-template.yaml'
    } else if (fileExists('k8s')) {
        sh 'kubectl apply -f k8s/'
    } else {
        echo 'No Kubernetes manifest found, skipping deploy'
    }
}

def cleanup() {
    deleteDir()
}
