from math import exp, floor, ceil
from itertools import permutations

class Node:
    def __init__(self):
        self.right = None
        self.left = None
        self.val = None

    def __str__(self):
        if self.val is None:
            return f'[{str(self.left)},{str(self.right)}]'
        else:
            return str(self.val)

    def inorderExplode(self, depth):
        if self.val is None:
            return self.left.inorderExplode(depth + 1) + [(depth, self)] + self.right.inorderExplode(depth + 1)
        else:
            return []

    def inorderSplit(self):
        if self.val is None:
            return self.left.inorderSplit() + self.right.inorderSplit()
        else:
            return [(self.val, self)]

    def magnitude(self):
        if self.val is None:
            return self.left.magnitude() * 3 + self.right.magnitude() * 2
        else:
            return self.val


def parseTree(str):
    node = Node()
    if str[0] == '[':
        node.left, str = parseTree(str[1:])
        node.right, str = parseTree(str[1:])
        return node, str[1:]
    else:
        node.val = int(str[0])
        return node, str[1:]

def add(n1, n2):
    node = Node()
    node.left = n1
    node.right = n2
    return node

def explode(n):
    arr = n.inorderExplode(0)

    for i, (depth, node) in enumerate(arr):
        if depth == 4 and node.right.val is not None and node.left.val is not None:
            if i > 0:
                current = arr[i - 1][1].left
                while current.val is None:
                    current = current.right
                current.val += node.left.val
            if i < len(arr) - 1:
                current = arr[i + 1][1].right
                while current.val is None:
                    current = current.left
                current.val += node.right.val
            
            node.left = None
            node.right = None
            node.val = 0

def split(n):
    arr = n.inorderSplit()
    changed = False

    for val, node in arr:
        if val > 9:
            changed = True
            a = Node()
            b = Node()
            a.val = floor(val/2)
            b.val = ceil(val/2)
            
            node.left = a
            node.right = b
            node.val = None
            break
    return changed

def reduce(n):
    explode(n)
    while split(n):
        explode(n)
    return n

input = [l for l in open('18.in').read().splitlines()] 

initial = parseTree(input[0])[0]
for l in input[1:]:
    initial = reduce(add(initial, parseTree(l)[0]))
print("Part 1:", initial.magnitude())

highestMag = 0
perms = permutations(input, 2)
for p in perms:
    n = add(parseTree(p[0])[0], parseTree(p[1])[0])
    n = reduce(n)
    highestMag = max(highestMag, n.magnitude())

print("Part 2:", highestMag)