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
f
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

version = "2022.04"

project {

    buildType(Test_2)
    buildType(Build)
    buildType(Composite)
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(DslContext.settingsRoot)
    }

    requirements {
        contains("system.agent.name", "JS")
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
        }
    }
})

object Composite : BuildType({
    name = "Composite"

    vcs {
        root(DslContext.settingsRoot)
    }

    type = BuildTypeSettings.Type.COMPOSITE

    vcs {
        showDependenciesChanges = true
    }

    triggers {
        vcs {
        }
    }

    dependencies {
        snapshot(Build) {
        }
        snapshot(Test_2) {
        }
    }
})

object Test_2 : BuildType({
    id("Test")
    name = "Test"

    vcs {
        root(DslContext.settingsRoot)
    }

    requirements {
        contains("system.agent.name", "dev-tc-test2")
    }

    features {
        parallelTests {
            numberOfBatches = 2
        }
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
        }
    }



    dependencies {
        snapshot(Build) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})

