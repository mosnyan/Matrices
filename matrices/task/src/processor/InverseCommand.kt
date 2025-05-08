package processor

class InverseCommand : Command {
    override fun execute() {
        print("Enter size of matrix: ")
        val (rowA, colA) = readln().split(" ").map {it.toInt()}
        println("Enter matrix: ")
        val A = Matrix(MutableList(rowA) { Util.formatRowData(readln()) })

        println("The result is: ")
        println(A.inverse())
    }

    override fun name(): String {
        return "Inverse matrix"
    }
}