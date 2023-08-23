# Union Find

A data structure that supports 2 operations

- $find(x)$: given $x$, return the name of $x$'s group.
- $union(x, y)$: given $x$ and $y$, merge groups containing them.

Union Find is commonly implemented using a forest, $find(x)$ will return the root of the tree containing $x$

## Lazy Union

Let's say we try to $union(x, y)$

The idea is to make the root of the tree containing one element become a child of the root of the tree containing the other element.

```txt
A
├── B
└── C
D
└── E
    └── F

UNION(B, F):
    FIND(B) = A
    FIND(F) = D
    Makes D child of A

A
├── B
├── C
└── D
    └── E
        └── F
```

**Pros:**

- **UNION** takes O(1) extra work (still bottle-necked by **FIND**).

**Cons:**

- **FIND** could takes O(n) steps to find root as the tree grows deeper.

## Union by Rank

We maintain the rank per root of each tree in the union forest. Rank is the deepest root-to-leaf depth of the tree. When we merge/union 2 trees, instead of arbitrarily make 1 root a child of other like in Lazy Union, we make the root with smaller rank the child of the other. The idea is to control the depth of each tree so we can reduce the time complexity of **FIND** to $O(\log{n})$

```txt
A
├── B
└── C
D
└── E
    └── F

UNION(B, F):
    FIND(B) = A
    FIND(F) = D
    RANK(A) = 2
    RANK(D) = 3
    IF RANK(A) < RANK(D)
        Makes A child of D
    ELSE
        Makes D child of A
        IF RANK(A) == RANK(D)
            ++RANK(A)

D
├── A
│   ├── B
│   └── C
└── E
    └── F
```

## Path Compression

As we invoke **FIND** we makes all nodes that we traverse through point to the tree root, this further reduces the tree depth.

```txt
A
├── B
│   ├── C
│   └── D
│       └── E
│           └── F
└── G
    └── H

FIND(E):
    IF E is root:
        return E:
    ELSE
        E.parent = FIND(E.parent)
        return E.parent

A
├── B
│   └── C
├── D
├── E
│   └── F
└── F
    └── G
```

## Union by rank + Path Compression

If you combine Union by rank and Path compression, then $m$ **UNION** and **FIND** operations take $O(m \cdot \alpha(n))$, where $\alpha(n)$ is the inverse Ackermann function (Tarjan's 70s).

### The Ackermann function

Define $A_k(r)$ for all integers $k \ge 0$ and $r \ge 1$

**Base case:** $A_0(r) = r + 1$ for all $r \ge 1$

**In general:** For $k, r \ge 1$

$A_k(r) =$ Applies $A_{k-1}$ $r$ times to $r$  
$A_k(r) = (A_{k-1}(r) \circ A_{k-1}(r) \circ ... \circ A_{k-1}(r))(r)$

Example:

$$
\begin{split}
A_3(2) & = A_2(A_2(2)) \\
       & = A_2(A_1(A_1(2))) \\
       & = A_2(A_1(A_0(A_0(2)))) \\
       & = A_2(A_1(A_0(3))) \\
       & = A_2(A_1(4)) \\
       & = A_2(A_0(A_0(A_0(A_0(4))))) \\
       & = A_2(A_0(A_0(A_0(5)))) \\
       & = A_2(A_0(A_0(6))) \\
       & = A_2(A_0(7)) \\
       & = A_2(8) \\
       & = 8 \cdot 2^8 \\
       & = 2048
\end{split}
$$

## The Inverse Ackermann function

**Definition:** For every $n \ge 4$, $\alpha(n) =$ the minimum value of $k$ such that $A_k(2) \ge n$

$\alpha(n) = 1$ for $n=4$  
$\alpha(n) = 2$ for $n=5,6,7,8$  
$\alpha(n) = 3$ for $n=9,10,11,...,2048$  
$\alpha(n) = 4$ for $n$ up to roughly a tower of 2's of height 2048  
