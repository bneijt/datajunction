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
        ot = outputTypes[outputType](**outputConf)
        try:
            ot.init()
            outputs.append(ot)
        except Exception, e:
            print 'Failed to initialize', ot.name()
            print '  %s' % str(e)
    return outputs

