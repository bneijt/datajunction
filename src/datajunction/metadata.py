
import os
import datetime
import json
import types
import copy
import pwd
import grp

def delta(last, new): #TODO Find a library with this functionality
    '''Return the delta between last and new'''
    if isinstance(last, str):
        raise Exception("Delta requested with last type 'str', you should be using unicode for value: %s" % last)
    if isinstance(new, str):
        raise Exception("Delta requested with new type 'str', you should be using unicode for value: %s" % new)

    if not type(last) == type(new):
        return new
    #Delegate
    if isinstance(last, types.ListType):
        r = type(last)()
        for i in xrange(0, min(len(last), len(new))):
            d = delta(last[i], new[i])
            if not d == None:
                r.append(d)
        if len(r) == 0:
            return None
        return r
    if isinstance(last, types.DictionaryType):
        r = type(last)()
        for key in new:
            if key in last:
                d = delta(last[key], new[key])
                if not d == None:
                    r[key] = d
            else:
                r[key] = new[key]
        if len(r.keys()) == 0:
            return None
        return r
    if last == new:
        return None
    return new

def appendMeta(fileName, constMetaData, unique = False):
    '''Return the metadata for a given file, always a list of json maps with file metadata. The last entry is always a list
    of object data usage (when the object was stored)
    
    if unique is given, it will make sure the entry is only stored once if it already exists.
    
    '''
    #TODO Optimize
    assert isinstance(fileName, types.StringTypes)
    if not u'.json' in fileName:
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
    if len(meta):
        #Append only the mutation from last metadata
        metaData = delta(meta[-1], constMetaData)
    else:
        metaData = constMetaData
    meta.append(metaData)
    json.dump(meta, file(fileName, 'w'))
    return meta

def location(fileName):
    meta = {
        u'filename': unicode(fileName),
        u'realpath': unicode(os.path.realpath(fileName)),
        }

    return meta

def dateString(dtime):
    return u'%sZ' % dtime.isoformat()

def stat(fileName):
    stat = os.lstat(fileName) #TODO Add support for other platforms (st_creator etc)
    meta = {}
    meta['size'] = stat.st_size
    meta['uid'] = stat.st_uid
    meta['uname'] = unicode(pwd.getpwuid(stat.st_uid).pw_name)
    meta['gid'] = stat.st_gid
    meta['gname'] = unicode(grp.getgrgid(stat.st_gid).gr_name)
    #TODO Time to universal time translation
    meta['atime'] = dateString(datetime.datetime.utcfromtimestamp(stat.st_atime))
    meta['ctime'] = dateString(datetime.datetime.utcfromtimestamp(stat.st_ctime))
    meta['mtime'] = dateString(datetime.datetime.utcfromtimestamp(stat.st_mtime))
    if int(stat.st_dev) == stat.st_dev:
        meta['dev'] = int(stat.st_dev)
    else:
        meta['dev'] = stat.st_dev

    meta['ino'] = stat.st_ino
    meta['mode']  = stat.st_mode
    meta['nlink'] = stat.st_nlink
    return meta


