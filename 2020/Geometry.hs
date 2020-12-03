module Geometry where

data Direction = North | East | South | West
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

oneStep :: Direction -> Point
oneStep North = Point 0 1
oneStep East = Point 1 0
oneStep South = Point 0 (-1)
oneStep West = Point (-1) 0

readGrid :: String -> [(Point, Char)]
readGrid str =
  concat $
    map (\(row, lst) -> map (\(col, ch) -> (Point col row, ch)) lst) $
      zip [0, -1 ..] $
        map (zip [0 ..]) $
          lines str