
fac n = if n == 0 then 1 else n * fac (n-1)
p = 12
main = putStrLn ("fac (" ++ show p ++ ") = " ++ show (fac p))

