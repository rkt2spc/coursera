from abc import ABC as AbstractClass, abstractmethod
import unittest
import unionfind


class TestUnionFind(AbstractClass):
    @abstractmethod
    def setUp(self):
        self.create_union_find = unionfind.UnionFind

    def test_set_1(self):
        uf = self.create_union_find(10)
        uf.union(4, 3)
        uf.union(3, 8)
        uf.union(6, 5)
        uf.union(9, 4)
        uf.union(2, 1)
        self.assertEqual(uf.connected(0, 7), False)
        self.assertEqual(uf.connected(8, 9), True)
        uf.union(5, 0)
        uf.union(7, 2)
        uf.union(6, 1)
        uf.union(1, 0)
        self.assertEqual(uf.connected(0, 7), True)
