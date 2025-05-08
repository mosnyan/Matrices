package processor
import kotlin.system.exitProcess

class ExitCommand : Command {
    override fun execute() {
        exitProcess(0)
    }

    override fun name(): String {
        return "Exit"
    }
}