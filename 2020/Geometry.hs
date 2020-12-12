module Geometry where

data Direction = North | East | South | West | NE | SE | SW | NW
  deriving (Show)

turnLeft :: Direction -> Direction
turnLeft North = West
turnLeft East = North
turnLeft South = East
turnLeft West = South

turnRight :: Direction -> Direction
turnRight North = East
turnRight East = South
turnRight South = West
turnRight West = North

data Point = Point Int Int
  deriving (Eq, Ord, Show)

origin :: Point
origin = Point 0 0

plusPoint :: Point -> Point -> Point
plusPoint (Point x1 y1) (Point x2 y2) = Point (x1 + x2) (y1 + y2)

minusPoint :: Point -> Point -> Point
minusPoint (Point x1 y1) (Point x2 y2) = Point (x1 - x2) (y1 - y2)

timesPoint :: Int -> Point -> Point
timesPoint x (Point x1 y1) = Point (x * x1) (y1 * x)

normPoint :: Point -> Int
normPoint (Point x y) = abs (x) + abs (y)

distance :: Point -> Point -> Int
distance p1 p2 = normPoint $ minusPoint p1 p2

rotateRight :: Point -> Point
rotateRight (Point x y) = Point y (- x)

rotateLeft :: Point -> Point
rotateLeft (Point x y) = Point (- y) x

moveXInDir :: Int -> Direction -> Point -> Point
moveXInDir steps dir = plusPoint (timesPoint steps $ oneStep dir)

oneStep :: Direction -> Point
oneStep North = Point 0 1
oneStep East = Point 1 0
oneStep South = Point 0 (-1)
oneStep West = Point (-1) 0
oneStep NE = Point 1 1
oneStep SE = Point 1 (-1)
oneStep SW = Point (-1) (-1)
oneStep NW = Point (-1) 1

neighbours4 :: Point -> [Point]
neighbours4 point =
  [nextIn dir | dir <- [North, East, South, West]]
  where
    nextIn dir = plusPoint (oneStep dir) point

neighbours8 :: Point -> [Point]
neighbours8 point =
  [nextIn dir | dir <- [North, East, South, West, NE, SE, SW, NW]]
  where
    nextIn dir = plusPoint (oneStep dir) point

readGrid :: String -> [(Point, Char)]
readGrid str =
  concat $
    map (\(row, lst) -> map (\(col, ch) -> (Point col row, ch)) lst) $
      zip [0, -1 ..] $
        map (zip [0 ..]) $
          lines str