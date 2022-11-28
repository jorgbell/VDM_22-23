# Python code to convert an image to ASCII image.
import sys, random, argparse
import numpy as np
import math
import os
from pathlib import Path
 
from tqdm import tqdm
from PIL import Image
 
def getAverageL(image):
 
    """
    Given PIL Image, return average value of grayscale value
    """
    # get image as numpy array
    im = np.array(image)
 
    # get shape
    w,h = im.shape
 
    # get average
    return np.average(im.reshape(w*h))
 
def convertImageToAscii(fileName, cols, rows):
    """
    Given Image and dims (rows, cols) returns an m*n list of Images
    """
 
    # open image and convert to grayscale
    image = Image.open(fileName).convert('L')
 
    # store dimensions
    W, H = image.size[0], image.size[1]
    #print("input image dims: %d x %d" % (W, H))
 
    # compute width of tile
    w = W/cols
 
    # compute height of tile
    h = H/rows
     
    #print("cols: %d, rows: %d" % (cols, rows))
    #print("tile dims: %d x %d" % (w, h))
 
    # check if image size is too small
    if cols > W or rows > H:
        #print("Image too small for specified dimensions!")
        exit(0)
 
    # ascii image is a list of character strings
    aimg = []
    # generate list of dimensions
    for j in range(rows):
        y1 = int(j*h)
        y2 = int((j+1)*h)
 
        # correct last tile
        if j == rows-1:
            y2 = H
 
        # append an empty string
        aimg.append("")
 
        for i in range(cols):
 
            # crop image to tile
            x1 = int(i*w)
            x2 = int((i+1)*w)
 
            # correct last tile
            if i == cols-1:
                x2 = W

            # crop image to extract tile
            img = image.crop((x1, y1, x2, y2))
 
            # get average luminance
            avg = int(getAverageL(img))

            if(int((avg*2)/256) < 1): gsval = 'true'
            else: gsval = 'false'

            #gsval = str(int((avg*2)/256) < 1)
 
            # append ascii char to string
            aimg[j] += gsval + ","
     
    # return txt image
    return aimg

# main() function
def main():
    # # create parser
    # descStr = "This program converts an image into ASCII art."
    # parser = argparse.ArgumentParser(description=descStr)
    # # add expected arguments
    # parser.add_argument('--file', dest='imgFile', required=True)
    # parser.add_argument('--scale', dest='scale', required=False)
    # parser.add_argument('--out', dest='outFile', required=False)
    # parser.add_argument('--cols', dest='cols', required=False)
    # parser.add_argument('--morelevels',dest='moreLevels',action='store_true')
 
    # parse args
    #args = parser.parse_args()
    #folder = Path("U:\hlocal\VDM_22-23\Practicas\P2\data\Boards")

    folder = os.path.dirname(__file__)
    print('f: ' +folder)
    folder = folder / Path('\P2\data\Boards')
    print('f: ' +folder)
    # if args.outFile:
    #     outFile = args.outFile
 
    # set scale default as 0.43 which suits
    # a Courier font
    scale = 0.43
    # if args.scale:
    #     scale = float(args.scale)
 
    # if args.cols:
    #     cols = int(args.cols)
 
    print('generating ASCII art...')
    # convert image to ascii txt

    for size in tqdm(os.listdir(folder)):    
        sizePath = folder / size
        cols = int(size.split("x")[0])
        rows = int(size.split("x")[1])

        for f in tqdm(os.listdir(sizePath)): 
            filePath = sizePath / Path(f)
            if(filePath.suffix == ".png"):
                #print("\nPrinting %s" % str(filePath))
                outFile =  filePath.with_suffix(".json")
                f = open(outFile, 'w')
                f.write('{\n\t\"Solucion\": [\n')
                

                aimg = convertImageToAscii(sizePath / filePath, cols, rows)

                x = 0
                for row in aimg:
                    f.write('\t\t[' + row[:-1] + ']')
                    if(x < len(aimg) - 1): f.write(',')
                    f.write('\n')
                    x += 1
                
                f.write('\t],\n\t')
                f.write('\"Cols\": ' + str(cols) + ',\n')
                f.write('\t\"Rows\": ' + str(rows) + '\n}')
                f.close()
                #print("ASCII art written to %s" % outFile)

 
# call main
if __name__ == '__main__':
    main()