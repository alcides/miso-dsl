
class CellMemory extends CellMem {
	var a:Int = 0;
	var b:Float = 0;
	
	def transition {
		a = Main.objects(0).a + Main.others(1).a + 1;
	}
}

object Main {
	
	val objects = new Cell[CellMemory](20)
	val others = new Cell[CellMemory](20)
	
	def main(args:Array[String]) {

		//MisoRuntime.runSeq(10, objects, others)

		MisoRuntime.runPar(10, objects, others)

		println(others(1).a)
	}
}

