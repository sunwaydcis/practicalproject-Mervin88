package ch.makery.address

import ch.makery.address.model.Person
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import javafx.scene as jfxs
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer

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