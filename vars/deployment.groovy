def deployK8s() {
    if (fileExists('devops-template.yaml')) {
        sh 'kubectl apply -f devops-template.yaml'
    } else if (fileExists('k8s')) {
        sh 'kubectl apply -f k8s/'
    } else {
        echo 'No Kubernetes manifest found, skipping deploy'
    }
}
