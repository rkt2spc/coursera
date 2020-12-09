# Nuts and bolts

A disorganized carpenter has a mixed pile of n nuts and n bolts. The goal is to find the corresponding pairs of nuts and bolts. Each nut fits exactly one bolt and each bolt fits exactly one nut. By fitting a nut and a bolt together, the carpenter can see which one is bigger (but the carpenter cannot compare two nuts or two bolts directly). Design an algorithm for the problem that uses at most proportional to n.log(n) compares (probabilistically).

## Practices

- https://www.lintcode.com/problem/nuts-bolts-problem

## Hints

- This [research paper](https://web.cs.ucla.edu/~rafail/PUBLIC/17.pdf) gives an algorithm that runs in O(n.log^4(n)) time.
