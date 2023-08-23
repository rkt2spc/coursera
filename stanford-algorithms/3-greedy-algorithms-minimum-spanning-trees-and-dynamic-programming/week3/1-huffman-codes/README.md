# Huffman Codes

## Goal

Map each character of an alphabet $\Sigma$ to a binary string

## Examples

For $\Sigma = \{A, B, C, D\}$

### Fixed length encoding

$A = 00 \\ B = 01 \\ C = 10 \\ D = 11$

### Variable length encoding

$A = 0 \\ B = 01 \\ C = 10 \\ D = 1$

-> Ambiguity: $001$ could be $AB$ or $AAD$

**Problem:** With variable length codes, not clear where one character ends and the next one begin.  
**Solution:** Use prefix-free codes, make sure that for every pair of $i, j \in \Sigma$, neither of the encoding $f(i)$, $f(j)$ is a prefix of the other.  
**Example:** $\{A = 0, B = 10, C = 110, D = 111 \}$

## Codes as tree

We can represent codes as binary tree, where the left branch denotes the $1$ bit and the right branch denotes the $0$ bit.
The root to node path is the encoding of each character

Example fixed length encoding:

$\{A = 00, B = 01, C = 10, D = 11 \}$

```txt
.
├──0── .
│      ├──0── A
│      └──1── B
└──1── .
       ├──0── C
       └──1── D
```

Example variable length encoding

$\{A = 0, B = 01, C = 10, D = 1 \}$

```txt
.
├──0── A
│      └──1── B
└──1── D
       └──0── C
```

Example prefix-free variable length encoding

$\{A = 0, B = 10, C = 110, D = 111 \}$

```txt
.
├──0── A
└──1── .
       ├──0── B
       └──1── .
              ├──0── C
              └──1── D
```

-> We can see that prefix-free codes when represented as tree will only have the character labels at their leaf nodes.

Then, to decode we can just follow the root to leaf path until we reach a label at the leaf.

Example: $0110111$ -> $[0][110][111]$ -> $ACD$

**NOTE:** Encoding length of $i \in \Sigma$ = depth of i in the tree.

## Problem Definition

### Input

Probability $p_i$ for each character $i \in \Sigma$  

### Notation

Assume we represent $\Sigma$ in a prefix-free variable length encoding binary tree $T$ where each leaf node is contains symbol of $\Sigma$

Then the average encoding length:

$L(T) = \sum_{i \in \Sigma} p_i \cdot [\text{depth of i in T}]$

### Desired Outcome

We want to encode in a way that minimize the average encoding length.

## Solution

The first idea to minimize average encoding length is putting the more frequent symbols at top of the tree and the less frequent symbols at the bottom. Bottom symbols have larger encoding length, if they are less frequent our final encoded product will be shorter. Likewise, top symbols have shorter encoding length, if they are frequent, we'll get to encoding more number of symbols using a short amount of encoding length.

Huffman has proven that the correct way to implement this is to merge nodes bottom up.

1. First he removed 2 nodes (symbols) with the smallest amount of weight, merge them and create a meta-node with the combined weight of the 2 merged node.
2. Then, he repeated step 1 with the remaining nodes and the previously merged meta-node added to the mix.

```
Example:

Symbol(Weight)
─ A(3)
─ B(2)
─ C(6)
─ D(8)
─ E(2)
─ F(6)

1. Merge B(2) & E(2) as they have the smallest weights

─ A(3)
─ BE(2+2=4)
  ├──B(2)
  └──E(2)
─ C(6)
─ D(8)
─ F(6)

2. Merge A(3) & BE(4) as they have the smallest weights

─ ABE(3+4=7)
  ├── A(3)
  └── BE(4)
      ├── B(2)
      └── E(2)
─ C(6)
─ D(8)
─ F(6)

3. Merge C(6) & F(6) as they have the smallest weights

─ ABE(7)
  ├── A(3)
  └── BE(4)
      ├── B(2)
      └── E(2)
─ D(8)
─ CF(6+6=12)
  ├──C(6)
  └──F(6)

4. Merge ABE(7) & D(8) as they have the smallest weights

─ ABED(7+8=15)
  ├── ABE(7)
  │   ├── A(3)
  │   └── BE(4)
  │       ├── B(2)
  │       └── E(2)
  └── D(8)
─ CF(12)
  ├──C(6)
  └──F(6)

5. Merge CF(12) & ABED(15) as they have the smallest weights

─ CFABED(12+15=27)
  ├── CF(12)
  │   ├──C(6)
  │   └──F(6)
  └─ ABED(15)
      ├── ABE(7)
      │   ├── A(3)
      │   └── BE(4)
      │       ├── B(2)
      │       └── E(2)
      └── D(8)
```
