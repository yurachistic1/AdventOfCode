# Input
toTuple = lambda l: (int(l[0]), int(l[1]))
dots, folds = open('13.in').read().split("\n\n")

folds = folds.splitlines()
dots = set([toTuple(d.split(",")) for d in dots.splitlines()])

def foldUp(dot, y):
    return (dot[0],  dot[1] -  2 * (dot[1] - y)) if dot[1] > y else dot
    
def foldLeft(dot, x):
    return (dot[0] -  2 * (dot[0] - x), dot[1]) if dot[0] > x else dot

def parseFold(str):
    axis, num = str.split()[2].split("=")
    return (foldLeft, int(num)) if axis == "x" else (foldUp, int(num))

# Part 1
fold, num = parseFold(folds[0])
dots = set([fold(d, num) for d in dots])

print("Part 1:", len(dots))

# Part 2
for str in folds[1:]:
    fold, num = parseFold(str)
    dots = set([fold(d, num) for d in dots])

dots = list(dots)
dots.sort(key = lambda l: (l[1], l[0]))
arr = [[] for _ in range(dots[-1][1] + 1)]

for col, row in dots:
    for j in range(len(arr[row]), col):
        arr[row].append(" ")
    arr[row].append("#")

print("Part 2:")
for row in arr:
    print("".join(row))