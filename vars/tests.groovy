def flake8Test() {
    sh '''
        if ls *.py >/dev/null 2>&1; then
            python3 -m pip install --quiet --target .tools/flake8 flake8
            PYTHONPATH=.tools/flake8 python3 -m flake8 . --select=E9,F63,F7,F82
        else
            echo "No Python files found, skipping Flake8"
        fi
    '''
}

def trivyTest() {
    def image = env.IMAGE_NAME ?: "vladxgx/hello-newapp"
    def tag = env.IMAGE_TAG ?: env.BUILD_NUMBER ?: "local"

    sh """
        if ! docker image inspect ${image}:${tag} >/dev/null 2>&1; then
            echo "Image ${image}:${tag} not found, skipping Trivy"
            exit 0
        fi

        docker run --rm \
            -v /var/run/docker.sock:/var/run/docker.sock \
            aquasec/trivy:0.58.1 image \
            --severity HIGH,CRITICAL \
            --ignore-unfixed \
            --exit-code 0 \
            ${image}:${tag}
    """
}

def banditTest() {
    sh '''
        if ls *.py >/dev/null 2>&1; then
            python3 -m pip install --quiet --target .tools/bandit bandit
            PYTHONPATH=.tools/bandit python3 -m bandit -r . --severity-level high
        else
            echo "No Python files found, skipping Bandit"
        fi
    '''
}

def sonarQubeTest() {
    def scannerHome = tool 'SonarQubeScanner'
    withSonarQubeEnv('SonarQubeScanner') {
        sh """
            ${scannerHome}/bin/sonar-scanner \
            -Dsonar.projectKey=${env.JOB_NAME} \
            -Dsonar.projectName=${env.JOB_NAME} \
            -Dsonar.sources=. \
            -Dsonar.sourceEncoding=UTF-8
        """
    }
}

def unitTest() {
    sh '''
        if [ -d tests ]; then
            python3 -m unittest discover -s tests
        else
            echo "No tests directory found, skipping unit tests"
        fi
    '''
}

def qaTest() {
    sh 'kubectl get pods --all-namespaces || true'
}
