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
                positions.append((i+1,j+1))
                
    blue_blocks = []
    for i in range(0, blues):
        blue_blocks.append(random.choice(positions))
    return blue_blocks


if __name__ == "__main__":
    blues = generateblocks(7, 7, 2, 3, 3, 1)
    print blues

