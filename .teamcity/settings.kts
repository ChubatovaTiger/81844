import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.parallelTests
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetBuild
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetTest
import jetbrains.buildServer.configs.kotlin.buildSteps.nuGetInstaller
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.05"

project {

    vcsRoot(HttpsGitJetbrainsTeamTcHermioneScreenshotsGit)

    buildType(Build)
    buildType(Composite)
    buildType(Test)
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        dotnetBuild {
            projects = "TW-81844-reproduce.sln"
            sdk = "6"
        }
        dotnetTest {
            enabled = false
            projects = "TW-81844-reproduce.Test/TW-81844-reproduce.Test.csproj"
            sdk = "6"
        }
        nuGetInstaller {
            enabled = false
            toolPath = "%teamcity.tool.NuGet.CommandLine.DEFAULT%"
            projects = "TW-81844-reproduce.sln"
            updatePackages = updateParams {
            }
        }
    }

    requirements {
        contains("system.agent.name", "JS")
    }
})

object Composite : BuildType({
    name = "Composite"

    type = BuildTypeSettings.Type.COMPOSITE

    vcs {
        root(DslContext.settingsRoot)

        showDependenciesChanges = true
    }

    triggers {
        vcs {
            enabled = false
        }
    }

    dependencies {
        snapshot(Build) {
        }
        snapshot(Test) {
        }
    }
})

object Test : BuildType({
    name = "Test"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        dotnetBuild {
            enabled = false
            projects = "TW-81844-reproduce.sln"
            sdk = "6"
        }
        dotnetTest {
            projects = "TW-81844-reproduce.Test/TW-81844-reproduce.Test.csproj"
            sdk = "6"
        }
        nuGetInstaller {
            enabled = false
            toolPath = "%teamcity.tool.NuGet.CommandLine.DEFAULT%"
            projects = "TW-81844-reproduce.sln"
            updatePackages = updateParams {
            }
        }
    }

    features {
        parallelTests {
            numberOfBatches = 2
        }
    }

    dependencies {
        snapshot(Build) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }

    requirements {
        contains("system.agent.name", "JS")
    }
})

object HttpsGitJetbrainsTeamTcHermioneScreenshotsGit : GitVcsRoot({
    name = "https://git.jetbrains.team/tc/hermione-screenshots.git"
    url = "https://git.jetbrains.team/tc/TeamCity.git"
    branch = "refs/heads/master"
    checkoutPolicy = GitVcsRoot.AgentCheckoutPolicy.NO_MIRRORS
    authMethod = password {
        userName = "nastasia.chubatova@jetbrains.com"
        password = "credentialsJSON:b4761266-b893-462e-86af-222386a14083"
    }
})
