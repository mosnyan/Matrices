package processor

interface Command {
    fun execute()
    fun name(): String
}