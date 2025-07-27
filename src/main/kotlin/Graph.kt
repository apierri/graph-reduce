package graph

interface Graph {
    fun addEdge(from: Node, edge: Edge)
    fun getEdges(from: Node): Collection<Edge>
}
