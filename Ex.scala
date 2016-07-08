
class CellMemory extends CellMem[CellMemory] {
	var a:Int = 0;
	var b:Float = 0;
	
	def transition(m:CellMemory) {
		m.a = m.a + 1;
	}
}

object Main {
	def main(args:Array[String]) {
		
		val objects = new Cell[CellMemory](20, (m) => {
			m.a = m.a + 1;
		})

		MisoRuntime.run(10,objects)

		println(objects(1).a)
	}
}

