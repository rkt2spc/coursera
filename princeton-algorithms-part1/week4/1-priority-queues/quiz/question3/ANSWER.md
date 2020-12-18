# Answer

# Version 1: Use time proportional to n^2.log(n) and space proportional to n^2

Generate combinations of pairs of numbers between 1 to n. Track their taxicab sum (i^3 + j^3) in a heap. Any pairs with same sum value should appear in sequential delMin() operations.

This will cost n^2 operations to generate the combinations, multiplied by log(n) as the cost of inserting the sums into the heap. So the final time complexity is n^2.log(n)
Because we store all n^2 combinations (and their sum) into the heap the space complexity will be n^2

# Version 2: Use time proportional to n^2.log(n) and space proportional to n

The overall idea is to continuously extract pairs with minimum taxicab sum from all combined pairs.

An inefficient implementation would be to sort all the pairs by their sum, loop through the sorted set and return any pairs with equal sums next to each others. This would cost n^2 space to store all the combinations.

In a more efficient implementation we can group all the combinations (pairs) by their first number. An illustration (with n = 5) is as follow:

```txt
group(1) = (1, 1), (1, 2), (1, 3), (1, 4), (1, 5)
group(2) =         (2, 2), (2, 3), (2, 4), (2, 5)
group(3) =                 (3, 3), (3, 4), (3, 5)
group(4) =                         (4, 4), (4, 5)
group(5) =                                 (5, 5)
```

We notice that for each group:

- The initial minimum pair can be easily determined as `min(group(i)) = (i, i)`
- The next minimum pair can be easily calculated from the previous minimum pair, by increasing the second number by 1.

We also know that the minium of the union of 2 sets is minimum between the minimums of each sets.

```txt
A = B ∪ C => min(A) = min(min(B), min(C))
```

Since the union of all our groups is equal to all available combinations, to extract the minimum pair from all combinations, we will find the minimum pair between all the minimums of each group and extract it.  

After we extract a minimum pair from a group, we will calculate the next smallest pair in this group by increasing the second number of the previous minimum pair by 1.  

Repeat to process to continuously extract minimum pairs.

--  
In implementation, we will maintain a min heap that tracks all the minimum pairs of each group, since we have n groups, this heap will contains at most n pairs at all time, hence we'll get O(n) space complexity.  

- We initialize this heap with all the initial minimum pair from each group as `min(group(i)) = (i, i)`.  
- Continuously extract the minimum pair from the heap (this will be the minimum between the minimums of each group).
- Determine which group the extracted minimum pair is from (by looking at the first number), calculate the next minimum pair of that group (by increasing the second number by 1) and add it back to the heap.
- Compare the extracted minimum pair to the previous minimum pair to see if we have sequential pairs with equal sums.
