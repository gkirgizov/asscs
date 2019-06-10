package sample

import db.DBReader
import javafx.fxml.FXML
import javafx.scene.Parent
import javafx.scene.control.ListView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.scene.Node
import javafx.scene.control.ContextMenu
import javafx.scene.input.ContextMenuEvent
import javafx.stage.Modality




class InfoControllerProxy {
    fun onClicked(event: MouseEvent) {
        if (event.isPrimaryButtonDown) {
            // todo:
            //  populate information pane
            ctr.displayFullQueryInfo(event)
        }
    }

    fun onQueryHistoryCMenuRequested(event: ContextMenuEvent) {
        queryControlCMenu.show(queryHistory, event.screenX, event.screenY)
    }

    fun onQueryControlBtnClicked(event: MouseEvent) {
        val selectedQuery: ResourceQuery = queryHistory.selectionModel.selectedItem
        // Initialize QueryController
        ctr.queryCtr.initQuery(selectedQuery)

        // Init and show a new window for query control
        val stage = Stage()
        stage.initModality(Modality.WINDOW_MODAL) // for making previous windows inactive
        val parentWindow = (event.source as Node).scene.window
        stage.initOwner(parentWindow)
        stage.title = "Query Status & Control"
        stage.scene = Scene(ctr.queryView) // fixme: window sizes?
        stage.show()
    }

    lateinit var ctr: InfoController

    // by default it just converts contents to String
    @FXML lateinit var queryHistory: ListView<ResourceQuery>
    @FXML lateinit var queryControlCMenu: ContextMenu

    // todo: ?use tableView ?construct it here
    @FXML lateinit var queryFullInfo: GridPane

}


class InfoController(
        private var dbReader: DBReader,
        var queryCtr: QueryController,
        var queryView: Parent

) {

    fun displayFullQueryInfo(event: MouseEvent): Unit {

        TODO()
    }

}