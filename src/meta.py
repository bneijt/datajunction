
import os


class Meta:
    '''Class containing meta information retrieval functions'''
    def __init__(self, fileName):
        self.fileName = fileName
    def location(self):
        return {
            'filename': self.fileName,
            'realpath': os.path.realpath(self.fileName),
            }
    def stat(self):
        stat = os.lstat(self.fileName) #TODO Add support for other platforms (st_creator etc)
        meta = {}
        meta['size'] = stat.st_size
        meta['uid'] = stat.st_uid
        meta['gid'] = stat.st_gid
        #TODO Time to universal time translation
        meta['atime'] = stat.st_atime
        meta['ctime'] = stat.st_ctime
        meta['mtime'] = stat.st_mtime
        meta['dev'] = stat.st_dev
        meta['ino'] = stat.st_ino
        meta['mode']  = stat.st_mode
        meta['nlink'] = stat.st_nlink
        return meta
 

