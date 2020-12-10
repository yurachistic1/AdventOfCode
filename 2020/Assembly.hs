module Assembly where

import qualified Data.Map as M
import Data.Maybe
import Parser
import Utilities

type VisitedInstructions = (M.Map Int Bool)

type Program = M.Map Int Instr

type Accumulator = Int

type Pointer = Int

data Instr = ACC Int | JMP Int | NOP Int
  deriving (Show)

data State = State
  { accum :: Accumulator,
    pointer :: Pointer
  }
  deriving (Show)

initState :: State
initState = State 0 0

-- parsing txt input and instructions --

instruction :: Parser Instr
instruction =
  ACC <$ string "acc" <* space <*> parameter
    <|> JMP <$ string "jmp" <* space <*> parameter
    <|> NOP <$ string "nop" <* space <*> parameter
  where
    parameter = int <|> (char '+' *> int)

parseInstructions :: String -> Program
parseInstructions =
  M.fromList . zip [0 ..] . mapMaybe (safeHead . runParser instruction) . lines

-- Instruction retrieval and application --

retrieveInstruction :: Program -> State -> Maybe Instr
retrieveInstruction instructions state = M.lookup (pointer state) instructions

applyInstruction :: Instr -> State -> State
applyInstruction instr (State acc pointer)
  | (ACC val) <- instr = State (acc + val) (pointer + 1)
  | (JMP val) <- instr = State acc (pointer + val)
  | (NOP _) <- instr = State acc (pointer + 1)

runProgram :: Program -> State -> [State]
runProgram instructions state
  | Nothing <- instr = []
  | Just i <- instr = state : runProgram instructions (nextState i)
  where
    nextState i = applyInstruction i state
    instr = retrieveInstruction instructions state

-- functions for day 8 (not sure if will be generally useful) --

isLooping :: VisitedInstructions -> [State] -> (State, Bool)
isLooping _ (state : []) = (state, False)
isLooping visited (current : rest)
  | isVisited = (current, True)
  | otherwise = isLooping updatedVisited rest
  where
    p = pointer current
    isVisited = visited M.! p
    updatedVisited = M.insert p True visited

createVisitedMap :: Ord k => M.Map k a -> M.Map k Bool
createVisitedMap is = M.fromList $ zip (M.keys is) (repeat False)
