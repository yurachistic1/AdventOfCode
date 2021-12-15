from collections import Counter

#Input
template, rules = open('14.in').read().split("\n\n")

toTuple = lambda str: tuple(str.split(" -> "))
rules = dict(toTuple(s) for s in rules.splitlines())

counts = Counter([a + b for a, b in zip(template, template[1:])])

for i in range(40):
    new_counts = Counter()
    for k, v in counts.items():
        new_counts[k[0] + rules[k]] += counts[k]
        new_counts[rules[k] + k[1]] += counts[k]

    counts = new_counts

    if i in [9, 39]:
        letter_count = Counter()
        for k, v in counts.items():
            letter_count[k[0]] += v

        letter_count[template[-1]] += 1 
        ans = max(letter_count.values()) - min(letter_count.values())
        if i == 9: 
            print("Part 1:", ans) 
        else: 
            print("Part 2:", ans)


