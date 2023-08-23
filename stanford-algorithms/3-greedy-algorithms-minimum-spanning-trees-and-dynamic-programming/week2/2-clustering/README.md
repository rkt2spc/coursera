# Clustering

## Problem Definition

Given n "points" (web pages, images, genome fragments, etc) classify them into "coherent" group.

Assumptions:

- Also given as input, a (dis)similarity measure function: $distance(p, q)$
  - Examples: Euclidean Distance, Genome Similarity, etc

Goal:

- Group "nearby" elements into the same cluster.

## Max-Spacing k-Clustering

Assume we know $k \coloneqq \text{number of clusters desired}$

Point $p$ and $q$ are considered **separated** if they are assigned to different clusters.

The spacing of a k-clustering is:

$$\min\limits_{\text{separated p, q}} distance(p, q)$$

**Problem Statement:** Given a distance measure $d$ and the desired number of clusters $k$, compute the k-clustering with maximum spacing.

**Implementation Idea:**

Initialize each element as its own cluster. Find the pair of elements with the shortest distance, merge their clusters into one. Repeat this process until the number of clusters reach the desired number $k$

$\rarr$ Single-link clustering
