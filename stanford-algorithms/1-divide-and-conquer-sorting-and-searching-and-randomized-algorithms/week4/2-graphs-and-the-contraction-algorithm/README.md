# Graph

Vertices (Nodes) and Edges :D

## Cut

Split a graph into 2 non-empty sets of vertices

## MinCut

Split a graph into 2 non-empty sets of vertices (cuts) such that the number of edges connecting the 2 sets are minimum.

## Representation: Adjacency Matrix

A_ij = 1 <=> There's an edge between V_i and V_j

**Variants:**

- A_ij = number of edges between V_i and V_j
- A_ij = weight of the edge between V_i and V_j

## Representation: Adjacency List

Array (or list) of vertices
Array (or list) of edges

Each edge points to its endpoints
Each vertex points to edges incident of it
