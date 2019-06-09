package sample

import db.DBReaderImpl
import javafx.scene.Parent
import javafx.fxml.FXMLLoader

import java.io.IOException


class StartupFactory {
    //    private String getView(UserID userID) {
    //        return
    //    }

    @Throws(IOException::class)
    fun getScene(userID: UserID): Parent {
        val mainFormId = "fxml/main_info.fxml"
        val mainFxmlLoader = FXMLLoader(javaClass.getResource(mainFormId))
        val root = mainFxmlLoader.load<Parent>()

        // Inject controller dependencies
        val dbReader = DBReaderImpl(userID)
        val infoCtr = mainFxmlLoader.getController<InfoControllerProxy>()
        infoCtr.ctr = InfoController(dbReader)
        // todo: rabbitmq


        //        FXMLLoader mainFxmlLoader = new FXMLLoader(getClass().getResource(getView(userId)));
        return root
    }

    companion object {
        private val viewMap = mapOf(
            UserID.RC to "fxml/uc2.fxml",
            UserID.SCI to "fxml/uc1.fxml"
        )
    }
}
