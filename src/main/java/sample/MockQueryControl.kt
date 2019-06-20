package sample

import javafx.event.ActionEvent
import sample.mock.MockDBReader

class MockQueryControl : QueryController() {


    fun ping(event: ActionEvent) {
        log.info("ping")

    }

    fun pong(event: ActionEvent) {
        log.info("pong")

    }

    fun initialize(dbReader: MockDBReader) {
        this.dbReader = dbReader
    }

    private lateinit var dbReader : MockDBReader


    private val log = getLogger(javaClass)
}