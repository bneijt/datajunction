
import os
import datetime
import json
import types

def appendMeta(fileName, metaData, unique = False):
    '''Return the metadata for a given file, always a list of json maps with file metadata. The last entry is always a list
    of object data usage (when the object was stored)
    
    if unique is given, it will make sure the entry is only stored once if it already exists.
    
    '''
    #TODO Optimize
    assert isinstance(fileName, types.StringTypes)
    if not '.json' in fileName:
        raise Exception("Expected to write json, but the given fileName does not contain a json extension\n\tFilename: %s" % fileName)
    assert not unique
    outputDir = os.path.dirname(fileName)
    if not os.path.exists(outputDir):
        os.makedirs(outputDir)
    if os.path.exists(fileName):
        meta = json.load(file(fileName))
    else:
        meta = []
    if not isinstance(meta, list):
        raise Exception("Metadata should be a list, corruption in metadata detected?\n\tFile name: %s" % fileName)
    #TODO: Append only what is different
    meta.append(metaData)
    json.dump(meta, file(fileName, 'w'))
    return meta

def location(fileName):
    meta = {
        'filename': fileName,
        'realpath': os.path.realpath(fileName),
        }
    if os.path.islink(fileName):
        meta['link'] = os.readlink(fileName)

    return meta

def dateString(dtime):
    return dtime.isoformat()

def stat(fileName):
    stat = os.lstat(fileName) #TODO Add support for other platforms (st_creator etc)
    meta = {}
    meta['size'] = stat.st_size
    meta['uid'] = stat.st_uid
    meta['gid'] = stat.st_gid
    #TODO Time to universal time translation
    meta['atime'] = dateString(datetime.datetime.utcfromtimestamp(stat.st_atime))
    meta['ctime'] = dateString(datetime.datetime.utcfromtimestamp(stat.st_ctime))
    meta['mtime'] = dateString(datetime.datetime.utcfromtimestamp(stat.st_mtime))
    meta['dev'] = stat.st_dev
    meta['ino'] = stat.st_ino
    meta['mode']  = stat.st_mode
    meta['nlink'] = stat.st_nlink
    return meta


