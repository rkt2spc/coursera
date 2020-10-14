class Solution:
    def __init__(self, n: int) -> None:
        self.s = list(range(n))

    def successor(self, x: int) -> int:
        it = x
        while it != self.s[it]:
            it = self.s[it]

        r = it
        it = x
        while it != self.s[it]:
            it = self.s[it]
            self.s[it] = r

        self.s[x] = r
        return r

    def remove(self, x: int) -> int:
        if x == len(self.s) - 1:
            return -1

        self.s[x] = self.s[x + 1]
        return self.successor(self.s[x])
