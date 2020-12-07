module Main where

import qualified Data.Map as M
import Parser

type Colour = String

type BagContent = [(Int, Colour)]

data Bag = Bag Colour BagContent
  deriving (Show, Eq)

type Input = M.Map Colour Bag

colour :: Parser Colour
colour =
  (++) <$> some letter <* space <*> some letter
    <* (string " bag" <|> string " bags")

contents :: Parser BagContent
contents =
  many ((,) <$ space <*> nat <* space <*> colour <* (char ',' <|> char '.'))

bag :: Parser Bag
bag =
  Bag <$> colour <* string " contain" <*> contents
    <* (string "" <|> string " no other bags.")

bagToMap :: Bag -> M.Map Colour Bag
bagToMap bag@(Bag col _) = M.singleton col bag

parse :: String -> Input
parse = M.unions . map (bagToMap . head . runParser bag) . lines

checkIfContains :: Colour -> Input -> Bag -> Bool
checkIfContains _ _ (Bag _ []) = False
checkIfContains target bags (Bag _ contents)
  | elem target bagCols = True
  | otherwise = any (== True) $ (map (checkIfContains target bags) bagsInside)
  where
    bagCols = map snd contents
    bagsInside = map (bags M.!) bagCols

part1 :: Input -> Int
part1 input =
  sum $ map (countTrue . checkIfContains "shinygold" input) (M.elems input)
  where
    countTrue x = if x then 1 else 0

-- Part 2 --

countContents :: Input -> Bag -> Int
countContents _ (Bag _ []) = 0
countContents input (Bag _ contents) =
  (sum $ map fst contents)
    + (sum $ map countInside contents)
  where
    countInside = \(num, col) -> num * (countContents input (input M.! col))

part2 :: Input -> Int
part2 input = countContents input (input M.! "shinygold")

main :: IO ()
main = do
  s <- readFile "inputs/07.in"
  let input = parse s
  putStrLn "Part 1:"
  print (part1 input)
  putStrLn "Part 2:"
  print (part2 input)