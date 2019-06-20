package sample

//import java.sql.Date
import java.util.Date
import java.time.Instant
import kotlin.random.Random
import java.text.SimpleDateFormat
import java.text.DateFormat




data class ResourceQuery(
        val specs: List<ResourceSpec>,
        val metaInfo: MetaInfo,
        val id: Int,
        val deadline: Date = Constants.NODEADLINE,
        val priority: Priority = Priority.Normal,
        val state: State = State.Created
) {

    data class MetaInfo(val title: String, val description: String = "")

    enum class Priority {
        Normal,
        High
    }

    enum class State {
        Created, // just created
        ApprovalRequested, // Sent to Resource Center
        CorrectionRequested, // Sent back to scientists from resource center for correction
        ShipsRequested,
        ScheduleRequested,
        Approved,
        Declined
    }

    companion object Constants {
        val NODEADLINE: Date = java.sql.Date.from(Instant.EPOCH)
    }

    fun isWithDeadline(): Boolean = deadline != NODEADLINE

    fun toShortString(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val deadlineStr = if (isWithDeadline()) formatter.format(deadline) else "No deadline"
        return "${metaInfo.title}\n$state\t$priority\t$deadlineStr"
    }
}

class ResourceQueryBuilder(private var nextId: Int = 1) { // must be init-d from db info, presumably
    fun fromSpecs(title: String, vararg specs: ResourceSpec) = ResourceQuery(
            arrayListOf(* specs),
            ResourceQuery.MetaInfo(title),
            incrId()
    )

    fun randomQuery(): ResourceQuery {
        val rtype = rtypes[r.nextInt(rtypes.size)]
        val quant = r.nextInt(0, 10)
        val title = "Mock query $nextId of $rtype, quantity $quant"

        return ResourceQuery(
                arrayListOf(ResourceSpec(rtype, quant)),
                ResourceQuery.MetaInfo(title, "mock random query"),
                incrId()
        )
    }

    private fun incrId() = nextId++

    private val r = Random.Default
    private val rtypes = ResourceSpec.RType.values()
}


data class ResourceSpec(val type: RType, val quantity: Int) {
    enum class RType {
        HydrogenAsteroid,
        IronAsteroid,
        SpecialEquipment
    }
}

