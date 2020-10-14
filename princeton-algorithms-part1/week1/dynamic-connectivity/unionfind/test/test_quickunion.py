import unittest
import unionfind
from .test_unionfind import TestUnionFind


class TestQuickUnion(TestUnionFind, unittest.TestCase):
    def setUp(self):
        self.create_union_find = unionfind.QuickUnion


class TestWeightedQuickUnion(TestUnionFind, unittest.TestCase):
    def setUp(self):
        self.create_union_find = unionfind.WeightedQuickUnion


class TestWeightedQuickUnionPathCompression(TestUnionFind, unittest.TestCase):
    def setUp(self):
        self.create_union_find = unionfind.WeightedQuickUnionPathCompression
