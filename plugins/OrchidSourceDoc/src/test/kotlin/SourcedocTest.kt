package com.eden.orchid.sourcedoc

import com.eden.orchid.groovydoc.GroovydocGenerator
import com.eden.orchid.javadoc.JavadocGenerator
import com.eden.orchid.kotlindoc.KotlindocGenerator
import com.eden.orchid.plugindocs.PluginDocsModule
import com.eden.orchid.strikt.asExpected
import com.eden.orchid.strikt.assertWhen
import com.eden.orchid.strikt.noOtherCollectionsCreated
import com.eden.orchid.strikt.nothingElseRendered
import com.eden.orchid.swiftdoc.SwiftdocGenerator
import com.eden.orchid.testhelpers.OrchidIntegrationTest
import com.eden.orchid.testhelpers.withGenerator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledForJreRange
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.JRE
import org.junit.jupiter.api.condition.OS

class SourcedocTest : OrchidIntegrationTest(SourceDocModule(), PluginDocsModule()) {

    val modules = listOf("one", "two")

    @BeforeEach
    internal fun setUp() {
        testCss()
        testPageStructure()
    }

// Tests
//----------------------------------------------------------------------------------------------------------------------

    @Test
    @DisabledForJreRange(min = JRE.JAVA_12)
    fun `Single-module Java`() {
        javadocSetup()
        execute(withGenerator<JavadocGenerator>())
            .asExpected()
            .withDefaultSourcedocPages()
            .assertJavaPages()
            .assertJavaCollections()
            .nothingElseRendered()
            .noOtherCollectionsCreated()
    }

    @Test
    @DisabledForJreRange(min = JRE.JAVA_12)
    fun `Multi-module Java`() {
        javadocSetup(modules)
        execute(withGenerator<JavadocGenerator>())
            .asExpected()
            .withDefaultSourcedocPages()
            .assertJavaPages(modules)
            .assertJavaCollections(modules)
            .nothingElseRendered()
            .noOtherCollectionsCreated()
    }

    @Test
    fun `Single-module Groovy`() {
        groovydocSetup()
        execute(withGenerator<GroovydocGenerator>())
            .asExpected()
            .withDefaultSourcedocPages()
            .assertGroovyPages()
            .assertGroovyCollections()
            .nothingElseRendered()
            .noOtherCollectionsCreated()
    }

    @Test
    fun `Multi-module Groovy`() {
        groovydocSetup(modules)
        execute(withGenerator<GroovydocGenerator>())
            .asExpected()
            .withDefaultSourcedocPages()
            .assertGroovyPages(modules)
            .assertGroovyCollections(modules)
            .nothingElseRendered()
            .noOtherCollectionsCreated()
    }

    @Test
    fun `Single-module Kotlin`() {
        kotlindocSetup()
        execute(withGenerator<KotlindocGenerator>())
            .asExpected()
            .withDefaultSourcedocPages()
            .assertKotlinPages()
            .assertKotlinCollections()
            .nothingElseRendered()
            .noOtherCollectionsCreated()
    }

    @Test
    fun `Multi-module Kotlin`() {
        kotlindocSetup(modules)
        execute(withGenerator<KotlindocGenerator>())
            .asExpected()
            .withDefaultSourcedocPages()
            .assertKotlinPages(modules)
            .assertKotlinCollections(modules)
            .nothingElseRendered()
            .noOtherCollectionsCreated()
    }

    @Test
    @EnabledOnOs(OS.MAC)
    fun `Single-module Swift`() {
        swiftdocSetup()
        execute(withGenerator<SwiftdocGenerator>())
            .asExpected()
            .withDefaultSourcedocPages()
            .assertSwiftPages()
            .assertSwiftCollections()
            .nothingElseRendered()
            .noOtherCollectionsCreated()
    }

    @Test
    @EnabledOnOs(OS.MAC)
    fun `Multi-module Swift`() {
        swiftdocSetup(modules)
        execute(withGenerator<SwiftdocGenerator>())
            .asExpected()
            .withDefaultSourcedocPages()
            .assertSwiftPages(modules)
            .assertSwiftCollections(modules)
            .nothingElseRendered()
            .noOtherCollectionsCreated()
    }

    @Test
    fun `Single-module all kinds`() {
        if(JRE.JAVA_12.isCurrentVersion) {
            javadocSetup()
        }
        groovydocSetup()
        kotlindocSetup()
        if (OS.MAC.isCurrentOs) {
            swiftdocSetup()
        }

        execute(
            *mutableListOf(
                withGenerator<GroovydocGenerator>(),
                withGenerator<KotlindocGenerator>()
            )
                .addWhen(JRE.JAVA_12.isCurrentVersion) { withGenerator<JavadocGenerator>() }
                .addWhen(OS.MAC.isCurrentOs) { withGenerator<SwiftdocGenerator>() }
                .toTypedArray()
        )
            .asExpected()
            .withDefaultSourcedocPages()
            .assertWhen(JRE.JAVA_12.isCurrentVersion) { assertJavaPages() }
            .assertWhen(JRE.JAVA_12.isCurrentVersion) { assertJavaCollections() }
            .assertGroovyPages()
            .assertGroovyCollections()
            .assertKotlinPages()
            .assertKotlinCollections()
            .assertWhen(OS.MAC.isCurrentOs) { assertSwiftPages() }
            .assertWhen(OS.MAC.isCurrentOs) { assertSwiftCollections() }
            .nothingElseRendered()
            .noOtherCollectionsCreated()
    }

    @Test
    fun `Multi-module all kinds`() {
        val generators = mutableListOf(
            withGenerator<GroovydocGenerator>(),
            withGenerator<KotlindocGenerator>()
        )
            .addWhen(JRE.JAVA_12.isCurrentVersion) { withGenerator<JavadocGenerator>() }
            .addWhen(OS.MAC.isCurrentOs) { withGenerator<SwiftdocGenerator>() }

        if(JRE.JAVA_12.isCurrentVersion) {
            javadocSetup(modules)
        }
        groovydocSetup(modules)
        kotlindocSetup(modules)
        if (OS.MAC.isCurrentOs) {
            swiftdocSetup(modules)
        }

        execute(*generators.toTypedArray())
            .asExpected()
            .withDefaultSourcedocPages()
            .assertWhen(JRE.JAVA_12.isCurrentVersion) { assertJavaPages(modules) }
            .assertWhen(JRE.JAVA_12.isCurrentVersion) { assertJavaCollections(modules) }
            .assertGroovyPages(modules)
            .assertGroovyCollections(modules)
            .assertKotlinPages(modules)
            .assertKotlinCollections(modules)
            .assertWhen(OS.MAC.isCurrentOs) { assertSwiftPages(modules) }
            .assertWhen(OS.MAC.isCurrentOs) { assertSwiftCollections(modules) }
            .nothingElseRendered()
            .noOtherCollectionsCreated()
    }
}

fun <T> MutableList<T>.addWhen(condition: Boolean, block: () -> T): MutableList<T> {
    if (condition) {
        add(block())
    }
    return this
}
