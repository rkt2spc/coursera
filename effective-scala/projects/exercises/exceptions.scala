import scala.util.Try
import scala.util.control.NonFatal
import scala.util.Success
import scala.util.Failure

def compute(): Unit =
  println("Computing...")
  throw RuntimeException("Booom")

def trySomething(): Try[Unit] =
  Try {
    println("Tryin'...")
    throw RuntimeException("Failed trying :'(")
  }

def main(): Unit =
  try
    compute()
  catch
    case NonFatal(throwable) =>
      println(throwable)
      println("Catched!!!")
  finally
    println("Finally bruv")

val handler: PartialFunction[Throwable, Unit] =
  case ex: RuntimeException => println(s"Encountered runtime exception $ex")

@main def main2(): Unit =
  trySomething() match
    case Success(value) => println("Happy")
    case Failure(exception) => println("Sad")

  trySomething()
    .recover(handler)
