# Selection in two sorted arrays

Given two sorted arrays a[] and b[], of lengths n1 n2 and an integer 0 ≤ k < n1 + n2, design an algorithm to find a key of rank k. The order of growth of the worst case running time of your algorithm should be log(n), where n = n1 + n2

Version 1: n1 = n2 (equal length arrays) and k = n / 2 (median).  
Version 2: k = n / 2 (median).  
Version 3: no restrictions.

## Practices

- https://leetcode.com/problems/median-of-two-sorted-arrays

## Hints

- Approach A: Compute the median in a[] and the median in b[]. Recur in a subproblem of roughly half the size.

- Approach B: Design a constant-time algorithm to determine whether a[i] is a key of rank k. Use this subroutine and binary search.
