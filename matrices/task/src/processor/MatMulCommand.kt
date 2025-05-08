package processor

class MatMulCommand : Command {
    override fun execute() {
        print("Enter size of first matrix: ")
        val (rowA, colA) = readln().split(" ").map {it.toInt()}
        println("Enter first matrix: ")
        val A = Matrix(MutableList(rowA) { Util.formatRowData(readln()) })
        print("Enter size of second matrix: ")
        val (rowB, colB) = readln().split(" ").map {it.toInt()}
        println("Enter second matrix: ")
        val B = Matrix(MutableList(rowB) { Util.formatRowData(readln()) })

        try {
            val C = A * B
            println("The result is: ")
            println(C)
        } catch (e: IllegalArgumentException) {
            println("The operation cannot be performed.")
        }
    }

    override fun name(): String {
        return "Multiply matrices"
    }
}