#!/usr/bin/python
import hashlib
import os
import json


class ChunkStorage:
    def __init__(self, storagePath = None):
        self.storagePath = storagePath
        if not os.path.exists(self.storagePath):
            raise Exception('Could not find given storage path for chunkstorage: %s' % self.storagePath)
        self.CHUNK_SIZE = 1024 * 1024
        
    def name(self):
        return 'ChunkStorage at %s' % self.storagePath
    def getMetadata(self, fileName):
        return {'filename': fileName}
    
    def store(self, fileName):
        assert self.storagePath
        assert os.path.exists(self.storagePath)
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
            #detect EOF
            if len(read) < self.CHUNK_SIZE:
                break
        del read
        
        metadata = self.getMetadata(fileName)
        metadata['chunks'] = chunkSums
        metadata['digest_sha1'] = completeDigest.hexdigest()
        metadataFileName = os.path.join(self.storagePath, 'meta', digest[:2], '%s.json' % digest)
        outputDir = os.path.dirname(metadataFileName)
        if not os.path.exists(outputDir):
            os.makedirs(outputDir)
        file(metadataFileName, 'w').write(json.dumps(metadata))
