-- Accidentally discarded my original solution in VS code,
-- originally didn't catch the binary trick and used binary search

module Main where

import Data.List

type Input = [String]

binaryToDec :: String -> Int
binaryToDec str =
  sum $ map (\(x, y) -> if elem y "BR" then 2 ^ x else 0) $ zip [0 ..] $ reverse str

part1 :: Input -> Int
part1 = maximum . map binaryToDec

part2 :: Input -> Int
part2 input =
  head $ [minimum seats .. maximum seats] \\ seats
  where
    seats = map binaryToDec input

main = do
  s <- readFile "inputs/05.in"
  let input = lines s
  print $ part1 input
  print $ part2 input