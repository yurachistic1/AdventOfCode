-- Lucky for me I have a whole module (from uni lectures) that does pretty much
-- everything this puzzle asks for :)

module Main where

import Geometry
import Utilities

type ShipLoc = Point

type WayPoint = Point

type Input = [String]

parse :: String -> Input
parse = lines

executeAction :: String -> (ShipLoc, Direction) -> (ShipLoc, Direction)
executeAction (action : steps) (loc, dir) = case action of
  'N' -> (moveXInDir numVal North loc, dir)
  'S' -> (moveXInDir numVal South loc, dir)
  'E' -> (moveXInDir numVal East loc, dir)
  'W' -> (moveXInDir numVal West loc, dir)
  'L' -> (loc, repeatF (numVal `div` 90) turnLeft dir) -- Repeat left turn
  'R' -> (loc, repeatF (numVal `div` 90) turnRight dir)
  'F' -> (moveXInDir numVal dir loc, dir)
  where
    numVal = read steps :: Int

part1 :: Input -> (ShipLoc, Direction) -> Int
part1 [] (loc, _) = distance loc (Point 0 0)
part1 (x : xs) state = part1 xs $ executeAction x state

-- Part 2 --

executeAction' :: String -> (ShipLoc, WayPoint) -> (ShipLoc, WayPoint)
executeAction' (action : steps) (loc, waypoint) = case action of
  'N' -> (loc, moveXInDir numVal North waypoint)
  'S' -> (loc, moveXInDir numVal South waypoint)
  'E' -> (loc, moveXInDir numVal East waypoint)
  'W' -> (loc, moveXInDir numVal West waypoint)
  'L' -> (loc, repeatF (numVal `div` 90) rotateLeft waypoint)
  'R' -> (loc, repeatF (numVal `div` 90) rotateRight waypoint)
  'F' -> (plusPoint (timesPoint numVal waypoint) loc, waypoint)
  where
    numVal = read steps :: Int

part2 :: Input -> (ShipLoc, WayPoint) -> Int
part2 [] (loc, _) = distance loc (Point 0 0)
part2 (x : xs) state = part2 xs $ executeAction' x state

main :: IO ()
main = do
  s <- readFile "inputs/12.in"
  let input = parse s
  putStrLn "Part 1:"
  print (part1 input (Point 0 0, East))
  putStrLn "Part 2:"
  print (part2 input (Point 0 0, Point 10 1))