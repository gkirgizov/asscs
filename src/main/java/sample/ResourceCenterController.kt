package sample

import db.DBReader
import db.DBWriter
import javafx.fxml.FXML
import javafx.scene.input.MouseEvent


abstract class QueryController {

    fun initQuery(query: ResourceQuery) {
        this.rquery = query
    }

    protected lateinit var rquery: ResourceQuery
}


class ResourceCenterController : QueryController() {

    fun initialize(dbWriter: DBWriter) {
        this.dbWriter = dbWriter

        // todo: get last id to init ResourceBuilder
        this.rBuilder = ResourceQueryBuilder()
    }

    private lateinit var dbWriter: DBWriter
    private lateinit var rBuilder: ResourceQueryBuilder


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