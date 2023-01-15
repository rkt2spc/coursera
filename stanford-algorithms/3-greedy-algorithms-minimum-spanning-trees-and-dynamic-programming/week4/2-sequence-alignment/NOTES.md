# Sequence Alignment

Compute similarity measure between 2 strings.

Similarity measure between 2 strings is defined as the total penalty of the best alignment (Needleman–Wunsch score)

Example:

```txt
s1 = AGGGCT
s2 = AGG-CA

- penalty for inserting a gap into s2
- penalty for mismatching the last character between s1 & s2
```

**Inputs:**

Strings

- $X = x_1 \ldots x_m$ of length $m$
- $Y = y_1 \ldots y_n$ of length $n$

$x_i$ and $y_i$ are characters over some alphabet $\Sigma$

Values for each type of penalty e.g

- $\alpha_{gap}$ for inserting a gap
- $\alpha_{ab}$ for a mismatching $a$ & $b$

**Goal:** Compute the alignment which minimizes the total penalties

## Intuition

Assume that $X$ and $Y$ are an optimal alignment of the same length $m$.

Consider the final character $x_m \in X$ and $y_m \in Y$  
Let $X' = X - x_m$ and $Y' = Y - y_m$

- If $x_m = y_m$ then the induced alignment $X'$ and $Y'$ must be optimal
- If $y_m$ is a filled gap then the induced alignment $X'$ and $Y$ must be optimal
- If $x_m$ is a filled gap then the induced alignment $X$ and $Y'$ must be optimal

## Solution

Our sub-problems only comprise of plucking off the final character of either $X$ or $Y$ or both

We only need to model the prefixes of each input string in the form of $(X_i,Y_j)$ where

- $X_i$ is the first $i$ letters of $X$
- $Y_j$ is the first $j$ letters of $Y$

Let $P_{ij}$ be the total penalty of the optimal alignment $X_i$ and $Y_j$

**Recurrence:** For all $i = 1, 2, 3, ..., m$ and $j = 1, 2,  3, ..., n$

$$
P_{ij} = min \begin{cases}
    \alpha_{x_i,y_j} + P_{(i-1)(j-1)} \\
    \alpha_{gap} + P_{(i-1)j} \\
    \alpha_{gap} + P_{i(j-1)} \\
\end{cases}
$$
