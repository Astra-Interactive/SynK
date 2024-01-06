package com.astrainteractive.synk.api.remote.mapping

internal interface ExposedMapper<I, O> {
    fun toExposed(it: O): I.() -> Unit
}
