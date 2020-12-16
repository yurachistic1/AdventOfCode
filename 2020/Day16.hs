module Main where

import Data.List
import qualified Data.Set as S
import Parser

data Rule = Rule
  { name :: String,
    range1 :: [Int],
    range2 :: [Int]
  }
  deriving (Show)

type Ticket = [Int]

type NearbyTickets = [Ticket]

data Input = Input
  { tRules :: [Rule],
    myT :: Ticket,
    nearbyT :: NearbyTickets
  }
  deriving (Show)

rules :: Parser [Rule]
rules = sepBy1 rule (string "\n")
  where
    rule = Rule <$> name <* string ": " <*> range <* string " or " <*> range
    name = some letter <|> (++) <$> some letter <* space <*> some letter
    range = enumFromTo <$> nat <* char '-' <*> nat

ticket :: Parser [Int]
ticket = sepBy1 nat (char ',')

parse :: String -> Input
parse = head . runParser input
  where
    input =
      Input <$> rules <* string "\n\nyour ticket:\n" <*> ticket
        <* string "\n\nnearby tickets:\n" <*> sepBy1 ticket (string "\n")

errorRate :: S.Set Int -> Ticket -> Int
errorRate allRanges ticket = sum $ filter (flip S.notMember allRanges) ticket

part1 :: Input -> Int
part1 (Input rs _ tickets) = sum $ map (errorRate allRanges) tickets
  where
    allRanges = S.fromList $ foldl (\xs (Rule _ r3 r4) -> xs ++ r3 ++ r4) [] rs

-- Part 2 --

isValid :: S.Set Int -> Ticket -> Bool
isValid allRanges ticket = and $ map (flip S.member allRanges) ticket

possibleRules :: [Rule] -> Int -> [Rule]
possibleRules rs val = filter (isPossible val) rs
  where
    isPossible v (Rule _ r1 r2) = (v `elem` r1) || (v `elem` r2)

collectRules :: ([Int], [String]) -> (Int, [String]) -> ([Int], [String])
collectRules (is, rSet) (i, rList) = (is ++ [i], union rSet rList)

part2 :: Input -> Integer
part2 (Input rs mT tickets) =
  findAns $ filter (isPrefixOf "departure" . snd) $ zip pos rSet
  where
    allRanges = S.fromList $ foldl (\xs (Rule _ r3 r4) -> xs ++ r3 ++ r4) [] rs
    validTs = filter (isValid allRanges) tickets
    elimRs rs ts = map (uncurry possibleRules) (zip rs ts)
    ruleLists = replicate (length mT) rs
    applicableRs =
      zip ([0 ..]) $ map (map name) $ last $ scanl elimRs ruleLists validTs
    sortedAppRs = sortOn (length . snd) applicableRs
    (pos, rSet) = foldl collectRules ([], []) sortedAppRs
    findAns = product . map (fromIntegral . (mT !!)) . map fst

main :: IO ()
main = do
  s <- readFile "inputs/16.in"
  let input = parse s
  putStrLn "Part 1:"
  print (part1 input)
  putStrLn "Part 2:"
  print (part2 input)