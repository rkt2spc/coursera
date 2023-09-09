# NP Complete Problems

## Polynomial-Time Solvability

A problem is polynomial-time solvable if there is an algorithm that correctly solves it in $O(n^k)$ time, for some constant $k$

## The Class P

$P$ = the set of polynomial-time solvable problems

## The Class NP

$NP$ = non-deterministic polynomial

A problem is in $NP$ if:

- Solutions always have length polynomial in the input size
- Purported solutions can be verified in polynomial time

Every problem in $NP$ can solved by brute-force search in polynomial time.

An $NP$ complete problem is a problem that is as hard as all other $NP$ problems.
A polynomial-time algorithm for one $NP$ complete problem solves every problem in $NP$ efficiently. (implies $P = NP$)

## Identifying NP Complete Problems

To prove that a problem $\pi$ is $NP$ complete:

1. Find a known $NP$ complete problem $\pi'$
2. Proves that $\pi'$ reduces to $\pi$

## The P vs NP question

Question: Is $P = NP$ ?

Widely conjectured: $P \ne NP$
