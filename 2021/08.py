# Input
lines = [l for l in open('08.in').read().splitlines()]

# Part 1
out_ls = [list(map(len, line.partition(" | ")[2].split())) for line in lines]
p1 = len([l for output in out_ls for l in output if l in [2,3,4,7]])
print("Part 1:", p1)

# Part 2
def decode(line):
    codes, _, out = line.partition(" | ")
    codes = codes.split()
    out = out.split()

    codesToNums = {c: -1 for c in codes}
    numsToCodes = {}
    seg = {"top":'', "topL":'', "topR":'', "mid":'', "botL":'', "botR":'', "bot":''}

    one = [c for c in codes if len(c) == 2][0]
    codesToNums[one] = 1
    numsToCodes[1] = set(one)

    four = [c for c in codes if len(c) == 4][0]
    codesToNums[four] = 4
    numsToCodes[4] = set(four)

    seven = [c for c in codes if len(c) == 3][0]
    codesToNums[seven] = 7
    numsToCodes[7] = set(seven)

    eight = [c for c in codes if len(c) == 7][0]
    codesToNums[eight] = 8
    numsToCodes[8] = set(eight)

    seg["top"] = list(numsToCodes[7].difference(numsToCodes[1]))[0]

    ZeroSixNine = [c for c in codes if len(c) == 6]

    six = [c for c in ZeroSixNine if not numsToCodes[1].issubset(set(c))][0]
    codesToNums[six] = 6
    numsToCodes[6] = set(six)

    seg["topR"] = list(numsToCodes[1].difference(numsToCodes[6]))[0]
    seg["botR"] = list(numsToCodes[1].difference(set(seg["topR"])))[0]

    TwoThreeFive = [c for c in codes if len(c) == 5]

    five = [c for c in TwoThreeFive if set(c).issubset(numsToCodes[6])][0]
    codesToNums[five] = 5
    numsToCodes[5] = five

    seg["botL"] = list(numsToCodes[6].difference(numsToCodes[5]))[0]

    nine = [c for c in ZeroSixNine if seg["botL"] not in c][0]
    codesToNums[nine] = 9
    numsToCodes[9] = nine

    zero = [c for c in ZeroSixNine if c != six and c != nine][0]
    codesToNums[zero] = 0
    numsToCodes[0] = zero

    seg["mid"] = list(numsToCodes[8].difference(numsToCodes[0]))[0]
    seg["topL"] = list(numsToCodes[4].difference(set([
        seg["mid"], seg["topR"], seg["botR"]
    ])))[0]
    seg["bot"] = list(numsToCodes[8].difference(set([
        seg["top"], seg["topL"], seg["mid"], seg["topR"], seg["botL"], seg["botR"]
    ])))[0]

    two = [c for c in TwoThreeFive if seg["botL"] in c][0]
    codesToNums[two] = 2
    numsToCodes[2] = two

    three = [c for c in TwoThreeFive if c != two and c != five][0]
    codesToNums[three] = 3
    numsToCodes[3] = three

    digits = [0] * 10
    for code, val in codesToNums.items():
        digits[val] = set(code)

    outSets = [set(o) for o in out]
    out = [0] * 4
    for i, code in enumerate(outSets):
        out[i] = digits.index(code)
    
    return out[0] * 1000 + out[1] * 100 + out[2] * 10 + out[3]
 

p2 = 0

for line in lines:
    p2 += decode(line)

print("Part 2:", p2)

