private String imageName() {
    return env.IMAGE_NAME ?: "vladxgx/hello-newapp"
}

private String imageTag() {
    return env.IMAGE_TAG ?: env.BUILD_NUMBER ?: "local"
}

def imagePush(){
    if (!fileExists('Dockerfile')) {
        echo 'No Dockerfile found, skipping Docker push'
        return
    }

    sh "docker push ${imageName()}:${imageTag()}"
}

def imagePull(){
    sh "docker pull ${imageName()}:${imageTag()}"
}

def imageBuild(){
    if (!fileExists('Dockerfile')) {
        echo 'No Dockerfile found, skipping Docker build'
        return
    }

    sh "docker build -t ${imageName()}:${imageTag()} ."
}

def containerRun(String ports = "8000:8000") {
    sh "docker run -d --name ${env.JOB_NAME}-${imageTag()} -p ${ports} ${imageName()}:${imageTag()}"
}

def containerStop() {
    sh "docker rm -f ${env.JOB_NAME}-${imageTag()} || true"
}
