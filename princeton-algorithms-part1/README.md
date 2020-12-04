# Princeton Algorithms, Part 1

## Setup

Source the file [.envrc](./.envrc).

This will download the necessary libraries to the `.lift/` directory under the current project path.

In addition, it will augment your Java classpath so `java` & `javac` know where to look for the libraries (Note: it ONLY augment the current shell session, source the file again for new sessions).

Use [direnv](https://direnv.net) if you want to source the file automatically every time you *cd* into this directory.

Check Princeton's [guide](https://algs4.cs.princeton.edu/code/#classpath) for additional setup methods.

## Run the code

### Launch Single-File Source-Code Programs

The source code is compiled in memory and then executed by the interpreter, without producing a .class file on disk. The first class declared in the source file will be used as the main class.

See [JEP 330](https://openjdk.java.net/jeps/330) for more details

```sh
# Just run it!
java MyProgram.java
# To enable assertion
java -ea MyProgram.java
```

### Standard method

Compile the code

```sh
# Compile selected files
javac MyProgram.java Helper.java
# Or just compile everything
javac *.java
# Enable all recommended warnings
javac -Xlint:all *.java
```

The .class files will be generated after compilation. Select a main class to run.

```sh
java MyProgram
```
