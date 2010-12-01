#!/usr/bin/python
import sys
import json
import os


#outputTypes
import chunkstore

outputTypes = {
    'chunkstorage': chunkstore.ChunkStorage
}


def loadOutputs():
    '''Read configuration file (~/.datajunction/outputs.json) and instantiate all mentioned outputs
    '''
    configurationFile = file(os.path.join(os.path.expanduser('~'), '.datajunction', 'outputs.json'))
    outputs = []
    outputConfs = json.load(configurationFile)
    for outputConf in outputConfs:
        outputType = outputConf['type']
        del outputConf['type']
        if not outputType in outputTypes:
            raise Exception("Could not find output type \"%s\" in known outputTypes. Valid values are: %s" % (outputType, ', '.join(outputTypes)))
        try:
            ot = outputTypes[outputType](**outputConf)
            outputs.append(ot)
        except Exception, e:
            print 'Exception:',e
            assert False
    return outputs

def main(argv):
    outputs = loadOutputs()
    for fileName in argv[1:]:
        print fileName
        for output in outputs:
            print ' >', output.name()
            output.store(fileName)
    
    
    return 0

if __name__ == '__main__':
    sys.exit(main(sys.argv))
