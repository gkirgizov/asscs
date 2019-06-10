package sample

import db.DBReader
import db.DBWriter
import javafx.fxml.FXML
import javafx.scene.input.MouseEvent


abstract class QueryController {

    fun initQuery(query: ResourceQuery) {
        this.query = query
    }

    protected lateinit var query: ResourceQuery
}


class ResourceCenterController : QueryController() {

    fun initialize(dbWriter: DBWriter) {
        this.dbWriter = dbWriter
    }

    private lateinit var dbWriter: DBWriter


    fun clicked(mouseEvent: MouseEvent) {

    }

    @FXML lateinit var blah: Unit
}

class SciController : QueryController() {

    fun initialize(dbReader: DBReader) {
        this.dbReader = dbReader
    }

    private lateinit var dbReader : DBReader

}