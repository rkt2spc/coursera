# How HashTable works

A hash function translate a key into a position within the backing storage.

Collisions are quite common, it happens when 2 keys hashed into the same position.

Solving collisions:

- Separate Chaining: At each hashed position there will be a linked-list, keys with duplicate hashes will be store on the same linked-list. Sequential comparison will be performed on the linked-list to get the correct value for a key.
- Open Addressing: Hash function returns a probe sequence of possible positions, iterate through this sequence and select the first empty position for a new key, perform sequential comparisons on this sequence to find an existing key.
  - Linear Probing: The hash function return a position in the backing storage, from this position iterate consecutively (H, H+1, H+2, H+3, ...) until you find a first empty position to insert the key.
  - Quadratic Probing: Similar to linear probing but the interval is a progressive quadratic polynomial (H, H+1^2, H+2^2, H+3^2, ...)
  - Double Hashing: Similar to linear probing but the interval is calculated using another hash function (H+H2, H+2.H2, H+3.H2, ...)

## Quick & dirty hash function

Key ---hash---> Big Integer / Hash Code ---compress---> Small Integer / Position within backing storage

Sample hash code calculation sub-routine for string:

```java
int hashCode(String s) {
    int PRIME = 31;
    int hashCode = 0;
    for (int i = 0; i < s.length(); ++i) {
        int c = (int) s.charAt(i);
        int f =  (int) Math.pow(PRIME, s.length() - i + 1)
        hashCode += c * f
    }
    return hashCode;
}
```

Sample compression

```java
int compress(int k, int n) {
    return k % n;
}
```

Rule of thumbs to improve performance of the quick-and-dirty hash function:

- If the data share a common factor with the number of positions, it's possible that only the factored positions are used, while the rest being empty. You should choose a number of positions with least possible factor, e.g a PRIME number.
- Choose the number of bucket not too close to a power of 2 and not too close to a power of 10.
