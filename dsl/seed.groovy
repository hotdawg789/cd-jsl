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

def emailjob() {
    job("emailjoba") {
        publishers {
                extendedEmail {
                recipientList('hotdawg789@gmail.com')
                attachBuildLog(true)
                    triggers {
                        failure {
                        subject(" disk running out of space")
                        content("\${FILE,path=\"bingo.json\"}")
                        sendTo{
                            recipientList()
                        }
                        }
                    }
                }
                wsCleanup()
        }
        properties {
            rebuild {
                rebuildDisabled(false)
            }
        }
        concurrentBuild(true)
        steps {
            shell {
            command(""" 
#!/bin/bash
echo "{I can  without double quotes}" >> ./bingo.json
if ! [[ -f ./DISKSasdfasdfPACEDOUT.json ]]; then
    exit 1
fi
            """)
            }
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