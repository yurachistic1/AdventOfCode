from collections import defaultdict
from itertools import repeat

def solution():
    lines = [l.split(" -> ") for l in open('05.in').read().splitlines()]
    straightLines = defaultdict(int)
    diagLines = defaultdict(int)

    for p1, p2 in lines:
        x1,y1 = stringToCoord(p1)
        x2,y2 = stringToCoord(p2)

        for coord in straightLine(x1, y1, x2, y2):
            straightLines[(coord)] += 1
        
        for coord in diagLine(x1, y1, x2, y2):
            diagLines[coord] += 1

    answer = 0

    for val in straightLines.values():
        if val > 1:
            answer += 1

    print("Part 1:", answer)

    for key, val in diagLines.items():
        if key not in straightLines:
            if val > 1:
                answer += 1
        elif straightLines[key] == 1:
            answer += 1

    print("Part 2:", answer)


def diagLine(x1, y1, x2, y2):
    if abs(x1 - x2) == abs(y1 - y2):
        xs = range(x1, x2 + unit(x1 - x2), unit(x1 - x2))
        ys = range(y1, y2 + unit(y1 - y2), unit(y1 - y2))
        return zip(xs, ys)
    else:
        return []

def straightLine(x1, y1, x2, y2):

    if x1 == x2:
        xs = repeat(x1)
        ys = range(y1, y2 + unit(y1 - y2), unit(y1 - y2))
        return zip(xs, ys)
    elif y1 == y2:
        xs = range(x1, x2 + unit(x1 - x2), unit(x1 - x2))
        ys = repeat(y1)
        return zip(xs, ys)

    return []

def unit(x):
    return -1 if x > 0 else 1

def stringToCoord(s):
    return [int(c) for c in s.split(",") ]

solution()