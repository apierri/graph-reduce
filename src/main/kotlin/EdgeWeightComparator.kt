package graph

/**
 * Comparator for [Edge]s based on their weight.
 */
object EdgeWeightComparator : Comparator<Edge> {
    override fun compare(o1: Edge, o2: Edge): Int = o1.weight.compareTo(o2.weight)
}
