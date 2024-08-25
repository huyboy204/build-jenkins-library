def call () {
    stage('Build Docker Image') {
        def branch = env.BRANCH_NAME
        if (branch == 'release') {
            sh "docker build -t ${env.NEXUS_URL_DOCKER}/web:${env.VERSION}-${env.BRANCH_NAME} ."
        }
        if (branch.startsWith('uat')) {
            def GIT_HASH = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
            sh "docker build -t ${env.NEXUS_URL_DOCKER}/web:${env.VERSION}-${env.BRANCH_NAME}-${GIT_HASH} ."
        }
    }
}