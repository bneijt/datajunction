
import os


def location(fileName):
    meta = {
        'filename': fileName,
        'realpath': os.path.realpath(fileName),
        }
    if os.path.islink(fileName):
        meta['link'] = os.readlink(fileName)

    return meta


def stat(fileName):
    stat = os.lstat(fileName) #TODO Add support for other platforms (st_creator etc)
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


