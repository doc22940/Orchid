package com.eden.orchid.groovydoc

import com.eden.orchid.api.generators.OrchidGenerator
import com.eden.orchid.api.registration.OrchidModule
import com.eden.orchid.utilities.addToSet

class GroovydocModule : OrchidModule() {
    override fun configure() {
        addToSet<OrchidGenerator<*>, GroovydocGenerator>()
    }
}
