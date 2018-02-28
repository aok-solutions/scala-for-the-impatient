import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

"-------- Question 2 --------"

def doInOrder[T, U, V](f: T => Future[U], g: U => Future[V]): T => Future[V] = (t: T) => {
  for {
    first ← f(t)
    second ← g(first)
  } yield second
}

//def doSequence[T, U, V](f: Seq[T => Future[T]], g: U => Future[V]): T => Future[U] = (t: T) => {
//
//}

"-------- Question 4 --------"
def doTogether[T,U,V](f: T => Future[U], g: U => Future[V]): T => Future[(U, V)] = (t: T) => {
  f(t).zip(g(t))
}

"-------- Question 5 --------"

def futureSequence[T](s: Seq[Future[T]]): Future[Seq[T]] = {
  Future.sequence(s)
}
