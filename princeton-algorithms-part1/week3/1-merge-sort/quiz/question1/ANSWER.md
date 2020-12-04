# Answer

1. Merge n smallest elements to the auxiliary array
2. Move the remaining elements to a[n:2n-1]
3. Move the merged elements in the auxiliary array to a[0:n-1]
4. Merge the remaining elements in a[n:2n-1] to the auxiliary array
5. Move the merged elements in the auxiliary array to a[n:2n-1]

## Example

```txt
n   = 4
a   = [1, 4, 5, 7, 0, 2, 3, 6]
aux = [0, 0, 0, 0]
```

### 1. Merge n smallest elements to the auxiliary array

```txt
n   = 4
a   = [1, 4, 5, 7, 0, 2, 3, 6]
aux = [0, 1, 2, 3]
```

### 2. Move the remaining elements to a[n:2n-1]

-> In the left part we merged [1] into the auxiliary array  
-> In the right part we merged [0, 2, 3] into the auxiliary array  
-> We move the remaining left part [4, 5, 7] into the right part (replacing [0, 2, 3])  

```txt
n   = 4
a   = [1, 4, 5, 7, 4, 5, 7, 6]
aux = [0, 1, 2, 3]
```

### 3. Move the merged elements in the auxiliary array to a[0:n-1]

```txt
n   = 4
a   = [0, 1, 2, 3, 4, 5, 7, 6]
aux = [0, 1, 2, 3]
```

### 4. Merge the remaining elements in a[n:2n-1] to the auxiliary array

-> The remaining left part [4, 5, 7]  
-> The remaining right part [6]

```txt
n   = 4
a   = [0, 1, 2, 3, 4, 5, 7, 6]
aux = [4, 5, 6, 7]
```

### 5. Move the merged elements in the auxiliary array to a[n:2n-1]

```txt
n   = 4
a   = [0, 1, 2, 3, 4, 5, 6, 7]
aux = [4, 5, 6, 7]
```
