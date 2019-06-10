package sample


data class ResourceQuery(val id: Int, val state: State = State.Created) {


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

}