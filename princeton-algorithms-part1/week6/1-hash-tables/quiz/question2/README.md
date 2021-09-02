# Hashing with wrong hashCode() or equals()

Suppose that you implement a data type OlympicAthlete for use in a java.util.HashMap.

1. Describe what happens if you override hashCode() but not equals().
2. Describe what happens if you override equals() but not hashCode().
3. Describe what happens if you override hashCode() but implement `public boolean equals(OlympicAthlete that)` instead of `public boolean equals(Object that)`.

## Answer

1. If you only override hashCode then multiple "equals" then objects with the same hashCode will be treated as collisions, instead of being replaced in the HashMap they will be appended to the bucket chain or linear probed.
2. If only equals is overriden, objects that are structurally same will have different hashCode (the default hashCode() from the Object class will be called), when put into the HashMap they will be added on different buckets, making the HashMap space consumption explode.
3. The the `equals` method is inherited from the Object class and it expects another Object as argument, HashMap implementations depends on this method, so changing the method signature like this is similar to not overriding equals.
