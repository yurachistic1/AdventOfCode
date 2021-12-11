from collections import deque
# Input
grid = [[int(x) for x in l] for l in open('11.in').read().splitlines()]

inBounds = lambda rc: (0 <= rc[0] < 10) and (0 <= rc[1] < 10) 
drdc = [(1, 0), (-1, 0), (1, 1), (-1, 1), (-1, -1), (1, -1), (0, 1), (0, -1)]

count = 0
step = 0
synced = False

while not synced:
    for r in range(10):
        for c in range(10):
                
            queue = deque([(r,c)])

            while queue:
        
                row, col = queue.popleft()
                if grid[row][col] == 9:
                
                    adj = [(row + dr, col + dc) for dr, dc in drdc]
                    adj = [rc for rc in adj if inBounds(rc)]
                    queue.extend(adj)
                    count += 1
                    
            
                grid[row][col] += 1
    step += 1
    
    # Part 1
    if step == 100:
        print("Part 1:", count)

    # Part 2
    flash_count = 0
    for r in range(10):
        for c, val in enumerate(grid[r]):
            if val > 9:
                grid[r][c] = 0
                flash_count += 1
    if flash_count == 100:
        print("Part 2:", step)
        synced = True
    
    