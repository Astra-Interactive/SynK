package com.astrainteractive.synk.api.remote.mapping

interface ExposedMapper<I, O> {
    fun toExposed(it: O): I.() -> Unit
}
