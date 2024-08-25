def call () {
    stage('Push artifact to Nexus Repo') {
        def branch = env.BRANCH_NAME
        if (branch == 'release') {
            withCredentials([string(credentialsId: "${NEXUS_CRED}", variable: 'NEXUS_ACC')]) {
                sh "curl -v -u ${NEXUS_ACC} --upload-file target/spring-petclinic-3.1.0-SNAPSHOT.jar http://${NEXUS_URL}/repository/${NEXUS_PRO_REPO}/${NEXUS_GROUP}/${NEXUS_ARTIFACT_ID}/${env.BRANCH_NAME}/${env.VERSION}-${env.BRANCH_NAME}.jar"
            
        }
        if (branch.startsWith('uat')) {
            def GIT_HASH = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
            withCredentials([string(credentialsId: "${NEXUS_CRED}", variable: 'NEXUS_ACC')]) {
                sh "curl -v -u ${NEXUS_ACC} --upload-file target/spring-petclinic-3.1.0-SNAPSHOT.jar http://${NEXUS_URL}/repository/${NEXUS_PRO_REPO}/${NEXUS_GROUP}/${NEXUS_ARTIFACT_ID}/${env.BRANCH_NAME}/${env.VERSION}-${env.BRANCH_NAME}-${GIT_HASH}.jar"
            }
        }
    }
}