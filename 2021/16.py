from math import prod

def hexToBin(s):
    spec = '{fill}{align}{width}{type}'.format(fill='0', align='>', width=len(s) * 4, type='b')
    hex = int("0x" + s, 16)
    return format(hex, spec)

class Packet:
    def __init__(self, version, type):
        self.version = version
        self.type = type
    
    def __str__(self):
        return f"Version: {self.version} Type: {self.type}"

class Operator(Packet):
    def __init__(self, version, type):
        super().__init__(version, type)
        self.children = []

    def __str__(self):
        return "{" + super().__str__() + " " + ", ".join([str(c) for c in self.children])  + "}"
    
    def versionSum(self):
        return self.version + sum([c.versionSum() for c in self.children])

    def eval(self):
        childrenEvaluated = [c.eval() for c in self.children]

        if self.type == 0:
            return sum(childrenEvaluated)
        elif self.type == 1:
            return prod(childrenEvaluated)
        elif self.type == 2:
            return min(childrenEvaluated)
        elif self.type == 3:
            return max(childrenEvaluated)
        else:
            a = childrenEvaluated[0]
            b = childrenEvaluated[1]
            if self.type == 5:
                return 1 if a > b else 0
            elif self.type == 6:
                return 1 if a < b else 0
            else:
                return 1 if a == b else 0

class Literal(Packet):
    def __init__(self, version, type, val):
        super().__init__(version, type)
        self.val = val
    
    def __str__(self):
        return "{" + super().__str__() + f" Value: {self.val}" + "}"

    def versionSum(self):
        return self.version

    def eval(self):
        return self.val

def parsePacket(s):
    version, t, s = int(s[:3], 2), int(s[3:6], 2), s[6:]

    if t == 4:
        return parseLiteral(version, t, s)
    else:
        return parseOperator(version, t, s)

def parseLiteral(version, t, s):    
    lit = ""

    while s[0] == '1':
        lit += s[1:5]
        s = s[5:]

    lit += s[1:5]
    return (Literal(version, t, int(lit, 2)), (s[5:]))

def parseOperator(version, t, s):
    ltypeId, s = s[0], s[1:]
    op = Operator(version, t)

    if ltypeId == '0':
        bitLength, s = int(s[:15], 2), s[15:]

        originalLen = len(s)

        while True:

            packet, s = parsePacket(s)
            op.children.append(packet)
            if originalLen - bitLength >= len(s):
                break
        
        return op, s
    else:
        noOfPackets, s = int(s[:11], 2), s[11:]
        for _ in range(noOfPackets):
            packet, s = parsePacket(s)
            op.children.append(packet)

        return op, s

input = hexToBin(open('16.in').read())
packet = parsePacket(input)[0]

print("Part 1:", packet.versionSum())

print("Part 2:", packet.eval())
