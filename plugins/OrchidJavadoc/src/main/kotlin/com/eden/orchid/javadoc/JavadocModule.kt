package com.eden.orchid.javadoc

import com.eden.orchid.api.generators.OrchidGenerator
import com.eden.orchid.api.registration.OrchidModule
import com.eden.orchid.utilities.addToSet

class JavadocModule : OrchidModule() {
    override fun configure() {
        addToSet<OrchidGenerator<*>, JavadocGenerator>()
    }
}
