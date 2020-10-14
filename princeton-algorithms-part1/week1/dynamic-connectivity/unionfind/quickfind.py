import unionfind


class QuickFind(unionfind.UnionFind):
    def __init__(self, N: int) -> None:
        self.id = list(range(N))

    def union(self, p: int, q: int) -> None:
        if self.connected(p, q):
            return

        pid = self.id[p]
        for i in range(len(self.id)):
            if self.id[i] == pid:
                self.id[i] = self.id[q]

    def connected(self, p: int, q: int) -> bool:
        return self.id[p] == self.id[q]
