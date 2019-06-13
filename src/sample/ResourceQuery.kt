package sample

import java.sql.Date
import java.sql.ResultSet;

data class ResourceQuery(val id: Int = 0, val pr: Priority = Priority.Normal, val hydrogen: Int = 0, val iron: Int = 0,
                         val special: String = "", val preferred: Date, val dl: Date,
                         val state: State = State.Created, val update: Date) {


    // Ну это пока примерно...
    enum class State {
        Created, // just created
        ApprovalRequested, // Sent to Resource Center
        CorrectionRequested, // Sent back to scientists from resource center for correction
        ShipsRequested,
        ScheduleRequested,
        Approved,
        Declined
    }

    enum class Priority {
        Normal,
        High
    }

    var priority : Priority = pr
        get() = Priority.valueOf(priority.toString())

    var hydrogenCount: Int = hydrogen

    var ironCount: Int = 0

    var specialSupplies: String = special

    var preferredDate: Date = preferred

    var deadline: Date = dl

    var status : State = state

    var updated: Date = update

    //Make a query out of ResultSet from DB
    constructor(rs: ResultSet): this(rs.getInt("id"), Priority.valueOf(rs.getString("Priority")),
            rs.getInt("Hydrogen count"), rs.getInt("Iron count"), rs.getString("Special supplies"),
            rs.getDate("Preferred deadline"), rs.getDate("Last deadline"), State.valueOf(rs.getString("Status")),
            rs.getDate("Created")){  }

    //var
}