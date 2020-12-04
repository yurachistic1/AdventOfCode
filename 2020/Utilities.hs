module Utilities where

import qualified Data.Map as M

safeHead :: [a] -> Maybe a
safeHead [] = Nothing
safeHead (x : _) = Just x

frequencyMap :: Ord a => [a] -> M.Map a Int
frequencyMap = M.unionsWith (+) . map (flip M.singleton 1)

indexMap :: [a] -> M.Map Int a
indexMap = M.fromList . zip [0 ..]

keyValue :: Eq a => a -> [a] -> Maybe ([a], [a])
keyValue val list
  | (k, _ : v) <- kv = Just (k, v)
  | otherwise = Nothing
  where
    kv = break (== val) list

-- From Data.List.SplitOn --
-- Copied to just better understand how it works

splitOn :: (a -> Bool) -> [a] -> [[a]]
splitOn _ [] = []
splitOn f l@(x : xs)
  | f x = splitOn f xs
  | otherwise = let (h, t) = break f l in h : (splitOn f t)

splitOn' :: (a -> Bool) -> [a] -> [[a]]
splitOn' f xs = split xs
  where
    split xs = case break f xs of
      (chunk, []) -> chunk : []
      (chunk, _ : rest) -> chunk : split rest
