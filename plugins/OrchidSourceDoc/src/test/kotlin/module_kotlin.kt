package com.eden.orchid.sourcedoc

import com.eden.orchid.kotlindoc.KotlindocGenerator
import com.eden.orchid.strikt.collectionWasCreated
import com.eden.orchid.strikt.pageWasRendered
import com.eden.orchid.testhelpers.OrchidIntegrationTest
import com.eden.orchid.testhelpers.TestResults
import strikt.api.Assertion

fun OrchidIntegrationTest.kotlindocSetup(modules: List<String> = emptyList(), showRunnerLogs: Boolean = false) {
    val type = KotlindocGenerator.type
    val nodeKinds = KotlindocGenerator.nodeKinds
    val otherSourceKinds = KotlindocGenerator.otherSourceKinds

    if(modules.isEmpty()) {
        singleModuleSetup(type, showRunnerLogs, nodeKinds, otherSourceKinds, null)
    }
    else {
        multiModuleSetup(type, modules, showRunnerLogs, nodeKinds, otherSourceKinds)
    }
}

fun Assertion.Builder<TestResults>.assertKotlinPages(baseDir: String = "/kotlindoc"): Assertion.Builder<TestResults> {
    return this
        .pageWasRendered("$baseDir/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/customstring/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javaannotation/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javaclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javaenumclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javaexceptionclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/javainterface/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlinannotation/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlinclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlinclassgenerics/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlinclasswithcompanionobject/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlinclasswithlibraryclasses/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlinenumclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlinexceptionclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlininlineclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlininterface/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlinobjectclass/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlinsealedclass1/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlinsealedclass2/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlinsealedclass3/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/kotlinsealedclasses/index.html") { }
        .pageWasRendered("$baseDir/com/eden/orchid/mock/index.html") { }
}

fun Assertion.Builder<TestResults>.assertKotlinPages(baseDirs: List<String>): Assertion.Builder<TestResults> {
    return baseDirs.fold(this) { acc, dir ->
        acc.assertKotlinPages("/kotlindoc/$dir")
    }
}

fun Assertion.Builder<TestResults>.assertKotlinCollections(baseDirs: List<String> = emptyList()): Assertion.Builder<TestResults> {
    return if (baseDirs.isNotEmpty()) {
        baseDirs.fold(this) { acc, dir ->
            acc
                .collectionWasCreated(KotlindocGenerator.GENERATOR_KEY, dir)
                .collectionWasCreated(KotlindocGenerator.GENERATOR_KEY, "$dir-classes")
                .collectionWasCreated(KotlindocGenerator.GENERATOR_KEY, "$dir-packages")
        }.collectionWasCreated(KotlindocGenerator.GENERATOR_KEY, "modules")
    } else {
        this
            .collectionWasCreated(KotlindocGenerator.GENERATOR_KEY, "modules")
            .collectionWasCreated(KotlindocGenerator.GENERATOR_KEY, KotlindocGenerator.GENERATOR_KEY)
            .collectionWasCreated(KotlindocGenerator.GENERATOR_KEY, "classes")
            .collectionWasCreated(KotlindocGenerator.GENERATOR_KEY, "packages")
    }
}
