package effective.example

import fansi.Color

val greeting = "Hello"

object Main {
  def main(name: String, age: Int): Unit =
    println(s"$greeting ${Color.Yellow(name)}. You are ${Color.Red(age.toString)} years old!")
}
