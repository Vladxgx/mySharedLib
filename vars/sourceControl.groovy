def gitPull(){
    sh 'git fetch --all --prune && git status --short --branch'
}

def gitClone(String repoUrl = '', String directory = '') {
    if (!repoUrl?.trim()) {
        error 'gitClone requires a repository URL'
    }

    if (directory?.trim()) {
        sh "git clone '${repoUrl}' '${directory}'"
    } else {
        sh "git clone '${repoUrl}'"
    }
}

def gitPush(){
    sh 'git push'
}

def gitStatus(){
    sh 'git status --short --branch'
}
