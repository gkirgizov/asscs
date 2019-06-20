package sample

import db.DBReader
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Parent
import javafx.scene.input.MouseEvent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.input.ContextMenuEvent
import javafx.stage.Modality
import javafx.util.Callback




class InfoControllerProxy {

    fun onClicked(event: MouseEvent) {
        selectedQuery?.let { displayQueryInfo(it) }
//        if (event.isPrimaryButtonDown) {
//        }
    }

    fun onQueryHistoryCMenuRequested(event: ContextMenuEvent) {
        queryControlCMenu.show(queryHistory, event.screenX, event.screenY)
    }

    fun onQueryControlBtnClicked(event: ActionEvent) {
        // Initialize QueryController
        selectedQuery?.let { ctr.queryCtr.initQuery(it) }

        // Init and show a new window for query control
        val stage = Stage()
        stage.initModality(Modality.WINDOW_MODAL) // for making previous windows inactive
        val parentWindow = (event.source as Node).scene.window
        stage.initOwner(parentWindow)
        stage.title = "Query Status & Control"
        stage.scene = Scene(ctr.queryView) // fixme: window sizes?
        stage.show()
    }

    fun onRefresh(event: ActionEvent) = ctr.refreshQueries()


    fun initQueryHistory() {
        // just construct in-place anonymous CellFactory
        queryHistory.cellFactory = Callback {
            object : ListCell<ResourceQuery>() {
                override fun updateItem(item: ResourceQuery?, empty: Boolean) {
                    super.updateItem(item, empty)

                    text = item?.toShortString() ?: ""
                }
            }
        }
//        ctr.refreshQueries() // done in startup factory
        queryHistory.items = ctr.knownQueries()
    }

    private val selectedQuery: ResourceQuery?
        get() = queryHistory.selectionModel.selectedItem


    lateinit var ctr: InfoController

    // by default it just converts contents to String
    // todo: ?use tableView ?construct it here. IT WILL ALLOW SORTING!
    @FXML lateinit var queryHistory: ListView<ResourceQuery>
    @FXML lateinit var queryControlCMenu: ContextMenu


    private fun displayQueryInfo(rq: ResourceQuery): Unit {
        queryInfoTitle.text = rq.metaInfo.title
        queryInfoDescription.text = rq.metaInfo.description
        queryInfoPriority.text = rq.priority.toString()
        queryInfoDeadline.text = if (rq.isWithDeadline()) rq.deadline.toString() else "no deadline"

//        queryInfoResources.items =
    }

    //    @FXML lateinit var queryFullInfo: GridPane
    @FXML lateinit var queryInfoTitle: Label
    @FXML lateinit var queryInfoDescription: TextArea
    @FXML lateinit var queryInfoPriority: Label
    @FXML lateinit var queryInfoDeadline: Label
//    @FXML lateinit var queryInfoDeadline: DatePicker
    // todo: see how to populate tableView from such data
    // todo: provide row cellfactory??
    @FXML lateinit var queryInfoResources: TableView<ResourceSpec>

}


class InfoController(
        private var dbReader: DBReader,
        var queryCtr: QueryController,
        var queryView: Parent

) {

    private var knownQueries: ObservableList<ResourceQuery> = FXCollections.observableArrayList()
    private var queryIndexById: HashMap<Int, Int> = HashMap()

    fun knownQueries(): ObservableList<ResourceQuery> = knownQueries
    fun refreshQueries() {
        // there can be some smart new-only update logic? (i.e. not reading all queries each time)
        knownQueries.setAll(dbReader.getQueries())
        knownQueries.forEachIndexed { index, resourceQuery -> queryIndexById[resourceQuery.id] = index }
    }


    // todo: for RMQ
    fun onQueryUpdate(rq: ResourceQuery) {
        queryIndexById[rq.id]?.let { knownQueries.set(it, rq) }
    }

}