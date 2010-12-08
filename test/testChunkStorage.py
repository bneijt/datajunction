#!/usr/bin/python
import sys
import os

sys.path.append(os.path.join(os.path.dirname(__file__), '../src/datajunction')) #UGLY!
import chunkstore

import unittest
import copy
import shutil

class TestSequenceFunctions(unittest.TestCase):
    def setUp(self):
        self.storagePath = '/tmp/storageTest%s' % os.getpid()
        self.testPath = '/tmp/tempfiles%s' % os.getpid()
        os.makedirs(self.storagePath)
        os.makedirs(self.testPath)

    def test_storeDirectory(self):
        cs = chunkstore.ChunkStorage(self.storagePath)
        cs.store('/tmp')
    
    def test_brokenSymlink(self):
        cs = chunkstore.ChunkStorage(self.storagePath)
        fn = os.path.join(self.testPath, 'broken_link')
        os.symlink('asdfasdf', fn)
        cs.store(fn)

    def test_symlink(self):
        cs = chunkstore.ChunkStorage(self.storagePath)
        ef = os.path.join(self.testPath, 'empty_file')
        file(ef, 'w').close()
        fn = os.path.join(self.testPath, 'normal_link')
        os.symlink('empty_file', fn)
        cs.store(fn)

    def test_selfSymlink(self):
        cs = chunkstore.ChunkStorage(self.storagePath)
        fn = os.path.join(self.testPath, 'self_link')
        os.symlink('self_link', fn)
        cs.store(fn)



    def tearDown(self):
        shutil.rmtree(self.storagePath)
        shutil.rmtree(self.testPath)

if __name__ == '__main__':
    unittest.main()

