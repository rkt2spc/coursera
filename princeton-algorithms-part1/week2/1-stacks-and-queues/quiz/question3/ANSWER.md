# Answer

Because Java arrays are covariant while generics are invariant

```java
Object[] a = new Integer[10]; // compile
```

```java
ArrayList<Object> a = new ArrayList<Integer>(); // won't compile
```

Java implements its Generics purely on the compiler level, and there is only one class file generated for each class. This is called Type Erasure.
At runtime, the compiled class needs to handle all of its uses with the same bytecode. So, new T[capacity] would have absolutely no idea what type needs to be instantiated.
