# Input
xs = [int(x) for x in open('01.in').read().splitlines()]

# Part 1
answer1 = 0

for i in range(1, len(xs)):
    if xs[i - 1] < xs[i]:
        answer1 += 1

print("Part 1:", answer1)

# Part 2
answer2 = 0

for i in range(4, len(xs) + 1):
    if sum(xs[i - 4:i - 1]) < sum(xs[i - 3:i]):
        answer2 += 1

print("Part 2:", answer2)