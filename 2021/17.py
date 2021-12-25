from re import search
input = open('17.in').read()
ranges = search('target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)', input)

lowerX, upperX, lowerY, upperY = [int(ranges.group(i)) for i in range(1, 5)]

xrange = range(lowerX, upperX + 1)
yrange = range(lowerY, upperY + 1)

print("Part 1:", sum(range(1, abs(lowerY))))

count = 0

for x in range(1, upperX + 1):
    for y in range(lowerY, abs(lowerY)):
        xvelocity = x
        xpos = 0
        yvelocity = y
        ypos = 0
        while xpos <= upperX and ypos >= lowerY:
            if ypos in yrange and xpos in xrange:
                count += 1
                break
            ypos += yvelocity
            yvelocity -= 1
            xpos += xvelocity
            xvelocity = xvelocity - 1 if xvelocity > 0 else 0
        

print("Part 2:", count)