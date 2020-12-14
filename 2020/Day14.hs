module Main where

import Data.List
import qualified Data.Map as M
import Parser

type Input = [String]

type Mask = [Char]

data Instruction = Ins {addr :: Int, val :: Int}
  deriving (Show)

data Line = M Mask | I Instruction
  deriving (Show)

type Memory = M.Map Int Int

mask :: Parser Mask
mask = reverse <$ string "mask = " <*> some anyChar

instruction :: Parser Instruction
instruction =
  Ins <$ string "mem[" <*> nat <* string "] = " <*> nat

line :: Parser Line
line = M <$> mask <|> I <$> instruction

decToBinary :: Int -> [Int]
decToBinary =
  unfoldr (\x -> if x == 0 then Nothing else Just (rem x 2, quot x 2))

binToDec :: [Int] -> Int
binToDec = foldl (\acc x -> acc * 2 + x) 0

maskBit :: (Int, Char) -> Int
maskBit (x, 'X') = x
maskBit (_, '0') = 0
maskBit (_, '1') = 1

processInstruction :: Instruction -> Mask -> Memory -> Memory
processInstruction (Ins a val) m mem =
  M.insert a maskedVal mem
  where
    maskedVal = binToDec $ reverse $ zipWith (curry maskBit) valBinExtended m
    valBinExtended = (decToBinary val) ++ (repeat 0)

part1 :: Input -> Mask -> Memory -> Memory
part1 [] _ mem = mem
part1 (next : rest) m mem = case parsed of
  I i -> part1 rest m $ processInstruction i m mem
  M newMask -> part1 rest newMask mem
  where
    parsed = head $ runParser line next

part1Ans :: Input -> Int
part1Ans input = sum $ M.elems $ part1 input (repeat 'X') M.empty

-- Part two --

maskAddress :: [(Int, Char)] -> [[Int]]
maskAddress [] = []
maskAddress [(_, '1')] = [[1]]
maskAddress [(x, '0')] = [[x]]
maskAddress [(_, 'X')] = [[0], [1]]
maskAddress ((_, '1') : xs) = map (1 :) (maskAddress xs)
maskAddress ((x, '0') : xs) = map (x :) (maskAddress xs)
maskAddress ((_, 'X') : xs) =
  map (1 :) (maskAddress xs) ++ map (0 :) (maskAddress xs)

processInstructionP2 :: Instruction -> Mask -> Memory -> Memory
processInstructionP2 (Ins a val) m mem =
  M.union (M.fromList $ zip addrs (repeat val)) mem
  where
    addrs = map (binToDec . reverse) $ maskAddress $ zip addrBin m
    addrBin = (decToBinary a) ++ (repeat 0)

part2 :: Input -> Mask -> Memory -> Memory
part2 [] _ mem = mem
part2 (next : rest) m mem = case parsed of
  I i -> part2 rest m $ processInstructionP2 i m mem
  M newMask -> part2 rest newMask mem
  where
    parsed = head $ runParser line next

part2Ans :: Input -> Int
part2Ans input = sum $ M.elems $ part2 input (repeat '0') M.empty

main :: IO ()
main = do
  s <- readFile "inputs/14.in"
  let input = lines s
  putStrLn "Part 1:"
  print (part1Ans input)
  putStrLn "Part 2:"
  print (part2Ans input)