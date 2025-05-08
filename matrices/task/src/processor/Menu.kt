package processor

object Menu {
    val options: List<Command> = listOf(ExitCommand(),
        AddCommand(),
        ScMulCommand(),
        MatMulCommand(),
        TransposeCommand(),
        DeterminantCommand(),
        InverseCommand())

    fun displayMenu() {
        for (i in options.indices) {
            println("$i. ${options[i].name()}")
        }
        print("Your choice: ")
        options[readln().toInt()].execute()
    }
}