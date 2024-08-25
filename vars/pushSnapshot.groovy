def call () {
    def jdkTool = tool 'Java17'
    withEnv(["JAVA_HOME=${jdkTool}"]) {
        sh './mvnw clean deploy -Dmaven.test.skip=true' 
    }
}