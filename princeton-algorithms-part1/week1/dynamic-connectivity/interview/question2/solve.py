class UnionFind:
    def __init__(self, N: int) -> None:
        self.id = list(range(N))
        self.sz = [1] * N
        self.mx = list(range(N))

    def __root(self, i: int) -> int:
        while i != self.id[i]:
            self.id[i] = self.id[self.id[i]]
            i = self.id[i]
        return i

    def union(self, p: int, q: int) -> None:
        pr = self.__root(p)
        qr = self.__root(q)

        if pr == qr:
            return

        if self.sz[pr] < self.sz[qr]:
            self.id[pr] = qr
            self.sz[qr] += self.sz[pr]
            self.mx[qr] = max(self.mx[pr], self.mx[qr])
        else:
            self.id[qr] = pr
            self.sz[pr] += self.sz[qr]
            self.mx[pr] = max(self.mx[pr], self.mx[qr])

    def find(self, i: int) -> int:
        return self.mx[self.__root(i)]

    def connected(self, p: int, q: int) -> bool:
        return self.__root(p) == self.__root(q)
