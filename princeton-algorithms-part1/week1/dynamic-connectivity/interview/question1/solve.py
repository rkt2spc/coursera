from typing import Tuple


def root(i: int, a: []int) -> int:
    r = i

    it = i
    while it != a[it]:
        it = a[it]

    r = it

    it = i
    while it != a[it]:
        it = a[it]
        a[it] = r

    a[i] = r
    return r


def solve(n: int, pairs: []Tuple[int, int]) -> int:
    us = list(range(n))
    sz = [1] * n

    for i in range(len(pairs)):
        p = pairs[i]
        a = p[0]
        b = p[1]
        ra = root(a, us)
        rb = root(b, us)
        if ra == rb:
            continue

        if sz[ra] < sz[rb]:
            us[ra] = rb
            sz[rb] += sz[ra]
            if sz[rb] == n:
                return i
        else:
            us[rb] = ra
            sz[ra] += sz[rb]
            if sz[ra] == n:
                return i

    return -1
