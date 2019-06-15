package sample

import javafx.event.ActionEvent

class MockQueryControl : QueryController() {


    fun ping(event: ActionEvent) {
        log.info("ping")

    }

    fun pong(event: ActionEvent) {
        log.info("pong")

    }


    private val log = getLogger(javaClass)
}