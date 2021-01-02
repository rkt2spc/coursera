# Document search

Design an algorithm that takes a sequence of n document words and a sequence of m query words and find the shortest interval in which the m query words appear in the document in the order given. The length of an interval is the number of words in that interval.

## Answer

1. Implement a BST to track the location of each word in the document. Duplicate words will have all their locations tracked in in increasing order. e.g `Map<String,List<Integer>>`
2. For each word in the query, look for all the locations of that word in the document using the previous BST. Then use binary search to find the smallest location that is larger than the location of the previous query word.
