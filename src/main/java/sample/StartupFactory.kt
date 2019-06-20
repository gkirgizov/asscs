package sample

import db.DBReaderImpl
import db.DBWriterImpl
import javafx.scene.Parent
import javafx.fxml.FXMLLoader
import sample.mock.MockDBReader

import java.io.IOException


class StartupFactory {

    @Throws(IOException::class)
    fun getScene(userID: UserID): Parent {
        val mainFormId = "resources/mainInfoForm.fxml"
        val mainFormResource = javaClass.getResource(mainFormId)
        println("resource for $mainFormId: $mainFormResource")
        assert(mainFormResource != null)

        val mainFxmlLoader = FXMLLoader(mainFormResource)
        val root = mainFxmlLoader.load<Parent>()

        // Inject controller dependencies
        val infoCtrProxy = mainFxmlLoader.getController<InfoControllerProxy>()
        val infoCtr: InfoController = if (userID != UserID.TEST) getInfoController(userID) else getTestInfoController()
        infoCtr.refreshQueries()
        infoCtrProxy.ctr = infoCtr
        infoCtrProxy.initQueryHistory()

        // todo: rabbitmq

        return root
    }

    private fun getTestInfoController(): InfoController {
        val queryCtrLoader = FXMLLoader(javaClass.getResource(viewMap[UserID.TEST]))
        val queryCtrParent = queryCtrLoader.load<Parent>()
        val queryCtr = queryCtrLoader.getController<QueryController>() as MockQueryControl

        // generate some test queries
        val queryBuilder = ResourceQueryBuilder()
        val testQueries = mutableListOf<ResourceQuery>().apply { repeat(10) {
            add(queryBuilder.randomQuery())
        }}
        val mockDbReader = MockDBReader(testQueries)

        queryCtr.initialize(mockDbReader)

        return InfoController(mockDbReader, queryCtr, queryCtrParent)
    }

    private fun getInfoController(userID: UserID): InfoController {
        // Provide QueryController and its view to the main controller

        val queryCtrLoader = FXMLLoader(javaClass.getResource(viewMap[userID]))
        val queryCtrParent = queryCtrLoader.load<Parent>()
        val queryCtr = queryCtrLoader.getController<QueryController>()

        // Some shared injected dependencies
        val dbReader = DBReaderImpl(userID)

        when (queryCtr) {
            is ResourceCenterController -> {
                assert(userID == UserID.RC)

                val dbWriter = DBWriterImpl(userID)
                queryCtr.initialize(dbWriter)
            }
            is SciController -> {
                assert(userID == UserID.SCI)

                queryCtr.initialize(dbReader)
            }
            else -> throw IllegalStateException("Mismatch between user and controller!")
        }

        return InfoController(dbReader, queryCtr, queryCtrParent)
    }

    companion object {
//        fun getControlCtr(userID: UserID):

        val viewMap = mapOf(
            UserID.RC to "resources/uc2.fxml",
            UserID.SCI to "resources/uc1.fxml",
            UserID.TEST to "resources/MockQueryControl.fxml"
        )
    }
}
