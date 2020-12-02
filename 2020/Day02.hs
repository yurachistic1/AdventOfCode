module Main where

import qualified Data.Map as M
import Data.Maybe (mapMaybe)
import Parser
import Utilities

data Policy = Policy Int Int Char

type Password = String

type Input = [(Policy, Password)]

policy :: Parser Policy
policy = Policy <$> nat <* char '-' <*> nat <* space <*> letter

input :: Parser (Policy, Password)
input = (,) <$> policy <* string ": " <*> (some letter)

parse :: String -> Input
parse str = mapMaybe parseLine $ lines str
  where
    parseLine line = safeHead $ runParser input line

-- Part 1 --

checkIfPassValid :: (Policy, Password) -> Bool
checkIfPassValid ((Policy x y c), pass)
  | (freqOfC >= x) && (freqOfC <= y) = True
  | otherwise = False
  where
    freqMap = frequencyMap pass
    freqOfC = M.findWithDefault 0 c freqMap

part1 :: Input -> Int
part1 input = length $ filter checkIfPassValid input

-- Part 2 --

checkIfPassValid2 :: (Policy, Password) -> Bool
checkIfPassValid2 ((Policy x y c), pass)
  | (Nothing, Nothing) <- twoChars = False
  | (Nothing, Just _) <- twoChars = True
  | ((Just _), Nothing) <- twoChars = True
  | (Just a, Just b) <- twoChars = b /= a && (b == c || a == c)
  where
    inMap = indexMap pass
    twoChars = (M.lookup (x - 1) inMap, M.lookup (y - 1) inMap)

part2 :: Input -> Int
part2 = length . filter checkIfPassValid2

main :: IO ()
main = do
  s <- readFile "inputs/02.in"
  let input = parse s
  putStrLn "Part 1:"
  print (part1 input)
  putStrLn "Part 2:"
  print (part2 input)