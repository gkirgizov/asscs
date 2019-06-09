package sample

import db.DBReader
import javafx.fxml.FXML
import javafx.scene.control.ListView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane

class InfoControllerProxy {
    fun onClicked(event: MouseEvent) {
        if (event.isPrimaryButtonDown) {
            // todo:
            //  get list view
            //  compute where mouse points at in list vew
            //  get ResourceQuery from it
            //  populate information pane
            ctr.displayFullQueryInfo(event)
//            this.queryHistory.
        } else if (event.isSecondaryButtonDown) {

        }

    }

    lateinit var ctr: InfoController

    // todo:
    //  how it displays contents?
    //  how to determine where mouse click falls?
    @FXML lateinit var queryHistory: ListView<ResourceQuery>
    // todo: ?use tableView ?construct it here
    @FXML lateinit var queryFullInfo: GridPane

}

//todo:
// add context menu
// create scene/xxx beforehand
// attach controller to it?

class InfoController(private var dbReader: DBReader) {

    fun displayFullQueryInfo(event: MouseEvent): Unit {

        TODO()
    }

}