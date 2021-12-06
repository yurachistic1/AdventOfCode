def solution(n):

    state = {i: 0 for i in range(9)}
    for val in [int(l) for l in open('06.in').read().split(",")]:
        state[val] += 1

    for _ in range(n):
        new_state = {i: 0 for i in range(9)}

        for j in range(8, 0, -1):
            new_state[j - 1] = state[j]
    
        new_state[8] = state[0]
        new_state[6] += state[0]

        state = new_state
    
        
    total = 0
    for val in state.values():
        total += val
    return total

print("Part 1:", solution(80))
print("Part 2:", solution(256))