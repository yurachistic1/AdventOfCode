module Day17 where

import qualified Data.Set as Set
import Geometry
import Utilities

{--
This is a solution to part 2 of the puzzle,
solution to part 1 is essentially the same
but instead of Point4D, it's point3D with
other minor changes. I thought a bit about how to
make a more generalized version but haven't
come up with anything so far.
--}

input =
  "#.#####.\n\
  \#..##...\n\
  \.##..#..\n\
  \#.##.###\n\
  \.#.#.#..\n\
  \#.##..#.\n\
  \#####..#\n\
  \..#.#.##"

data Point4D = Point4D Int Int Int Int
  deriving (Show, Eq, Ord)

type Occupied = Set.Set Point4D

initState :: Occupied
initState = Set.fromList $ map (convertTo4d . fst) $ filter ((== '#') . snd) $ readGrid input

convertTo4d :: Point -> Point4D
convertTo4d (Point x y) = Point4D x y 0 0

neighbours :: Point4D -> Set.Set Point4D
neighbours og@(Point4D x y z w) =
  Set.filter (/= og) $
    Set.fromList
      [(Point4D a b c d) | a <- l1, b <- l2, c <- l3, d <- l4]
  where
    l1 = [x -1, x, x + 1]
    l2 = [y -1, y, y + 1]
    l3 = [z -1, z, z + 1]
    l4 = [w -1, w, w + 1]

rule :: Bool -> Int -> Bool
rule True numNeighbours
  | numNeighbours == 2 || numNeighbours == 3 = True
  | otherwise = False
rule False numNeighbours
  | numNeighbours == 3 = True
  | otherwise = False

isOccupied :: Occupied -> Point4D -> Bool
isOccupied occ point = rule currentState numNeighbours
  where
    currentState = (Set.member point occ)
    numNeighbours = (length $ Set.intersection (neighbours point) (occ))

generation :: Occupied -> Occupied
generation occ =
  Set.filter (isOccupied occ) $
    Set.unions $
      Set.insert occ $
        Set.map neighbours occ

part2 :: Int
part2 = Set.size $ repeatF 6 generation initState