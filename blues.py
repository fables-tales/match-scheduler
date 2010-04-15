#!/usr/bin/env python
import random

def not_in_rectangle(left, top, width, height, i, j):
    in_rectangle = None
    in_x = i >= left and i < left+width
    in_y = j >= top and j < top+height
    in_rectangle = in_x and in_y
    return not in_rectangle

def generateblocks(width, height, rampleft, ramptop, rampwidth, rampheight, blues = 8):
    positions = []
    for i in range(0, width):
        for j in range(0, height):
            
            if (not_in_rectangle(rampleft, ramptop, rampwidth, rampheight, i, j)):
                positions.append((i,j))
                
    blue_blocks = []
    for i in range(0, blues):
        b = random.choice(positions)
        positions.remove(b)
        blue_blocks.append(b)
    return blue_blocks


if __name__ == "__main__":
    blues = generateblocks(7, 7, 2, 3, 3, 1)
    print "+--------------+"
    chars = []
    for j in range(0, 7):
        chars.append([])
        for i in range(0, 7):
            if (i,j) in blues:
                chars[j].append("B")
            elif (j == 3 and (i == 2 or i == 3 or i == 4)):
                chars[j].append("R")
            else:
                chars[j].append(".")
    
    
    for row in chars:
        r = ""
        for c in row:
            r += c + " "
        print "| "+r+"|"
    print "+--------------+"
                
        
    print blues

