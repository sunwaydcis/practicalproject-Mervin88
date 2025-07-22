package ch.makery.address

import ch.makery.address.model.Person
import ch.makery.address.view.{PersonEditDialogController, PersonOverviewController}
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.{Scene, control}
import scalafx.Includes.*
import javafx.scene as jfxs
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}

object MainApp extends JFXApp3:

  //Window Root Pane
  //none object
  var roots: Option[scalafx.scene.layout.BorderPane] = None //option value to store the root panel

  var cssResource = getClass.getResource("view/DarkTheme.css")
  var personOverviewController : Option[PersonOverviewController] = None //it will be initialize showpersonOverview is call at least 1 times

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
      icons += new Image(getClass.getResource("/images/book.png").toExternalForm) //appear at the primary stage, not in the scene
      scene = new Scene(): //code block
        root = roots.get //point to the container, thn display together
        stylesheets = Seq(cssResource.toExternalForm) //to set the stylesheet to be used under this empty dialog

    // call to display PersonOverview when app start
    showPersonOverview()
  // actions for display person overview window
  def showPersonOverview(): Unit =
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane] //load anchor pane
    val ctrl = loader.getController[PersonOverviewController] //used to bridge
    personOverviewController = Option(ctrl) //used to bridge
    this.roots.get.center = roots //set to the center

  //String Property have publisher (data) & subscriber (require data)
  val stringA = new StringProperty("Hello") //publisher
  val stringB = new StringProperty("Sunway") //subscriber
  val stringC = new StringProperty("University") //subscriber

  //binding
  stringA.value = "world"
  stringB <== stringA //value of string A will push to string B
  stringC <== stringA
  //stringB.value = "google" //all will change to this

  //println(stringA.value)

  stringA.onChange { (_, oldvalue, newvalue) =>
    println(s"stringA change from $oldvalue to $newvalue"
    )
  }

  stringA.onChange { (_, _, _) =>
    println(s"stringA has change !!!!!!!!"
    )
  }

  stringA.value = "Google"

  //Function Object, Function has type
  val func1 = (a:Int) =>  // val func1: Int => Int = (a) => //input is int, output also int
    a + 1
  //println(func1(8))


  /**
   * The data as an observable list of Persons.
   */
  val personData = new ObservableBuffer[Person]() //buffer is mutable, everytime add smtg the buffer will change

  /**
   * Constructor
   */
  personData += new Person("Hans", "Muster") //+= add 1 value to the collection; ++= add collection to collection
  personData += new Person("Ruth", "Mueller")
  personData += new Person("Heinz", "Kurz")
  personData += new Person("Cornelia", "Meier")
  personData += new Person("Werner", "Meyer")
  personData += new Person("Lydia", "Kunz")
  personData += new Person("Anna", "Best")
  personData += new Person("Stefan", "Meier")
  personData += new Person("Martin", "Mueller")

  extension (value: Int)
    def area: Double = 3.142 * value * value 
  
  println(34.area)

  def showPersonEditDialog(person: Person): Boolean =
    val resource = getClass.getResource("view/PersonEditDialog.fxml") //load the PersonEditDialog.fxml
    val loader = new FXMLLoader(resource)
    loader.load();
    val roots2 = loader.getRoot[jfxs.Parent] //get the anchor pane; Parent is the abstract class (all anchor, text, label); all the object inside
    val control = loader.getController[PersonEditDialogController] //get the reference of the controller and pass in the controller type

    val dialog = new Stage(): //create own new window
      initModality(Modality.ApplicationModal) //set the behavior of the window; modality - if user hide the window it will hide behind or stay on top
      initOwner(stage) //second window, need to set if this window is close set back to stage window
      scene = new Scene: //create new scene
        root = roots2 //assign root2 to display
        stylesheets = Seq(cssResource.toExternalForm) //to set the stylesheet to be used under this empty dialog

    control.dialogStage = dialog //at first dialogstage is set to now, now assign it
    control.person = person //call the setter to set to the person parameter
    dialog.showAndWait() //window pop out and wait to be close
    control.okClicked //return boolean
