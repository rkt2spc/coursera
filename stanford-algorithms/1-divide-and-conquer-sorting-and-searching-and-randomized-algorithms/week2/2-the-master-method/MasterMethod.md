# Master method

For calculating upper-bound complexity of recursive divide and conquer algorithms

## 1. Introduction

This paper aims to explain the master method used for calculating upper-bound
complexity of recursive divide and conquer algorithms. The materials here are
my interpretation of the Stanford Algorithms lecture.

## 2. Divide and Conquer

A recursive divide and conquer algorithm method is understood to be comprised of 2 steps:

- **The divide and conquer step** which splits the problem into smaller sub-problems, then recursively invoke the method on each sub-problem.
- **The combine step** which combines the results of all solved sub-problems from the divide and conquer step.

**NOTE:** To avoid the divide process running indefinitely, it is assumed that when the problem reach a certain small enough size, the conquer process can be performed in constant time.

## 3. Recurrence Formula

The recurrence formula relatively describes the number of operations performed by a recursive divide and conquer algorithm. It takes the following form:

$$ T(n) = a \cdot T(\frac{n}{b}) + C \cdot n^d $$

- $n$ : the input size
- $a$ : the number of recursive calls (number of sub-problems)
- $b$ : the input shrinkage factor
- $d$ : the exponent factor of performing the combine step
- $C$ : the constant factor of performing the combine step

### 3.1 Examples

For **MERGE SORT**

- We make **2 recursive calls** ($a=2$) in each invocation
- The input size **reduced by half** ($b=2$) for each recursive call
- The combine step aka "merge" is performed in **linear time** ($d = 1$)

&rarr; The recurrence formula for **MERGE SORT** is $T(n) = 2T(\frac{n}{2}) + C \cdot n$

For **BINARY SEARCH**

- We make **1 recursive call** ($a=1$) in each invocation
- The input size **reduced by half** ($b=2$) for each recursive call
- The combine step is performed in **constant time** ($d = 0$)

&rarr; The recurrence formula for **BINARY SEARCH** is $T(n) = T(\frac{n}{2}) + C$

## 4. Assumptions

The master method explained here is only correct under the assumption that sub-problems must be of equal size (e.g in merge sort, you divide the original array in to 2 **equal size** sub-arrays).

## 5. Master Method Formula

Given a recursive divide and conquer method $T(n) = a \cdot T(\frac{n}{b}) + C \cdot n^d$ with parameters as described in \nameref{Recurrence Formula}. The upper bound complexity of the method $T(n)$ can be calculated as follow:

$$
T(n) = \begin{cases}
    O(n^d \cdot \log n),& \text{if } a = b^d\\
    O(n^d),& \text{if } a < b^d\\
    O(n^{\log_{b} a}),& \text{if } a > b^d
\end{cases}
$$

## 6. Proof

Following the recurrence formula, imagine a recursion tree that has that has many levels.

### Observation 1

At level *j* of the recursion tree.

- The number of sub-problems is: $a^j$
- Each sub-problem size is: $\frac{n}{b^j}$

### Observation 2

The maximum depth of the recursion tree is $\log_{b} n$

Because at that level the input size is $\frac{n}{b^{log_{b} n}} = \frac{n}{n} = 1$ which is a constant and at that constant input size recursion is no longer needed.

$\implies j = 0, 1, 2, 3, ..., log_{b} n$

### Observation 3

Following the recurrence formula, the total amount of work at level *j* of the recursion tree, **ignoring works in recursive calls**, is lesser than or equal to:

> The total effort of the "combining step" of all sub-problems at level *j* of the recursion tree.

In other words, it is lesser than or equal to:

> The number of sub-problems $a^j$ multiplied by the amount of work in the combining step of each sub-problem of size $\frac{n}{b^j}$, which is $C \cdot (\frac{n}{b^j})^d$

In mathematical notion the total amount of work at level *j* of the recursion tree, $W(j)$, is:

$$
\begin{split}
W(j) & \leq a^j \cdot C \cdot (\frac{n}{b^j})^d \\
\iff W(j) & \leq C \cdot n^d \cdot (\frac{a}{b^d})^j
\end{split}
$$

Notice the total amount of work at each level of the recursion tree is govern by the ratio between $a$ and $b^d$ (which hints at the condition of the 3 cases in the [Master Method Formula](#5-master-method-formula))

- If $a = b^d$ meaning $\frac{a}{b^d} = 1$, the amount of work is equal at every level of the recursion tree regardless of depth *j*
- If $a < b^d$ meaning $\frac{a}{b^d} < 1$, the amount of work decrease proportionately to the recursion level depth *j*
- If $a > b^d$ meaning $\frac{a}{b^d} > 1$, the amount of work increase proportionately to the recursion level depth *j*

### Observation 4

Combining information from the previous observations we have:

- The recursion tree has at most $log_{b} n$ levels, meaning $j = 0, 1, 2, 3, ..., log_{b} n$
- The amount of work at level *j* of the recursion tree, $W(j) \leq C \cdot n^d \cdot (\frac{a}{b^d})^j$

The total amount of work of the recursion tree, denoted as $T(n)$ by the [Recurrence Formula](#3-recurrence-formula), is bounded by the sum of the amount of work at all levels.

$$
\tag{*}
\begin{split}
T(n) & \leq \sum_{j=1}^{log_{b} n} W(j) \\
\iff T(n) & \leq \sum_{j=1}^{log_{b} n} C \cdot n^d \cdot (\frac{a}{b^d})^j \\
\iff T(n) & \leq C \cdot n^d \cdot \sum_{j=1}^{log_{b} n} (\frac{a}{b^d})^j
\end{split}
$$

#### Case $a = b^d$

As $\frac{a}{b^d} = 1$, $(*)$ becomes

$$
\begin{split}
T(n) & \leq C \cdot n^d \cdot \sum_{j=0}^{log_{b} n} 1^j \\
\iff T(n) & \leq C \cdot n^d \cdot (\log_{b} n + 1)   
\end{split}
$$

Applying the Big-O notation we'll have

$$T(n) = O(n^d \cdot \log n)$$

*This proves case 1 of the master method.*

#### Cases where $a \neq b^d$

##### Math fact:

For $r \neq 1$ we have $1 + r + r^2 + r^3 + ... + r^k = \sum_{j=0}^{k} r^j = \frac{r^{k + 1} - 1}{r - 1}$

- If $r < 1$ then $\sum_{j=0}^{k} r^j \leq \frac{1}{1 - r}$ which is a constant independent of $k$
- If $r > 1$ then $\sum_{j=0}^{k} r^j \leq r^k \cdot (1 + \frac{1}{r - 1})$. In which $\frac{1}{r - 1}$ is a constant independent of $k$

##### Case $a < b^d$

Base on the [Math fact](#math-fact) formula, imagine $r = \frac{a}{b^d}$, in this case $r < 1$ because $a < b^d$. We'll see that $(*)$ becomes:

$$T(n) \leq C \cdot n^d \cdot \frac{1}{1 - \frac{a}{b^d}}$$

In which $\frac{1}{1 - \frac{a}{b^d}}$ is a constant. We can safely remove this constant as we convert into Big-O notation, which gets us:

$$T(n) = O(n^d)$$

*This proves case 2 of the master method.*

##### Case $a > b^d$

Base on the [Math fact](#math-fact) formula, imagine $r = \frac{a}{b^d}$, in this case $r > 1$ because $a > b^d$. We'll see that $(*)$ becomes:

$$ T(n) \leq C \cdot n^d \cdot (\frac{a}{b^d})^{\log_{b} n} \cdot (1 + \frac{1}{\frac{a}{b^d} - 1}) $$

In which $1 + \frac{1}{\frac{a}{b^d} - 1}$ is a constant. We can safely remove this constant as we convert into Big-O notation, which gets us:

$$
\begin{split}
T(n) & = O(n^d \cdot (\frac{a}{b^d})^{\log_{b} n}) \\
     & = O(n^d \cdot a^{log_{b} n} \cdot (\frac{1}{b^d})^{log_{b} n}) \\
     & = O(n^d \cdot a^{log_{b} n} \cdot b^{(\log_{b} n)(-d)}) \\
     & = O(n^d \cdot a^{log_{b} n} \cdot n^{-d}) \\
     & = O(a^{log_{b} n}) \\
     & = O(n^{log_{b} a})
\end{split}
$$

Note that $a^{log_{b} n} = n^{log_{b} a}$ with the below proof:

$$
\begin{split}
log_{n} a & = \frac{log_{b} a}{log_{b} n} \\
\iff log_{b} a & = (log_{n} a)(log_{b} n) \\
\iff n^{log_{b} a} & = n^{(log_{n} a)(log_{b} n)} \\
\iff n^{log_{b} a} & = a^{log_{b} n}
\end{split}
$$

*This proves case 3 of the master method.*