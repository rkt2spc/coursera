# Decimal dominants

Given an array with n keys, and a value k < n
Design an algorithm to find all values that occur more than n/k times.
The expected running time of your algorithm should be linear.

## Practices

- https://leetcode.com/problems/majority-element-ii

## Hints

- Determine the (n/10)-th largest key using quickselect and check if it occurs more than n/10 times.

- Alternate solution hint: use 9 counters.
