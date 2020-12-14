module Main where

import Data.List
import Data.List.Split

type Input = [Int]

target = 1000434

parse :: String -> Input
parse = map read . filter (/= "x") . splitOn ","

part1 :: Int -> Input -> Int
part1 t input = a * b
  where
    (a, b) = head $ sortOn snd $ map (\x -> (x, x - (mod t x))) input

-- Part 2 --
-- Chinese remainder theorem. Hopefully will do later --

main :: IO ()
main = do
  s <- readFile "inputs/13.in"
  let input = parse s
  putStrLn "Part 1:"
  print (part1 target input)
  putStrLn "Part 2:"

--print (part2 input)