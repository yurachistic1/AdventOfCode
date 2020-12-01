module Testing where

data Result = PASS | FAIL
  deriving (Show)

test :: Eq b => (a -> b) -> [(a, b)] -> [(Result, b, b)]
test f cases =
  [ (result, actualOutput, expectedOutput)
    | testPair <- cases,
      let input = fst testPair,
      let expectedOutput = snd testPair,
      let actualOutput = f input,
      let result = testResult expectedOutput actualOutput
  ]

testResult :: Eq a => a -> a -> Result
testResult x y
  | x == y = PASS
  | otherwise = FAIL

toString :: Show b => (Result, b, b) -> String
toString (res, actual, expected) =
  unwords [show res, show actual, show expected]