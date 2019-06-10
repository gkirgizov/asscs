package sample

import db.DBReaderImpl
import db.DBWriterImpl
import javafx.scene.Parent
import javafx.fxml.FXMLLoader

import java.io.IOException


class StartupFactory {

    @Throws(IOException::class)
    fun getScene(userID: UserID): Parent {
        val mainFormId = "resources/mainInfoForm.fxml"
        val mainFormResource = javaClass.getResource(mainFormId)
//        println("Working Directory = ${System.getProperty("user.dir")}")
//        println("file $mainFormId exists? ${File(mainFormId).exists()}")
        println("resource for $mainFormId: $mainFormResource")
        assert(mainFormResource != null)

        val mainFxmlLoader = FXMLLoader(mainFormResource)
        val root = mainFxmlLoader.load<Parent>()

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
        // Provide QueryController and its view to the main controller

        // Inject controller dependencies
        val infoCtr = mainFxmlLoader.getController<InfoControllerProxy>()
        infoCtr.ctr = InfoController(dbReader, queryCtr, queryCtrParent)
        // todo: rabbitmq

        return root
    }

    companion object {
//        fun getControlCtr(userID: UserID):


        val viewMap = mapOf(
            UserID.RC to "resources/uc2.fxml",
            UserID.SCI to "resources/uc1.fxml"
        )
    }
}
