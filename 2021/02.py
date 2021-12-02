# Input
toInt = lambda pair: (pair[0], int(pair[1]))
course = [toInt(c.split()) for c in open("02.in").read().splitlines()]

# Part 1
distance, depth = 0, 0

for command, steps in course:
    if command == "forward":
        distance += steps
    elif command == "down":
        depth += steps
    else:
        depth -= steps

print("Part 1:", distance * depth)

# Part 2
aim, distance, depth = 0, 0, 0

for command, x in course:
    if command == "forward":
        distance += x
        depth += x * aim
    elif command == "down":
        aim += x
    else:
        aim -= x
print("Part 2:", distance * depth)