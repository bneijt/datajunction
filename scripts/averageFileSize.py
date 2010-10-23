#!/usr/bin/python
import os
import tempfile
import numpy 
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt
import math


def main():
    sizes = []

    queue = ['/']
    while len(queue):
        path = queue.pop()
        #Skip links, ttys and dev stuff
        if not os.path.exists(path):
            continue
        if os.path.islink(path):
            continue
        if os.path.isdir(path):
            try:
                queue.extend([os.path.join(path, i) for i in os.listdir(path)])
            except OSError, e:
                #Permission denied
                pass
            continue
        size = os.path.getsize(path)
        sizes.append(size)
    

    # the histogram of the data
    mean = numpy.mean(sizes)
    std = numpy.std(sizes)
    median = numpy.median(sizes)

    n, bins, patches = plt.hist(sizes, 50, facecolor='green', alpha=0.75)
    plt.axvspan(max(0, mean - std), mean + std, alpha=0.2)
    
    plt.xlabel('File size')
    plt.ylabel('Count')
    plt.title(r'$n=%i$ $\mu=%d$ $median=%i$ $\sigma=%i$' % (len(sizes), mean, median, std))
    plt.grid(True)
    plt.savefig("/tmp/graph.png")




if __name__ == '__main__':
    main()
