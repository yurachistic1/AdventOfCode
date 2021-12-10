lines = [l for l in open('10.in').read().splitlines()]

points_tableP1 = {')': 3, ']': 57, '}': 1197, '>': 25137}
points_tableP2 = dict(zip("([{<)", range(1, 5)))

matching = {')': '(', ']': '[', '}': '{', '>': '<'}

scoreP1 = 0
scoresP2 = []

for line in lines:
    stack  = []
    incomplete = True
    for c in line:
        if c in "{[(<":
            stack.insert(0, c)
        else:
            if not stack or stack.pop(0) != matching[c]:
                scoreP1 += points_tableP1[c]
                incomplete = False
                break

    if incomplete:
        scoreP2 = 0
        for c in stack:
            scoreP2 = scoreP2 * 5 + points_tableP2[c] 
    
        scoresP2.append(scoreP2)

scoresP2.sort()

print("Part 1:", scoreP1) 
print("Part 2:", scoresP2[len(scoresP2)//2])