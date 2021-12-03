# Input
lines = [l for l in open('03.in').read().splitlines()]

def freqs(list, i):
    ones, zeros = 0, 0

    for x in list:
        if x[i] == '0':
            zeros += 1
        else:
            ones += 1

    return ones, zeros

# Part 1
gamma = ""
epsilon = ""

for i in range(len(lines[0])):
    ones, zeros = freqs(lines, i)
    most_common, least_common = ('1', '0') if ones >= zeros else ('0', '1')
    gamma += most_common
    epsilon += least_common

print("Part 1:", int(gamma, 2) * int(epsilon, 2))

# Part 2
ox_list, c02_list = lines, lines

for i in range(len(lines[0])):

    if len(ox_list) > 1:
        ones, zeros = freqs(ox_list, i)

        most_common = '1' if ones >= zeros else '0'
        
        ox_list = list(filter(lambda word: word[i] == most_common, ox_list))
        
    if len(c02_list) > 1:
        ones, zeros = freqs(c02_list, i)

        least_common = '0' if zeros <= ones else '1'

        c02_list = list(filter(lambda word: word[i] == least_common, c02_list))

print("Part 2:", int(c02_list[0], 2) * int(ox_list[0], 2))

