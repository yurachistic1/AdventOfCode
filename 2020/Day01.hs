module Main where

import Data.Maybe
import qualified Data.Set as S
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

-- generic solution that I saw on reddit --

knapsack ::
  -- | number of items n to pick
  Int ->
  -- | goal sum
  Int ->
  -- | set of options
  S.Set Int ->
  -- | resulting n items that sum to the goal
  Maybe [Int]
knapsack 0 _ _ = Nothing
knapsack 1 goal xs
  | goal `S.member` xs = Just [goal]
  | otherwise = Nothing
knapsack n goal xs = listToMaybe $ do
  x <- S.toList xs
  let goal' = goal - x
      (_, ys) = S.split x xs
  case knapsack (n - 1) goal' ys of
    Nothing -> []
    Just rs -> pure (x : rs)

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
