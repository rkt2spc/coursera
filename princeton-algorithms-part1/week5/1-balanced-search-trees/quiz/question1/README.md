# Red–black BST with no extra memory

Describe how to save the memory for storing the color information when implementing a red–black BST.

## Answer

Just modify the BST. For black node, do nothing. And for red node, exchange its left child and right child. In this case, a node can be justified red or black according to if its right child is larger than its left child.

[Source](https://stackoverflow.com/a/31633167/4318281)
