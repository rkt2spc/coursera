import unionfind


class QuickUnion(unionfind.UnionFind):
    def __init__(self, N: int) -> None:
        self.id = list(range(N))

    def __root(self, i: int) -> int:
        while i != self.id[i]:
            i = self.id[i]
        return i

    def union(self, p: int, q: int) -> None:
        self.id[self.__root(p)] = self.__root(q)

    def connected(self, p: int, q: int) -> bool:
        return self.__root(p) == self.__root(q)


class WeightedQuickUnion(unionfind.UnionFind):
    def __init__(self, N: int) -> None:
        self.id = list(range(N))
        self.sz = [1] * N

    def __root(self, i: int) -> int:
        while i != self.id[i]:
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
        else:
            self.id[qr] = pr
            self.sz[pr] += self.sz[qr]

    def connected(self, p: int, q: int) -> bool:
        return self.__root(p) == self.__root(q)


class WeightedQuickUnionPathCompression(unionfind.UnionFind):
    def __init__(self, N: int) -> None:
        self.id = list(range(N))
        self.sz = [1] * N

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
        else:
            self.id[qr] = pr
            self.sz[pr] += self.sz[qr]

    def connected(self, p: int, q: int) -> bool:
        return self.__root(p) == self.__root(q)
