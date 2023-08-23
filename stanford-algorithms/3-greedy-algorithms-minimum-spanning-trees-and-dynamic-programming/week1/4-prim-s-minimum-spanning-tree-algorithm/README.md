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

Maintain two sets of vertices. The first set contains the vertices already included in the MST, the other set contains the vertices not yet included. At every step, consider all the edges that connect the two sets and picks the minimum weight edge from these edges. After picking the edge, move the other endpoint of the edge to the set containing MST.

## Pseudo Code

Given undirected graph $G = (V, E)$

- Initialize: $X = \{s \in V\}$ and $T = \varnothing$
- While $X \neq V$:
  - Let $e = (u, v)$ be the cheapest edge of $G$ with $u \in X$ and $v \notin X$
  - add $e$ to $T$, add $v$ to $X$

## Alternative Algorithms

- Kruskal's MST Algorithm
