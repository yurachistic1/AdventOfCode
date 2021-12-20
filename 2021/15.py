from heapq import heappop, heappush

# Input
dr = [1, -1, 0, 0]
dc = [0, 0, -1, 1]

risk_map = [[int(c) for c in l] for l in open("15.in").read().splitlines()]
Rs = len(risk_map)
Cs = len(risk_map[0])
scaleFactor = 1

def dijkstra():

    Q = [(0, (0, 0))]
    dist = {(0, 0): 0}

    while Q:
        d, current = heappop(Q)

        for n in neigbours(current):
            rq, rmod = divmod(n[0], Rs)
            cq, cmod = divmod(n[1], Cs)
            val = risk_map[rmod][cmod] + rq + cq
            val = val % 10 + 1 if val > 9 else val
            alt = d + val
            if n not in dist or alt < dist[n]:
                dist[n] = alt
                heappush(Q, (alt, n))

    return dist[(Rs * scaleFactor - 1, Cs * scaleFactor - 1)]


def neigbours(rc):

    n = []

    for i in range(4):
        new_r = dr[i] + rc[0]
        new_c = dc[i] + rc[1]

        if new_r in range(0, Rs * scaleFactor) and new_c in range(0, Cs * scaleFactor):
            n.append((new_r, new_c))

    return n

print("Part 1:", dijkstra())

scaleFactor = 5

print("Part 2:", dijkstra())