module Main where

import Data.List.Split
import qualified Data.Set as S

type Input = [[String]]

parse :: String -> Input
parse = map words . splitOn "\n\n"

part1 :: Input -> Int
part1 = sum . map (S.size . S.unions . map S.fromList)

part2 :: Input -> Int
part2 = sum . map (S.size . intersections . map S.fromList)
  where
    intersections = foldl1 S.intersection

main :: IO ()
main = do
  s <- readFile "inputs/06.in"
  let input = parse s
  putStrLn "Part 1:"
  print (part1 input)
  putStrLn "Part 2:"
  print (part2 input)