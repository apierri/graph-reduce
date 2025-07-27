package graph

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class EdgeComparatorTest {
    @Test
    fun `compare edges by weight`() {
        val e1 = Edge.Viewed(Node.Item("i1"))
        val e2 = Edge.Bought(Node.Item("i2"))
        assertTrue(EdgeWeightComparator.compare(e1, e2) < 0)
    }
}
