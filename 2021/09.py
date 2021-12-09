# Input
h_map = [list(map(int, l)) for l in open('09.in').read().splitlines()]

dirs = [(1, 0), (-1, 0), (0, 1), (0, -1)]
risk = 0
basin_sizes = []
visited = set()

for i, row in enumerate(h_map):
    for j, val in enumerate(row):

        adj = list(map(lambda d: (i + d[1], j + d[0]) , dirs))
        inBounds = lambda p: p[0] in range(0, len(h_map)) and p[1] in range(0, len(row))
        adj = [p for p in adj if inBounds(p)]
        adj_vals = [h_map[n[0]][n[1]] for n in adj]

        # Part 1
        if val < min(adj_vals):
            risk += val + 1

            # Part 2, bfs
            if (i, j) not in visited:
                
                size = 0
                queue = [(i, j)]

                while queue:
                    r, c = queue.pop(0)
                    if h_map[r][c] != 9:
                        visited.add((r, c))
                        size += 1
                        adj = list(map(lambda d: (r + d[1], c + d[0]) , dirs))
                        adj = [p for p in adj if inBounds(p) and p not in visited]

                        for p in adj:
                            visited.add(p)
                        
                        queue += adj
                basin_sizes.append(size)
basin_sizes.sort()

print("Part 1:", risk)
print("Part 2:", basin_sizes[-1] * basin_sizes[-2] * basin_sizes[-3])