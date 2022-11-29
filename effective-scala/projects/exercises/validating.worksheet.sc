import scala.util.{Try,Left,Right}
import java.time.LocalDate

type Errors = Seq[String]

type Validated[A] = Either[Errors, A]

def validateBoth[A, B](
  validatedA: Validated[A],
  validatedB: Validated[B],
): Validated[(A, B)] =
  (validatedA, validatedB) match
    case (Right(a), Right(b)) => Right((a, b))
    case (Left(e), Right(_)) => Left(e)
    case (Right(_), Left(e)) => Left(e)
    case (Left(e1), Left(e2)) => Left(e1 ++ e2)

def validateEach[A, B](as: Seq[A])(validate: A => Validated[B]): Validated[Seq[B]] =
  as.foldLeft[Validated[Seq[B]]](Right(Vector.empty)) {
    (validatedBs, a) =>
      val validatedB: Validated[B] = validate(a)
      validateBoth(validatedBs, validatedB)
        .map((bs, b) => bs :+ b)
  }

def parseDate(str: String): Validated[LocalDate] =
  Try(LocalDate.parse(str)).toEither
    .left.map(error => Seq(error.getMessage))

def parseDates(strs: Seq[String]): Validated[Seq[LocalDate]] =
  validateEach(strs)(parseDate)


parseDates(List(
  "2021",
  "2022-11-27",
  "2023-01-20",
  "2021-31-20",
))
