package ch.makery.address.view

import ch.makery.address.MainApp
import javafx.event.ActionEvent
import javafx.fxml.FXML

@FXML
class RootLayoutController():

  @FXML
  def handleclose(action: ActionEvent): Unit = //ActionEvent must import javafx; binding Menu Bar - Menu file - go to ; controllerclass
    MainApp.stage.close()

  @FXML
  def handledelete(action: ActionEvent): Unit = //a function when user click the delete
    MainApp.personOverviewController.map(x => x.handleDeletePerson(action))//map the function to the optional value; 1 controller use the other controller throught the main app project

//Alt for short cut
//Text infront add _. click Mnemonic Parsing
//Accelerator