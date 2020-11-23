import unittest
import unionfind
from .test_unionfind import TestUnionFind


class TestQuickFind(TestUnionFind, unittest.TestCase):
    def setUp(self):
        self.create_union_find = unionfind.QuickFind
