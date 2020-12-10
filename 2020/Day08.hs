module Main where

import Assembly
import qualified Data.Map as M

part1 :: Program -> Accumulator
part1 is =
  accum $ fst $ isLooping (createVisitedMap is) (runProgram is initState)

-- Part 2 --

changeInstruction :: Program -> Pointer -> Program
changeInstruction input pointer
  | (ACC _) <- currentIns = input
  | (JMP val) <- currentIns = M.insert pointer (NOP val) input
  | (NOP val) <- currentIns = M.insert pointer (JMP val) input
  where
    currentIns = input M.! pointer

part2 :: Program -> Accumulator
part2 is =
  getAcc $ filter (infinite) $ map (isLooping (createVisitedMap is)) list
  where
    getAcc = accum . fst . head
    infinite = \x -> snd x == False
    list = [runProgram (changeInstruction is p) initState | p <- [0 .. length is]]

main :: IO ()
main = do
  s <- readFile "inputs/08.in"
  let input = parseInstructions s
  putStrLn "Part 1:"
  print (part1 input)
  putStrLn "Part 2:"
  print (part2 input)