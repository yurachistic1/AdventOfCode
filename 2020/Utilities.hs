module Utilities where

import qualified Data.Map as M

safeHead :: [a] -> Maybe a
safeHead [] = Nothing
safeHead (x : _) = Just x

frequencyMap :: Ord a => [a] -> M.Map a Int
frequencyMap = M.unionsWith (+) . map (flip M.singleton 1)

indexMap :: [a] -> M.Map Int a
indexMap = M.fromList . zip [0 ..]

tails :: [a] -> [(a, [a])]
tails [x] = [(x, [])]
tails (x : xs) = (x, xs) : tails xs