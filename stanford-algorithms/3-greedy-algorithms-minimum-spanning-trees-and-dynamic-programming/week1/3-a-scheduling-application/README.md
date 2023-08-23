# A Scheduling Application

## Problem Definition

**Setup:**

- One shared resource
- Many jobs

**Question:**

In what order should we sequence the jobs?

**Assume:**

Each job has:

- Weight or Priority - $w$
- Length - $l$

Completion time $c_i$ of the $i$-th job is the sum of lengths of all the jobs scheduled before and including the $i$-th job.

$$ c_i = \sum_{j=1}^{i} l_j $$

## Objective

We want to minimize the weighted sum of completion times

$$ \min \sum_{i=1}^{n} w_ic_i $$

Where:

- $w_i$ is the weight (priority) of the $i$-th job
- $c_i$ is the completion time of the $i$-th job

## Claim

If we order job by the ratio between their weight and length $(\frac{w_i}{l_i})$, we will get the minimum $\sum_{i=1}^{n} w_ic_i$ and achieve our objective.

## Proof

Fix an arbitrary number of $n$ jobs

Let:

- $\sigma$ be the greedy schedule computed using our claim
- $\sigma*$ be the optimal greedy schedule

Assume:

- Jobs are sorted according to our claim $(\frac{w_1}{l_1} > \frac{w_2}{l_2} > ... > \frac{w_n}{l_n})$

Thus our greedy schedule $\sigma = {1, 2, ..., n}$

Thus if optimal schedule $\sigma*$ exist, then there are consecutive jobs $i$, $j$ with $i > j$. For example $\sigma* = {1, 2, \bold{4}, \bold{3}, 5, ..., n}$

Let say we swap the position of that $i$-th and $j$-th jobs in $\sigma*$, we'll have:

- The cost of the exchange: $w_il_j$ ($c_i$ goes up by $l_j$)
- The benefit of the exchange: $w_jl_i$ ($c_j$ goes down $l_i$)

By assumption, if $i > j$:

$$
\begin{split}
\implies & \frac{w_i}{l_i} \leq \frac{w_j}{l_j} \\
\implies & w_il_j \leq w_jl_i \\
\implies & COST \leq BENEFIT
\end{split}
$$

So if we swap that $i$-th and $j$-th job in $\sigma*$ then we'll have an at-least equal or more optimized schedule.

Then if we repeatedly invert out of order pairs in $\sigma*$ (like bubble-sort):

- We'll only get an at-least equal or more optimized schedule.
- We'll remove all out of order pairs and eventually get $\sigma$

$\implies$ $\sigma$ is at-least equal or more optimized than $\sigma*$

$\implies$ $\sigma$ is optimized
