module Main where

import Data.List

type Input = [Int]

parse :: String -> Input
parse = map read . lines

isSum :: Int -> [Int] -> Bool
isSum x ys = null [a + b | a <- ys, b <- ys, a /= b, a + b == x]

part1 :: Input -> Int
part1 list
  | isSum current preamble = current
  | otherwise = part1 $ tail list
  where
    (preamble, current : _) = splitAt 25 list

-- Part 2 --

scanRange :: Int -> [Int] -> [(Int, Int)]
scanRange target list = zip list $ filter (<= target) $ scanl1 (+) list

part2 :: [Int] -> Int -> Int
part2 list target = maximum contigousSum + minimum contigousSum
  where
    contigousSum =
      map fst $
        head $
          filter isContiguousSum $
            map (scanRange target) $
              init $
                tails list
    isContiguousSum = (\x -> target == (snd $ last x))

main :: IO ()
main = do
  s <- readFile "inputs/09.in"
  let input = parse s
  putStrLn "Part 1:"
  let p1Ans = part1 input
  print (p1Ans)
  let p2Input = takeWhile (< p1Ans) input
  putStrLn "Part 2:"
  print (part2 p2Input p1Ans)