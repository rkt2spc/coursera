# Asymptotic Analysis

Big-O, Omega (Ω), Theta (Θ), Little-o

## Formally

`T(n) is O(f(n))` if for some constants c and n0, `T(n) <= c.f(n) for all n >= n0`

`T(n) is Ω(f(n))` if for some constants c and n0, `T(n) >= c.f(n) for all n >= n0`

`T(n) is Θ(f(n))` if `T(n) is O(f(n))` AND `T(n) is Ω(f(n))`

`T(n) is o(f(n))` if `T(n) is O(f(n))` AND `T(n) is NOT Θ(f(n))`

## Informally

`T(n) is O(f(n))` basically means that `f(n)` describes the upper bound for `T(n)`

`T(n) is Ω(f(n))` basically means that `f(n)` describes the lower bound for `T(n)`

`T(n) is Θ(f(n))` basically means that `f(n)` describes the exact bound for `T(n)`

`T(n) is o(f(n))` basically means that `f(n)` is the upper bound for `T(n)` but that `T(n)` can never be equal to `f(n)`

## Another way of saying

`T(n) is O(f(n))` growth rate of `T(n)` <= growth rate of `f(n)`

`T(n) is Ω(f(n))` growth rate of `T(n)` >= growth rate of `f(n)`

`T(n) is Θ(f(n))` growth rate of `T(n)` == growth rate of `f(n)`

`T(n) is o(f(n))` growth rate of `T(n)` < growth rate of `f(n)`
