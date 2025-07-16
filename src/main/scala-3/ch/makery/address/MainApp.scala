package ch.makery.address

import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import javafx.scene as jfxs

object MainApp extends JFXApp3:

  //Window Root Pane
  //none object
  var roots: Option[scalafx.scene.layout.BorderPane] = None //option value to store the root panel 

  override def start(): Unit =
    // transform path of RootLayout.fxml to URI for resource location.
    val rootResource = getClass.getResource("view/RootLayout.fxml") //load the resourse
    // initialize the loader object.
    val loader = new FXMLLoader(rootResource) //read the FXML file into a object, FXMLLoader pass in the fxml file, create the loader, thn start to pass the fxml and create all the object based on the value in the fxml
    // Load root layout from fxml file.
    loader.load()

    // retrieve the root component BorderPane from the FXML
    //some object
    roots = Option(loader.getRoot[jfxs.layout.BorderPane]) //initialize the roots of the borderpane; jfxs is the type of borderpane; option is decide it have value or no value, like a container, if empty thn none

    stage = new PrimaryStage():
      title = "AddressApp"
      scene = new Scene(): //code block
        root = roots.get //point to the container, thn display together

    // call to display PersonOverview when app start
    showPersonOverview()
  // actions for display person overview window
  def showPersonOverview(): Unit =
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane] //load anchor pane
    this.roots.get.center = roots //set to the center

  var personA = 
