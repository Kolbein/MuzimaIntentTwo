package example.kfold.muzimaintenttwo

data class Encounter(
    var status : String? = null,
    var priority : Number? = null,
    var name : String? = null
) {
    override fun toString(): String {
        return "Encounter(status=$status, priority=$priority, name=$name)"
    }
}