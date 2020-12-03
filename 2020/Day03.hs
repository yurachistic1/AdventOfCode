module Main where

import Geometry ( Point(..), readGrid )
import Data.Maybe ( mapMaybe )

import qualified Data.Map as M

type Input = M.Map Point Char
type Width = Int
type Height = Int

parse :: String -> Input
parse = M.fromList . readGrid

width :: String -> Width
width = length . head . lines

height :: String -> Int
height = length . lines

goDown :: (Int, Int) -> Width -> Point -> Point 
goDown (stepRight, stepDown) w (Point x y) = 
  Point ((x + stepRight) `mod` w) (y + stepDown)

part1 :: Input -> Width -> Height -> (Int, Int) -> Int
part1 input w h (stepRight, stepDown) = 
  length $ filter (=='#') $ mapMaybe (flip M.lookup input) $ take h $ coords
    where
      coords = iterate (goDown (stepRight, stepDown) w) (Point 0 0)

part2 :: Input -> Width -> Height -> Integer
part2 input w h = 
  product $ map fromIntegral $ map (part1 input w h) gradients
  where 
    gradients = [(1,-1),(3,-1),(5,-1), (7, -1),(1, -2)] 

main :: IO ()
main = do
  s <- readFile "inputs/03.in"
  let input = parse s
  let w = width s
  let h = height s
  putStrLn "Part 1:"
  print (part1 input w h (3,-1))
  putStrLn "Part 2:"
  print (part2 input w h)