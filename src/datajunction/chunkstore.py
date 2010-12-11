#!/usr/bin/python
import hashlib
import os
import metadata
import datetime

class ChunkStorage:
    def __init__(self,
            storagePath = None,
            ):
        self.storagePath = storagePath
        self.CHUNK_SIZE = 1024 * 1024

    def init(self):
        if not os.path.exists(self.storagePath):
            raise Exception('Could not find given storage path for chunkstorage: %s' % self.storagePath)
        
    def name(self):
        return u'ChunkStorage at %s' % self.storagePath

    def getMetadata(self, fileName):
        md = {}
        md['ctime'] = metadata.dateString(datetime.datetime.utcnow()) #Time the entry was created
        md['stat'] = metadata.stat(fileName)
        md['location'] = metadata.location(fileName)
        return md

    def store(self, fileName):
        assert self.storagePath #We muset have a storagepath
        assert fileName[0] ==  '/' #fileName should be absolute path
        assert os.path.exists(self.storagePath) #Storepath must exist, otherwise we might create it later on.
        #Broken symlinks do not exist, so no: assert os.path.exists(fileName)
        if os.path.islink(fileName):
            self.storeLink(fileName)
            return
        if os.path.isdir(fileName):
            self.storeDirectory(fileName)
            return
        if os.path.isfile(fileName):
            self.storeFile(fileName)
            return
        raise Exception('Unable to store file in %s\n\tFilename: %s' % (self.name(), fileName))
    
    def storeLink(self, fileName):
        '''Pricate specialization function
            Links are stored much like directories. Their type is link
        '''
        assert os.path.islink(fileName)
        md = self.getMetadata(fileName)
        md['type'] = u'link'
        md['target'] = os.readlink(fileName)
        digest = unicode(hashlib.sha1(fileName).hexdigest())
        metadataFileName = os.path.join(self.storagePath, 'tree', digest[:2], u'%s.json' % digest)
        metadata.appendMeta(metadataFileName, md)


    def storeDirectory(self, fileName):
        '''Private specialization function
            Directories are stored by using the path to the file as input for the hash function.
            All the hashed files are placed in a special directory called "tree" because
            they only describe parts of the file system tree.
        '''
        assert os.path.isdir(fileName)
        md = self.getMetadata(fileName)
        md['type'] = u'directory'
        digest = unicode(hashlib.sha1(fileName).hexdigest())
        metadataFileName = os.path.join(self.storagePath, 'tree', digest[:2], u'%s.json' % digest)
        metadata.appendMeta(metadataFileName, md)
        

    def storeFile(self, fileName):
        '''Private specialization function'''
        i = file(fileName)
        chunkSums = []
        completeDigest = hashlib.sha1()
        while True:
            read = i.read(self.CHUNK_SIZE)
            h = hashlib.sha1()
            h.update(read)
            completeDigest.update(read)
            digest = unicode(h.hexdigest())
            chunkSums.append(digest)
            #Store chunk
            chunkFileName = os.path.join(self.storagePath, 'data', digest[:2], digest)
            #Only write if not existing
            if not os.path.exists(chunkFileName):
                outputDir = os.path.dirname(chunkFileName)
                if not os.path.exists(outputDir):
                    os.makedirs(outputDir)
                file(chunkFileName, 'w').write(read)
                del outputDir
            else:
                #If the file does exist, do some sanity checks
                if not os.path.getsize(chunkFileName) == len(read):
                    raise Exception('Chunk with name %s already exists but it does not have the same size as the chunk I just read (with the same name) %i on disk, %i read' % (chunkFileName, os.path.getsize(chunkFileName), len(read)))
            #detect EOF
            if len(read) < self.CHUNK_SIZE:
                break
        del read
        
        md = self.getMetadata(fileName)
        md['type'] = u'file'
        md['chunks'] = chunkSums
        md['digest_sha1'] = unicode(completeDigest.hexdigest())
        metadataFileName = os.path.join(self.storagePath, 'meta', digest[:2], u'%s.json' % digest)
        metadata.appendMeta(metadataFileName, md)
