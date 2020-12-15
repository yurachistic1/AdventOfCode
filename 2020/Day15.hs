{-# LANGUAGE BangPatterns #-}

-- Learned about forcing strict evaluation with ! and $! and function until

module Main where

import qualified Data.Map as M

input :: [Int]
input = [6, 19, 0, 5, 7, 13, 1]

startVals :: (M.Map Int Int, Int, Int)
startVals = (M.fromList $ zip input [1 ..], length input, last input)

nextNum :: (M.Map Int Int, Int, Int) -> (M.Map Int Int, Int, Int)
nextNum (!map, !turn, !last)
  | M.notMember last map = (M.insert last turn map, turn + 1, 0)
  | otherwise = (M.insert last turn map, turn + 1, turn - lastSeen)
  where
    lastSeen = map M.! last

solve :: Int -> (M.Map Int Int, Int, Int) -> (M.Map Int Int, Int, Int)
solve n map = until (\(_, t, _) -> t == n) nextNum map

main :: IO ()
main = do
  putStrLn "Part 1:"
  let (_, _, ans) = solve 2020 startVals
  print ans
  putStrLn "Part 2:"
  -- This one takes a while but gets there eventually.
  let (_, _, ans2) = solve 30000000 startVals
  print ans2