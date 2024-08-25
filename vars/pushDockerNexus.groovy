def call () {
    stage('Push Docker Image') {
        def branch = env.BRANCH_NAME
        if (branch == 'release') {
            withCredentials([usernamePassword(credentialsId: 'nexus-credential', passwordVariable: 'PSW', usernameVariable: 'USER')]) {
                sh "echo $PSW | docker login -u $USER --password-stdin ${env.NEXUS_URL_DOCKER}"
                sh "docker push ${env.NEXUS_URL_DOCKER}/web:${env.VERSION}-${env.BRANCH_NAME}"
            }
        }
        if (branch.startsWith('uat')) {
            def GIT_HASH = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
            withCredentials([usernamePassword(credentialsId: 'nexus-credential', passwordVariable: 'PSW', usernameVariable: 'USER')]) {
                sh "echo $PSW | docker login -u $USER --password-stdin ${env.NEXUS_URL_DOCKER}"
                sh "docker push ${env.NEXUS_URL_DOCKER}/web:${env.VERSION}-${env.BRANCH_NAME}-${GIT_HASH}"
            }
        }
    }
}