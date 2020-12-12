module Main where

{--
Reflection:
This code gives the right answers to the puzzle however does take quite a while
to run (30 secs or so, faster if compiled). I used sets to contain coordinates
of different types of grid cells and then used set operations to solve the puzzle.

--}

import Data.Maybe
import Data.Set (Set)
import qualified Data.Set as S
import Geometry
import Utilities

type Seats = Set (Point, Char)

type Occupied = Set Point

type Free = Set Point

type Floor = Set Point

type Input = (Occupied, Free, Floor)

parse :: String -> Input
parse = splitSeats . S.fromList . readGrid

splitSeats :: Seats -> Input
splitSeats seats = (occupied, free, floor)
  where
    select c = S.map fst $ S.filter ((== c) . snd) seats
    occupied = select '#'
    free = select 'L'
    floor = select '.'

isCrowded :: Int -> Bool
isCrowded = (>= 4)

isAvailable :: Int -> Bool
isAvailable = (== 0)

generation :: (Input, Bool) -> (Input, Bool)
generation (s1@(occupied, free, floor), _) = (newState, isChanged)
  where
    countNeighbrs = (S.size . S.intersection occupied . S.fromList . neighbours8)
    (freed, occ) = S.partition (isCrowded . countNeighbrs) occupied
    (taken, nOcc) = S.partition (isAvailable . countNeighbrs) free
    newOccupied = S.union taken occ
    newFree = S.union freed nOcc
    newState = (newOccupied, newFree, floor)
    isChanged = s1 == newState

part1 :: Input -> Int
part1 seats =
  countOccupied $ takeWhile changing $ iterate generation (seats, False)
  where
    changing = (== False) . snd
    countOccupied = S.size . (\(x, _, _) -> x) . fst . last

-- Part 2 --

linesOfSight :: Point -> [[Point]]
linesOfSight point =
  [allInDir dir | dir <- [North, East, South, West, NE, SE, SW, NW]]
  where
    allInDir dir = iterate (plusPoint (oneStep dir)) point

generation' :: (Input, Bool) -> (Input, Bool)
generation' (s1@(occupied, free, floor), _) = (newState, isChanged)
  where
    allSeats = S.unions [occupied, free, floor]
    countNeighbrs point =
      S.size
        . S.intersection occupied
        . S.fromList
        $ mapMaybe
          ( safeHead -- Take first visible
              . filter (not . (flip S.member floor)) -- Filter floor spaces
              . takeWhile (flip S.member allSeats) -- Take within bounds
              . tail
          )
          $ linesOfSight point
    (freed, occ) = S.partition (isCrowded' . countNeighbrs) occupied
    (taken, nOcc) = S.partition (isAvailable . countNeighbrs) free
    newOccupied = S.union taken occ
    newFree = S.union freed nOcc
    newState = (newOccupied, newFree, floor)
    isChanged = s1 == newState

isCrowded' :: Int -> Bool
isCrowded' = (>= 5)

part2 :: Input -> Int
part2 seats = countOccupied $ takeWhile changing $ iterate generation' (seats, False)
  where
    changing = (== False) . snd
    countOccupied = S.size . (\(x, _, _) -> x) . fst . last

main :: IO ()
main = do
  s <- readFile "inputs/11.in"
  let input = parse s
  putStrLn "Part 1:"
  print (part1 input)
  putStrLn "Part 2:"
  print (part2 input)
