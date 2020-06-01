package com.eden.orchid.kotlindoc

import com.eden.orchid.sourcedoc.model.SourceDocModuleConfig
import javax.inject.Inject
import javax.inject.Named

class KotlinDocModuleConfig
@Inject
constructor(
    @Named("kotlindocClasspath") private val kotlindocClasspath: String
) : SourceDocModuleConfig(KotlindocGenerator.GENERATOR_KEY) {

    override fun additionalRunnerArgs(): List<String> = if (kotlindocClasspath.isNotBlank()) listOf(
        "-classpath",
        kotlindocClasspath,
        *args.toTypedArray()
    ) else args
}
