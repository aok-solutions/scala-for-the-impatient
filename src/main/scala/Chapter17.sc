import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

"-------- Question 1 --------"

for {
  m1 ← Future { Thread.sleep(1000); 2}
  m2 ← Future { Thread.sleep(1000); 40 }
} println(m1 + m2)

"-------- Question 2 --------"

def doInOrder[T, U, V](f: T ⇒ Future[U], g: U ⇒ Future[V]): T ⇒ Future[V] = {
  t ⇒
    for {
      first ← f(t)
      second ← g(first)
    } yield second
}

"-------- Question 3 --------"

def doSequence[T](fs: Seq[T ⇒ Future[T]]): T ⇒ Future[T] = {
  x ⇒ fs.tail.foldLeft(fs.head(x)) { (acc, t) ⇒
    acc.flatMap(t)
  }
}

"-------- Question 4 --------"
def doTogether[T,U,V](f: T ⇒ Future[U], g: U ⇒ Future[V]): T ⇒ Future[(U, V)] = {
//  t ⇒ f(t).zip(g(t))
  t ⇒
    for {
      f1 ← f(t)
      g1 ← g(t)
    } yield (f1, g1)
}

"-------- Question 5 --------"

def futureSequence[T](s: Seq[Future[T]]): Future[Seq[T]] = {
  Future.sequence(s)
}

"-------- Question 6 --------"

def repeat[T](action: ⇒ T, until: T ⇒ Boolean): Future[T] = {
  val futureAction = Future(action)
  for {
    result ← futureAction
    v ← Future(until(result))
    res ← if (v) futureAction else repeat(action, until)
  } yield res
}
