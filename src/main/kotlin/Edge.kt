package graph

import java.io.Serializable
import java.time.Duration
import java.time.Instant
import kotlin.math.exp
import kotlin.math.ln

sealed class Edge(
    open val to: Node,
    val type: String
) : Serializable {

    /** Weight of the edge. */
    abstract val weight: Double

    data class Viewed(override val to: Node, val timestamp: Instant = Instant.now()) :
        Edge(to, "Viewed") {
        override val weight: Double
            get() {
                val days = Duration.between(timestamp, Instant.now()).toDays().toDouble()
                return (0.1 - TERMINAL_WEIGHT) * exp(-DECAY_K * days)
            }
    }

    data class Bought(override val to: Node, val timestamp: Instant = Instant.now()) :
        Edge(to, "Bought") {
        override val weight: Double
            get() {
                val days = Duration.between(timestamp, Instant.now()).toDays().toDouble()
                return (0.4 - TERMINAL_WEIGHT) * exp(-DECAY_K * days)
            }
    }

    companion object {
        private const val TERMINAL_WEIGHT = 0.0
        private val DECAY_K: Double = ln(100.0) / 30.0
    }
}
