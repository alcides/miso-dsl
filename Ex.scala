
class CellMemory extends CellMem {
	var a:Int = 0;
	var b:Float = 0;
	
	def transition {
		a = a + 1;
	}
}

object Main {
	def main(args:Array[String]) {
		
		val objects = new Cell[CellMemory](20)
		val others = new Cell[CellMemory](20)

		MisoRuntime.runSeq(10,objects, others)

		println(others(1).a)
	}
}

