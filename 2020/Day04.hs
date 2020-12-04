module Main where

import Data.Char
import qualified Data.Map as M
import Data.Maybe (mapMaybe)
import Parser
import Utilities

type Input = [M.Map String String]

requiredFields :: [String]
requiredFields = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]

parse :: String -> Input
parse =
  map (M.fromList . mapMaybe (keyValue ':'))
    . splitOn (== "")
    . splitOn' (\x -> x == '\n' || x == ' ')

part1 :: Input -> Int
part1 = length . filter (valid)
  where
    valid passport = and $ map (flip M.member passport) requiredFields

-- Part 2 --

validateNat :: Int -> Int -> String -> Bool
validateNat low high year =
  case parsedNat of
    Nothing -> False
    Just yr -> yr >= low && yr <= high
  where
    parsedNat = safeHead $ runParser nat year

height :: Parser (Int, String)
height = (,) <$> nat <*> some letter

validateHeight :: String -> Bool
validateHeight h =
  case parsedHeight of
    Nothing -> False
    Just (x, "cm") -> x >= 150 && x <= 193
    Just (x, "in") -> x >= 59 && x <= 76
    Just (_, _) -> False
  where
    parsedHeight = safeHead $ runParser height h

validateHcl :: String -> Bool
validateHcl ('#' : hcl) = all isHexDigit hcl && length hcl == 6
validateHcl _ = False

validateEcl :: String -> Bool
validateEcl ecl = elem ecl ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]

validatePid :: String -> Bool
validatePid id = all isDigit id && length id == 9

validateField :: String -> (String -> Bool)
validateField "byr" = validateNat 1920 2002
validateField "iyr" = validateNat 2010 2020
validateField "eyr" = validateNat 2020 2030
validateField "hgt" = validateHeight
validateField "hcl" = validateHcl
validateField "ecl" = validateEcl
validateField "pid" = validatePid

validatePassport :: M.Map String String -> Bool
validatePassport passport =
  and $ map checkField requiredFields
  where
    checkField x = M.member x passport && (validateField x) ((M.!) passport x)

part2 :: Input -> Int
part2 = length . filter (validatePassport)

main :: IO ()
main = do
  s <- readFile "inputs/04.in"
  let input = parse s
  putStrLn "Part 1:"
  print (part1 input)
  putStrLn "Part 2:"
  print (part2 input)