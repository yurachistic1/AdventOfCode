from collections import defaultdict
enhancement, input = open("20.in").read().split("\n\n")
drdc = [(-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 0), (0, 1), (1, -1), (1, 0), (1, 1)]
input = [[x for x in l] for l in input.splitlines()]
grid = defaultdict(lambda: '0')

for r in range(len(input)):
    for c in range(len(input[0])):
        grid[(r,c)] = '1' if input[r][c] == '#' else '0'


def enchance(image, enhancement_alg, rows, cols, repeats):
    old_image = image
    default = '0'
    for i in range(1, repeats + 1):
        new_image = defaultdict(lambda: default)
        for r in range(-i , rows + i ):
            for c in range(-i , cols + i ):
                arr = [old_image[(r + dr, c + dc)] for dr, dc in drdc]
                char = enhancement_alg[int(''.join(arr), 2)] 
                new_image[(r,c)] = '1' if char == "#" else '0'

        old_image = new_image
        default = '1' if default == '0' else '0'
    
    return len([x for x in old_image.values() if x == '1'])

print("Part 1:", enchance(grid, enhancement, len(input), len(input[0]), 2))
print("Part 2:", enchance(grid, enhancement, len(input), len(input[0]), 50))