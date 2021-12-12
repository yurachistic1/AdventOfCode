from collections import defaultdict

# Input
lines = [l.split("-") for l in open('12.in').read().splitlines()]
edges = defaultdict(list)

for v1, v2 in lines:
    edges[v1].append(v2)
    edges[v2].append(v1)

# Part 1
def dfs1(cave, visited):
    new_vis = visited.copy()
    if cave == "end":
        return 1
    
    if cave not in visited:
        if cave.islower():
            new_vis.add(cave)
        
        adj = [c for c in edges[cave]]

        return sum(map(lambda c: dfs1(c, new_vis), adj))
    else:
        return 0

print("Part 1:", dfs1("start", set()))

# Part 2
def dfs2(cave, visited, twice):
    new_vis = visited.copy()
    if cave == "end":
        return 1
    
    adj = [c for c in edges[cave]]

    if cave not in visited:
        if cave.islower():
            new_vis.add(cave)
        return sum(map(lambda c: dfs2(c, new_vis, twice), adj))

    elif cave in visited and twice is None and cave != "start":
        return sum(map(lambda c: dfs2(c, new_vis, cave), adj))
    else:
        return 0

print("Part 2:", dfs2("start", set(), None))

