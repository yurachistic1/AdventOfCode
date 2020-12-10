module Main where

import Data.List
import qualified Data.Map as M
import Utilities

type Input = [Int]

parse :: String -> Input
parse = sort . (0 :) . map read . lines

part1 :: Input -> Int
part1 =
  product . M.elems . M.adjust (+ 1) (3) . frequencyMap . sort . diffs

-- Part 2 --
{--
This is not a very good solution since paths has exponential complexity.
It only works because diffs are grouped, and these groups are not
very long. I need to learn how to apply memoization in haskell.
--}

paths :: [Int] -> Integer
paths [] = 0
paths [_] = 1
paths [_, _] = 2
paths [_, _, _] = 4
paths (_ : y : z : rest) =
  paths (y : z : rest) + paths (z : rest) + paths (rest)

part2 :: Input -> Integer
part2 =
  product . map paths . filter ((== 1) . head) . group . diffs

main :: IO ()
main = do
  s <- readFile "inputs/10.in"
  let input = parse s
  putStrLn "Part 1:"
  print (part1 input)
  putStrLn "Part 2:"
  print (part2 input)