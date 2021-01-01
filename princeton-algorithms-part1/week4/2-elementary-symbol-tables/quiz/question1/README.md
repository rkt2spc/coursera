# Java autoboxing and equals()

Consider two **double** values *a* and *b* and their corresponding **Double** values *x* and *y*.

1. Find values such that `a == b` is **true** but `x.equals(y)` is **false**.
2. Find values such that `a == b` is **false** but `x.equals(y)` is **true**.

## Answers

1. `0.0f` and `-0.0f`
2. `NaN` and `NaN`
