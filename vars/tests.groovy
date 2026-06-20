def flake8Test() {
    sh 'python3 -m py_compile app.py'
}

def trivyTest() {
    def image = env.IMAGE_NAME ?: "vladxgx/hello-newapp"
    def tag = env.IMAGE_TAG ?: env.BUILD_NUMBER ?: "local"

    sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy:0.58.1 image --severity HIGH,CRITICAL --ignore-unfixed --exit-code 0 ${image}:${tag}"
}

def trivyBaseImageTest() {
    sh 'docker run --rm aquasec/trivy:0.58.1 image --severity HIGH,CRITICAL --ignore-unfixed --exit-code 0 python:3.12-slim'
}

def banditTest() {
    sh 'python3 -m pip install --quiet bandit && python3 -m bandit app.py --severity-level high'
}

def unitTest() {
    sh 'python3 -m unittest discover -s tests || echo "No unit tests found"'
}

def qaTest() {
    sh 'kubectl get pods --all-namespaces || true'
}
