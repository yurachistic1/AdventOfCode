from collections import defaultdict

def solution():
    lines = [l for l in open('04.in').read().splitlines() if l != ""]

    numsToDraw = [int(x) for x in lines[0].split(",")]

    rows = {i: 5 for i in range(len(lines[1:]))}

    cols = {i: 5 for i in range(len(lines[1:]))}

    boardsInPlay = set(list(range(0, len(rows) // 5)))

    # has there been a winner
    winner = False

    # dict of numbers to locations
    bingo = defaultdict(list)

    # dict of locations to numbers
    locs = {}

    # winning board number
    latestWin = -1

    def calcAnswer(num):
        total = 0
        for i in range(latestWin * 5, latestWin * 5 + 5):
            for j in range(0, 5):
                if (i, j) in locs:
                    total += locs[(i, j)]
    
        return total * num


    for i, line in enumerate(lines[1:]):
        for j, num in enumerate([int(x) for x in line.split()]):
            bingo[num].append((i,j))
            locs[(i, j)] = num

    for i in numsToDraw:
        crossed_out = bingo[i]
        for row, col in crossed_out:
            rows[row] -= 1
            cols[row // 5 * 5 + col] -= 1
            locs.pop((row, col))

            if rows[row] == 0 or cols[row // 5 * 5 + col] == 0:
                latestWin = row // 5
                boardsInPlay.discard(row // 5)
                if not winner:
                    winner = True
                    print("Part 1:", calcAnswer(i))
                if not boardsInPlay:
                    print("Part 2:", calcAnswer(i))
                    return 
solution()