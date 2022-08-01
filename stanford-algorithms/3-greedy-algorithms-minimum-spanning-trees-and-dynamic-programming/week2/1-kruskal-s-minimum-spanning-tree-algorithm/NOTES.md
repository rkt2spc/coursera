# Prim's Minimum Spanning Tree (MST) Algorithm

## Problem Definition

Connect all points in an graph together as cheaply as possible

## Formal Definition

**Input:** Undirected graph $G = (V, E)$ and a cost $c_e$ for each edge $e \in E$

**Output:** A minimum cost tree $T \subseteq E$ that span all vertices.

- $T$ has no cycles
- The subgraph $(V, T)$ is connected
- The sum of all the edges weight in the subgraph $(V, T)$ is minimal

## Implementation Idea

Continue adding edges to the MST, from the smallest weight to the largest weight, skipping edges that create cycles.

## Pseudo Code

Given undirected graph $G = (V, E)$

- Initialize: $T = \varnothing$
- Sort $E$ in order of increasing cost
- for each edge $e \in E$ in the sorted order:
  - If $T \cup \{e\}$ has no cycles:
    - add $e$ to $T$
- return $T$
