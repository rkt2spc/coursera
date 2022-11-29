import scala.concurrent.{ Future, Promise }
import scala.concurrent.ExecutionContext.Implicits._
import scala.util.Random
import scala.util.control.NonFatal

def getPagesCount(): Future[Int] =
  Future(42)

def getPage(page: Int): Future[String] =
  if Random.nextDouble() > 0.95 then
    Future.failed(Exception(s"Timeout when fetching the page"))
  else
    Future(s"Page $page")

def resilientGetPage(page: Int): Future[String] =
  val maxAttempts = 3
  def attempt(remainingAttempts: Int): Future[String] =
    if (remainingAttempts == 0)
      Future.failed(Exception(s"Failed after $maxAttempts attempts"))
    else
      println(s"Trying to fetch page $page ($remainingAttempts remaining attempts)")
      getPage(page).recoverWith {
        case NonFatal(_) =>
          System.err.println(s"Fetching page $page failed...")
          attempt(remainingAttempts - 1)
      }
  attempt(maxAttempts)

def getAllPages(): Future[Seq[String]] =
  getPagesCount().flatMap { pagesCount =>
    Future.traverse(1 to pagesCount)(getPage)
  }

def resilientGetAllPages(): Future[Seq[String]] =
  getPagesCount().flatMap { pagesCount =>
    Future.traverse(1 to pagesCount)(resilientGetPage)
  }

getAllPages()
resilientGetAllPages()
