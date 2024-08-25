def call () {
    stage('Compile') {
        // Set JDK tool 
        def jdkTool = tool 'Java17'
        withEnv(["JAVA_HOME=${jdkTool}"]) {
            sh './mvnw package' 
        }
    }
}