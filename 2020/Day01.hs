module Main where

import Testing

type Input = [Int]

testInput :: [Int]
testInput = [1721, 979, 366, 299, 675, 1456]

parse :: String -> Input
parse str = map read $ lines str

-- part 1

test1 :: [(Result, Int, Int)]
test1 = test part1 [(testInput, 514579)]

part1 :: Input -> Int
part1 input = head [a * b | a <- input, b <- input, a + b == 2020]

-- part2

test2 :: [(Result, Int, Int)]
test2 = test part2 [(testInput, 241861950)]

part2 :: Input -> Int
part2 input =
  head [a * b * c | a <- input, b <- input, c <- input, a + b + c == 2020]

main :: IO ()
main = do
  s <- readFile "inputs/01.in"
  let input = parse s
  print "Part 1:"
  putStr $ unlines $ map toString $ test1
  print (part1 input)
  putStrLn "\nPart 2:"
  putStr $ unlines $ map toString $ test2
  print (part2 input)
