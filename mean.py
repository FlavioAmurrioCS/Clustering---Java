from sklearn.cluster import KMeans
import numpy as np

filename = 'res/gen/mapfile.txt'

def fileToNp(filename):
    ret = numpy.zeros(shape=(8580,27673))
    lines = []
    with open(filename, 'r') as rf:
        for s in rf.readlines():
            ar = s.split()
            vector = [0] * 27673
            size = len(ar)//2
            for i in range(size):
                index = i * 2
                vindex = (i*2) + 1
                vector[index] = vindex
            lines.append(vector)
    return lines

nArr = fileToNp(filename)

