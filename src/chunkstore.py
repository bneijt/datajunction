#!/usr/bin/python
import hashlib
import os
import metadata
import datetime

class ChunkStorage:
    def __init__(self, storagePath = None):
        self.storagePath = storagePath
        if not os.path.exists(self.storagePath):
            raise Exception('Could not find given storage path for chunkstorage: %s' % self.storagePath)
        self.CHUNK_SIZE = 1024 * 1024
        
    def name(self):
        return 'ChunkStorage at %s' % self.storagePath

    def getMetadata(self, fileName):
        md = {}
        md['ctime'] = datetime.datetime.utcnow().isoformat() #Time the entry was created
        md['stat'] = metadata.stat(fileName)
        md['location'] = metadata.location(fileName)
        return md
    
    def store(self, fileName):
        assert self.storagePath #We muset have a storagepath
        assert fileName[0] ==  '/' #fileName should be absolute path
        assert os.path.exists(self.storagePath) #Storepath must exist, otherwise we might create it later on.
        if os.path.isdir(fileName):
            raise Exception('There is no code to handle directories yet')
        i = file(fileName)
        chunkSums = []
        completeDigest = hashlib.sha1()
        while True:
            read = i.read(self.CHUNK_SIZE)
            h = hashlib.sha1()
            h.update(read)
            completeDigest.update(read)
            digest = h.hexdigest()
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
        md['chunks'] = chunkSums
        md['digest_sha1'] = completeDigest.hexdigest()
        metadataFileName = os.path.join(self.storagePath, 'meta', digest[:2], '%s.json' % digest)
        metadata.appendMeta(metadataFileName, md)
