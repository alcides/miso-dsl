import scala.reflect._
import scala.collection.mutable.Stack
import java.util.concurrent.CyclicBarrier
import scala.concurrent._
import scala.concurrent.forkjoin._
import scala.concurrent.duration._


class WithAccessor { protected def cloneUnprotected = clone }
trait MisoState extends WithAccessor with Cloneable {
	def cloneO[T]():T = {
		this.cloneUnprotected.asInstanceOf[T];
	}
	def transition();
	
	
	type T
	var misoPos = 0
	var misoCell:Cell[_] = null
	def %(i:Int) = {
		misoCell.history(i)(misoPos).asInstanceOf[T]
	}
}

class Cell[Mem <: MisoState](val number:Int)(implicit m: ClassTag[Mem]) {
  
  
	var history:Stack[Array[Mem]] = Stack();
	var instances:Array[Mem] = new Array[Mem](number);
	var instances2:Array[Mem] = null;

	for (i <- 0 to number-1) {
		instances(i) = m.runtimeClass.newInstance.asInstanceOf[Mem];
		instances(i).misoPos = i
		instances(i).misoCell = this
	}

	def iterate {
		instances2 = instances.map(m => m.cloneO[Mem])
		instances2.foreach { m => m.transition };
	}

	def endIteration {
		history.push(instances2)
		instances = instances2;
	}

	def apply(i:Integer):Mem = {
		instances(i);
	}
  
  
}

object MisoRuntime {


	def runPar(it:Int, os:Cell[_]*) {

		implicit val ec = scala.concurrent.ExecutionContext.global
		val total = os.map(_.instances.length).reduceLeft(_+_);
		val barrier = new CyclicBarrier(total);
		
		for ( i <- 1 to it) {	
			var fs:Future[Seq[Unit]] = Future.sequence(os.map(a => Future { a.iterate }))
			Await.ready(fs, Duration.Inf)
			fs = Future.sequence(os.map(a => Future { a.endIteration }))
			Await.ready(fs, Duration.Inf)
		}
	}	
	
	def runSeq(it:Int, os:Cell[_]*) {
		for ( i <- 1 to it) {
			for (o <- os) {
				o.iterate
			}
			for (o <- os) {
				o.endIteration
			}

		}
	}
}