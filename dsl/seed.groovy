def createDeploymentJob(jobName, repoUrl) {
    pipelineJob(jobName) {
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(repoUrl)
                        }
                        branches('master')
                        extensions {
                            cleanBeforeCheckout()
                        }
                    }
                }
                scriptPath("Jenkinsfile")
            }
        }
    }
}

def createTestJob(jobName, repoUrl) {
    multibranchPipelineJob(jobName) {
        branchSources {
            git {
                remote(repoUrl)
                includes('*')
            }
        }
        triggers {
            cron("H/5 * * * *")
        }
    }
}

def buildPipelineJobs() {
    //def repo = "https://github.com/"
    // def repoUrl = repo + jobName + ".git"
    def repoUrl = "https://github.com/hotdawg789/cd-jsl.git"
    def deployName = jobName + "_deploy"
    def testName = jobName + "_test"

    createDeploymentJob(deployName, repoUrl)
    createTestJob(testName, repoUrl)
}

buildPipelineJobs()