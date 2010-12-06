#!/usr/bin/python
import sys
import os

sys.path.append(os.path.join(os.path.dirname(__file__), '../src')) #UGLY!
import chunkstore

import unittest
import copy
import shutil

class TestSequenceFunctions(unittest.TestCase):
    def setUp(self):
        self.storagePath = '/tmp/storageTest%s' % os.getpid()
        os.makedirs(self.storagePath)

    def test_storeDirectory(self):
        cs = chunkstore.ChunkStorage(self.storagePath)
        cs.store('/tmp')


    def tearDown(self):
        shutil.rmtree(self.storagePath)

if __name__ == '__main__':
    unittest.main()

