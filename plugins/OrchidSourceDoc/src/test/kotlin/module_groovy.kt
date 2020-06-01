package com.eden.orchid.sourcedoc

import com.eden.orchid.groovydoc.GroovydocGenerator
import com.eden.orchid.strikt.collectionWasCreated
import com.eden.orchid.strikt.pageWasRendered
import com.eden.orchid.testhelpers.OrchidIntegrationTest
import com.eden.orchid.testhelpers.TestResults
import strikt.api.Assertion

fun OrchidIntegrationTest.groovydocSetup(modules: List<String> = emptyList(), showRunnerLogs: Boolean = false) {
    val type = GroovydocGenerator.type
    val nodeKinds = GroovydocGenerator.nodeKinds
    val otherSourceKinds = GroovydocGenerator.otherSourceKinds

    if(modules.isEmpty()) {
        singleModuleSetup(type, showRunnerLogs, nodeKinds, otherSourceKinds, null)
    }
    else {
        multiModuleSetup(type, modules, showRunnerLogs, nodeKinds, otherSourceKinds)
    }
}

fun Assertion.Builder<TestResults>.assertGroovyPages(baseDir: String = "/groovydoc"): Assertion.Builder<TestResults> {
    return this
        .pageWasRendered("$baseDir/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/groovyannotation/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/groovyclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/groovyenumclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/groovyexceptionclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/groovyinterface/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/groovytrait/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javaannotation/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javaclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javaenumclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javaexceptionclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javainterface/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/index.html") { }
}

fun Assertion.Builder<TestResults>.assertGroovyPages(baseDirs: List<String>): Assertion.Builder<TestResults> {
    return baseDirs.fold(this) { acc, dir ->
        acc.assertGroovyPages("/groovydoc/$dir")
    }
}

fun Assertion.Builder<TestResults>.assertGroovyCollections(baseDirs: List<String> = emptyList()): Assertion.Builder<TestResults> {
    return if(baseDirs.isNotEmpty()) {
        baseDirs.fold(this) { acc, dir ->
            acc
                .collectionWasCreated(GroovydocGenerator.GENERATOR_KEY, dir)
                .collectionWasCreated(GroovydocGenerator.GENERATOR_KEY, "$dir-classes")
                .collectionWasCreated(GroovydocGenerator.GENERATOR_KEY, "$dir-packages")
        }.collectionWasCreated(GroovydocGenerator.GENERATOR_KEY, "modules")
    }
    else {
        this
            .collectionWasCreated(GroovydocGenerator.GENERATOR_KEY, "modules")
            .collectionWasCreated(GroovydocGenerator.GENERATOR_KEY, GroovydocGenerator.GENERATOR_KEY)
            .collectionWasCreated(GroovydocGenerator.GENERATOR_KEY, "classes")
            .collectionWasCreated(GroovydocGenerator.GENERATOR_KEY, "packages")
    }
}
