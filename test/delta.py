#!/usr/bin/python
import sys
import os

sys.path.append(os.path.join(os.path.dirname(__file__), '../src/datajunction'))
import metadata

import unittest
import copy

class TestSequenceFunctions(unittest.TestCase):

    def test_delta(self):
        a = {'asdf': 10}
        b = ['asdf', 10]
        c = {'asdf': 5}
        d = {'asdf': 10, 'banana': 20}
        e = {'banana': 20}
        self.assertEqual(metadata.delta(a, a), None, "delta(a,a) should result in a becoming none")
        self.assertEqual(metadata.delta(a, b), b, "delta(typeA, typeB) should be equal to B")
        self.assertEqual(metadata.delta(a, c), c, "delta(typeA, typeB) should be equal to B")
        self.assertEqual(metadata.delta(a, d), e, "delta(%s, %s) should be equal to %s" % (str(a), str(b), str(e)))

    def test_delta_multilevel(self):
        a = {'chunks': [u'096ae77cca0af389eded65d107c9781ef40cf810'], 'stat': {'uid': 1000, 'dev': 2054L, 'ctime': u'2010-12-01T21:31:50.846438Z', 'nlink': 1, 'gid': 1000, 'mode': 33188, 'mtime': u'2010-12-01T21:31:50.716438Z', 'atime': u'2010-12-01T21:31:50.706438Z', 'ino': 10355275, 'size': 88}, 'location': {'realpath': u'/home/bram/program/datajunction/outputs.json', 'filename': u'/home/bram/program/datajunction/outputs.json'}, 'ctime': u'2010-12-04T21:10:02.935323Z', 'digest_sha1': u'096ae77cca0af389eded65d107c9781ef40cf810'}
        b = copy.deepcopy(a)
        b['ctime'] = u'a'
        self.assertEqual(metadata.delta(a, a), None, "delta(a,a) should result in None")
        self.assertEqual(metadata.delta(a, b), {'ctime': u'a'}, "delta(a,a) should result in None")


if __name__ == '__main__':
    unittest.main()

