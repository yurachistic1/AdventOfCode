from statistics import median

# Input
crabs = [int(crab) for crab in open('07.in').read().split(",")]

# Part 1
med = int(median(crabs))

p1 = 0

for crab in crabs:
    p1 += abs(med - crab)

print("Part 1:", p1)

# Part 2
average = sum(crabs)//len(crabs)

p2 = 0

for crab in crabs:
    p2 += sum(range(1, abs(average - crab) + 1))

print("Part 2:", p2)

