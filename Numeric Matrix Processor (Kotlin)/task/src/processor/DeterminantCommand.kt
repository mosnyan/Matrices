package processor

class DeterminantCommand : Command {
    override fun execute() {
        print("Enter size of matrix: ")
        val (rowA, colA) = readln().split(" ").map {it.toInt()}
        println("Enter matrix: ")
        val A = Matrix(MutableList(rowA) { Util.formatRowData(readln()) })

        println("The result is: ")
        println(A.determinant())
    }

    override fun name(): String {
        return "Calculate a determinant"
    }
}