package com.eden.orchid.sourcedoc

import com.eden.orchid.javadoc.JavadocGenerator
import com.eden.orchid.strikt.collectionWasCreated
import com.eden.orchid.strikt.pageWasRendered
import com.eden.orchid.testhelpers.OrchidIntegrationTest
import com.eden.orchid.testhelpers.TestResults
import strikt.api.Assertion

fun OrchidIntegrationTest.javadocSetup(modules: List<String> = emptyList(), showRunnerLogs: Boolean = false) {
    val type = JavadocGenerator.type
    val nodeKinds = JavadocGenerator.nodeKinds
    val otherSourceKinds = JavadocGenerator.otherSourceKinds

    if(modules.isEmpty()) {
        singleModuleSetup(type, showRunnerLogs, nodeKinds, otherSourceKinds, null)
    }
    else {
        multiModuleSetup(type, modules, showRunnerLogs, nodeKinds, otherSourceKinds)
    }
}

fun Assertion.Builder<TestResults>.assertJavaPages(baseDir: String = "/javadoc"): Assertion.Builder<TestResults> {
    return this
        .pageWasRendered("$baseDir/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javaannotation/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javaclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javaenumclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javaexceptionclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javainterface/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/index.html") { }
}

fun Assertion.Builder<TestResults>.assertJavaPages(baseDirs: List<String>): Assertion.Builder<TestResults> {
    return baseDirs.fold(this) { acc, dir ->
        acc.assertJavaPages("/javadoc/$dir")
    }
}

fun Assertion.Builder<TestResults>.assertJavaCollections(baseDirs: List<String> = emptyList()): Assertion.Builder<TestResults> {
    return if(baseDirs.isNotEmpty()) {
        baseDirs.fold(this) { acc, dir ->
            acc
                .collectionWasCreated(JavadocGenerator.GENERATOR_KEY, dir)
                .collectionWasCreated(JavadocGenerator.GENERATOR_KEY, "$dir-classes")
                .collectionWasCreated(JavadocGenerator.GENERATOR_KEY, "$dir-packages")
        }.collectionWasCreated(JavadocGenerator.GENERATOR_KEY, "modules")
    }
    else {
        this
            .collectionWasCreated(JavadocGenerator.GENERATOR_KEY, "modules")
            .collectionWasCreated(JavadocGenerator.GENERATOR_KEY, JavadocGenerator.GENERATOR_KEY)
            .collectionWasCreated(JavadocGenerator.GENERATOR_KEY, "classes")
            .collectionWasCreated(JavadocGenerator.GENERATOR_KEY, "packages")
    }
}
