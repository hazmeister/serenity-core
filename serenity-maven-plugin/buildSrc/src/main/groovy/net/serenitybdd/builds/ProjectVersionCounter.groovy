package net.serenitybdd.builds

class ProjectVersionCounter {
    Boolean isRelease
    Boolean useCurrentVersion = false

    def getCurrentVersionTag() {
        def currentVersion = "git describe --tags".execute().text
        if (currentVersion.isEmpty()) {
            currentVersion = "v1.0.0"
        }
        return currentVersion
    }

    def getNextVersion() {
        def matcher = currentVersionTag =~ "\\d+"
        def majorMinorNumbers = matcher[0] + "." + matcher[1]
        def currentBuildNumber = Integer.valueOf(matcher[2])
        def nextBuildNumber = (useCurrentVersion) ? currentBuildNumber : currentBuildNumber + 1
        return (isRelease) ?
                majorMinorNumbers + "." + nextBuildNumber :
                majorMinorNumbers + "." + nextBuildNumber + "-SNAPSHOT"
    }

    def getCurrentVersion() {
        def matcher = currentVersionTag =~ "\\d+"
        def majorMinorNumbers = matcher[0] + "." + matcher[1]
        def currentBuildNumber = Integer.valueOf(matcher[2])
        return majorMinorNumbers + "." + currentBuildNumber
    }
}
