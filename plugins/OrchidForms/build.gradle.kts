apply(from = "$rootDir/gradle/groups/mainProjects.gradle")
apply(from = "$rootDir/gradle/groups/pluginProjects.gradle")

dependencies {
    testImplementation(Modules.OrchidPluginDocs)
}
