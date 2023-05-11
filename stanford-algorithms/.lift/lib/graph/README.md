# Graph Types

| Class                          | Edges      | Self-loops | Multiple edge | Weighted |
|--------------------------------|------------|------------|---------------|----------|
| SimpleGraph                    | undirected | no         | no            | no       |
| MultiGraph                     | undirected | no         | yes           | no       |
| PseudoGraph                    | undirected | yes        | yes           | no       |
| DefaultUndirectedGraph         | undirected | yes        | no            | no       |
| SimpleWeightedGraph            | undirected | no         | no            | yes      |
| WeightedMultigraph             | undirected | no         | yes           | yes      |
| WeightedPseudograph            | undirected | yes        | yes           | yes      |
| DefaultUndirectedWeightedGraph | undirected | yes        | no            | yes      |
| SimpleDirectedGraph            | directed   | no         | no            | no       |
| DirectedMultigraph             | directed   | no         | yes           | no       |
| DirectedPseudograph            | directed   | yes        | yes           | no       |
| DefaultDirectedGraph           | directed   | yes        | no            | no       |
| SimpleDirectedWeightedGraph    | directed   | no         | no            | yes      |
| DirectedWeightedMultigraph     | directed   | no         | yes           | yes      |
| DirectedWeightedPseudograph    | directed   | yes        | yes           | yes      |
| DefaultDirectedWeightedGraph   | directed   | yes        | no            | yes      |
