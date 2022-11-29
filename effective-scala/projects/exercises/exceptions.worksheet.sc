import scala.io.Source
import scala.util.{Try,Using,Success,Failure}
import java.time.LocalDate
import java.time.Period

def parseDate(s: String): Try[LocalDate] =
  Try {
    LocalDate.parse(s)
  }

def tryPeriod(s1: String, s2: String): Try[Period] =
  parseDate(s1).flatMap { (date1: LocalDate) =>
    parseDate(s2).map { (date2: LocalDate) =>
      Period.between(date1, date2)
    }
  }

def tryPeriodV2(s1: String, s2: String): Try[Period] =
  for
    date1 <- parseDate(s1)
    date2 <- parseDate(s2)
  yield
    Period.between(date1, date2)

tryPeriod("2020-07-27", "2020-12-25")
tryPeriod("2020-19-27", "2020-12-25")
tryPeriod("2020-07-27", "2020-22-25")


def readDateStrings(filename: String): Try[Seq[String]] =
  Using(Source.fromFile(filename)) { source =>
    source.getLines.toSeq
  }

def parseDates(filename: String): Try[Seq[LocalDate]] =
  readDateStrings(filename).flatMap { (dateStrings: Seq[String]) =>
    dateStrings.foldLeft[Try[Seq[LocalDate]]](Success(Vector.empty)) {
      (tryDates, dateString) =>
        for
          dates <- tryDates
          date <- parseDate(dateString)
        yield
          dates :+ date
    }
  }

val fileName = "/Users/tuan.nguyen/Desktop/scala-lab/projects/exercises/dates.txt"
parseDates(fileName)
