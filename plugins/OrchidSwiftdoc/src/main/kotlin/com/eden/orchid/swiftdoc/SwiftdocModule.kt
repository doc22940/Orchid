package com.eden.orchid.swiftdoc

import com.eden.orchid.api.generators.OrchidGenerator
import com.eden.orchid.api.registration.OrchidModule
import com.eden.orchid.utilities.addToSet

class SwiftdocModule : OrchidModule() {
    override fun configure() {
        addToSet<OrchidGenerator<*>, SwiftdocGenerator>()
    }
}
