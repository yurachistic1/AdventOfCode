module Main where

import qualified Data.Map as M
import Data.Maybe
import Parser
import Utilities

data Instruction = ACC Int | JMP Int | NOP Int
  deriving (Show)

type VisitedInstructions = (M.Map Int Bool)

type Accumulator = Int

type Pointer = Int

data State = State VisitedInstructions Accumulator Pointer
  deriving (Show)

initState :: State
initState = State M.empty 0 0

parameter :: Parser Int
parameter = int <|> (char '+' *> int)

instruction :: Parser Instruction
instruction =
  ACC <$ string "acc" <* space <*> parameter
    <|> JMP <$ string "jmp" <* space <*> parameter
    <|> NOP <$ string "nop" <* space <*> parameter

type Input = M.Map Int Instruction

parse :: String -> Input
parse =
  M.fromList . zip [0 ..] . mapMaybe (safeHead . runParser instruction) . lines

applyInstruction :: State -> Instruction -> State
applyInstruction (State mem acc pointer) ins
  | (ACC val) <- ins = State newMem (acc + val) (pointer + 1)
  | (JMP val) <- ins = State newMem acc (pointer + val)
  | (NOP _) <- ins = State newMem acc (pointer + 1)
  where
    newMem = M.insert pointer True mem

isLooping :: State -> Input -> (Bool, Accumulator)
isLooping state@(State mem acc pointer) input
  | (_, Nothing) <- (valInMem, currentIns) = (False, acc)
  | (Nothing, Just i) <- (valInMem, currentIns) =
    isLooping (applyInstruction state i) input
  | (Just _, _) <- (valInMem, currentIns) = (True, acc)
  where
    currentIns = input M.!? pointer
    valInMem = mem M.!? pointer

part1 :: Input -> Accumulator
part1 = snd . isLooping initState

-- Part 2 --

changeInstruction :: Input -> Pointer -> Input
changeInstruction input pointer
  | (ACC _) <- currentIns = input
  | (JMP val) <- currentIns = M.insert pointer (NOP val) input
  | (NOP val) <- currentIns = M.insert pointer (JMP val) input
  where
    currentIns = input M.! pointer

part2 :: Input -> Accumulator
part2 input =
  snd $ head $ filter (\x -> fst x == False) $ map (isLooping initState) list
  where
    list = [changeInstruction input p | p <- [0 .. length input]]

main :: IO ()
main = do
  s <- readFile "inputs/08.in"
  let input = parse s
  putStrLn "Part 1:"
  print (part1 input)
  putStrLn "Part 2:"
  print (part2 input)