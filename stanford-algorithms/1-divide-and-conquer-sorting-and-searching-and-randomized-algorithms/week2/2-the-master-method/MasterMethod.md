# Master Method for computing upper-bound complexity of recursive divide and conquer algorithms

## Recurrence Formula

```txt
T(n) = a.T(n/b) + C.n^d
```

Where  

- **n**: the input size
- **a**: the number of recursive calls (number of sub-problems)  
- **b**: input shrinkage factor  
- **d**: exponent of the input size in running time of the "combine step"
- **C**: is a constant factor of performing the "combine step"

## Assumptions

Sub-problems must be equal size (e.g in merge sort, you split the original array in to 2 **equal size** sub arrays).

## Master Method Formula

If `a = b^d` then `T(n) = O(n^d.log(n))`  
If `a < b^d` then `T(n) = O(n^d)`  
If `a > b^d` then `T(n) = O(n^log_b(a))`  

## Proof

Following the recurrence formula, imagine a recursion tree that has that has many levels.

### Observation 1  

At level *j* of the recursion tree

- The number of subproblems is: `a^j`
- The subproblem size is: `n / b^j`

### Observation 2

The maximum depth of the recursion tree is `log_b(n)` because at that level the input size is `n / b^log_b(n) = n / n = 1`. And at that constant input size recursion is no longer needed.  

=> `j = 0, 1, 2, 3, ..., log_b(n)`

### Observation 3

Following the recurrence formula, the total amount of work at level *j* of the recursion tree (ignoring work in recursive calls) is:  

> The total effort of the "combining step" of all subproblems.

In other words

> It is the number of sub-problems `a^j` multiplied by the amount of work in the combining step of each sub-problem of size `n/b^j`, which is `C.(n/b^j)^d`

Written in a mathematical formula the total amount of work at level *j* of the recursion tree `W(j)` is:

```txt
W(j) ≤ a^j . C.(n/b^j)^d
W(j) ≤ C . n^d . (a/b^d)^j
```

=> Notice the upper bound of `W(j)` is govern by the ratio between `a` and `b^d` (which hints at the conditions of the 3 cases in the master method formula)

- If `a = b^d` the amount of work is equal at every level of the recursion tree
- If `a < b^d` the amount of work decrease proportionate to the recursion level depth
- If `a > b^d` the amount of work increase proportionate to the recursion level depth

### Observation 4

Combining observation 2 & 3 we have:

- The recursion tree has at most `log_b(n)` levels, meaning `j = 0, 1, 2, 3, ..., log_b(n)`
- The amount of work at level *j* is `≤ C . n^d . (a/b^d)^j`

=> The total amount of work (W) of the recursion tree is the sum of the amount of work at all levels

We have the formula (*)

```txt
W ≤ C . n^d . ∑{j=0->log_b(n)}((a/b^d)^j)
```

#### Case 1: `a = b^d`

(*) becomes:  

```txt
    W ≤ C . n^d . ∑{j=0->log_b(n)}(1^j)
<=> W ≤ C . n^d . (log_b(n) + 1)
```

Applying the Big-O notation we'll have

```txt
O(n^d.log(n))
```

**This proves case 1 of the master method.**

#### Some math facts

For `r != 1` we have

```txt
  1 + r + r^2 + r^3 + ... + r^k
= (r^(k + 1) - 1) / (r - 1)
```

- If `r < 1` then the above sum is `≤ 1 / (1 - r)` which is a constant independent of k.
- If `r > 1` then the above sum is `≤ r^k . (1 + (1/(r-1)))`. In which `1 / (r - 1)` is a constant independent of k.

#### Case 2 `a < b^d`

Replace `r = a / b^d` in the math fact formula. In this case `r < 1` because `a < b^d`. We'll see that:

```txt
∑{j=0->log_b(n)}((a/b^d)^j) ≤ a constant independent of j
```

We can safely remove this constant in (\*) as we convert (\*) into Big-O notation, which gets us:

```txt
O(n^d)
```

**This proves case 2 of the master method.**

#### Case 3 `a > b^d`

Replace `r = a / b^d` in the math fact formula. In this case `r > 1` because `a > b^d`. We'll see that:

```txt
∑{j=0->log_b(n)}((a/b^d)^j) ≤ ((a/b^d)^log_b(n)) . constant independent of j
```

Remove the constants in (\*) as we convert (\*) into Big-O notation, which gets us:

```txt
O(n^d.((a/b^d)^log_b(n)))

Note:
  (1/b^d)^log_b(n)
= (b^log_b(n))^(-d)
= n^(-d)

The Big-O notation becomes:
  O(n^d . a^log_b(n) . n^(-d))
= O(a^log_b(n))

Note:
a^log_b(n) = n^log_b(a)

The Big-O notation can also be written as:
O(n^log_b(a))
```

**This proves case 3 of the master method.**
