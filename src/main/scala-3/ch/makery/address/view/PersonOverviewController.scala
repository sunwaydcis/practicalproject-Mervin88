package ch.makery.address.view
import ch.makery.address.model.Person
import ch.makery.address.MainApp
import javafx.fxml.FXML
import javafx.scene.control.{Label, TableColumn, TableView}
import scalafx.Includes.*
import javafx.scene.control.TextField
import ch.makery.address.util.DateUtil.*
import javafx.event.ActionEvent
import scalafx.beans.binding.Bindings
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType //all the string & local date inside this source code will have extension

@FXML
class PersonOverviewController():
  @FXML
  private var personTable: TableView[Person] = null //in FXML file
  @FXML
  private var firstNameColumn: TableColumn[Person, String] = null
  @FXML
  private var lastNameColumn: TableColumn[Person, String] = null
  @FXML
  private var firstNameLabel: Label = null
  @FXML
  private var lastNameLabel: Label = null
  @FXML
  private var streetLabel: Label = null
  @FXML
  private var postalCodeLabel: Label = null
  @FXML
  private var cityLabel: Label = null
  @FXML
  private var birthdayLabel: Label = null
  @FXML
  private var mytext: TextField = null

  // initialize Table View display contents model
  def initialize() =
    personTable.items = MainApp.personData
    // initialize columns's cell values
    firstNameColumn.cellValueFactory = {x => x.value.firstName} //assign what value will be inside the column
    lastNameColumn.cellValueFactory  = {_.value.lastName} //S is person, T is string

    lastNameLabel.text <== mytext.text
    showPersonDetails(None)

    //bind the property, everytime it change it call the property
    personTable.selectionModel().selectedItem.onChange( //use option - new value can be mull
      (_, _, newValue) => showPersonDetails(Option(newValue)) //selection - to check which item user selected thn the selected item will change
    )


  private def showPersonDetails(person: Option[Person]): Unit = //option value have 2 types (some & none)
    person match
      case Some(person) =>
        // Fill the labels with info from the person object.
        firstNameLabel.text <== person.firstName
        lastNameLabel.text <== person.lastName
        streetLabel.text <== person.street
        cityLabel.text <== person.city;
        //postalCodeLabel.text = person.postalCode.value.toString //object property; = just to test column
        postalCodeLabel.text <== person.postalCode.delegate.asString() //binding; object convert to into a string binding then binding it into a string property 
        //birthday
        //birthdayLabel.text   = person.date.value.asString  //person.date is object property, .value will return local date & local data will have a as string method
        //bind local date, need to create the binding method first
        birthdayLabel.text <== Bindings.createStringBinding(() => {person.date.value.asString}, person.date)


      case None =>
        // Person is null, remove all the text.
        //need to unbind first because subscriber cannot change value, then only can set the text to empty
        firstNameLabel.text.unbind()
        firstNameLabel.text = ""

        lastNameLabel.text.unbind()
        lastNameLabel.text = ""

        streetLabel.text.unbind()
        streetLabel.text = ""

        postalCodeLabel.text.unbind()
        postalCodeLabel.text = ""

        cityLabel.text.unbind()
        cityLabel.text = ""

        birthdayLabel.text.unbind()
        birthdayLabel.text = ""

  @FXML //this method is bind to a FXML file
  def handleDeletePerson(action: ActionEvent): Unit = //actionevent - refer to button click
    val selectedIndex = personTable.selectionModel().selectedIndex.value //selected index - which row
    if (selectedIndex >= 0) then //selectedIndex - integer, if is >= 0 only will remove
      personTable.items().remove(selectedIndex); //personTable - is a table view object
      //MainApp.personData.remove(selectedIndex)
    else
      // Nothing selected. customize dialog
      val alert = new Alert(AlertType.Warning): //alert is a predefine window, can change the AlertType.Error
        initOwner(MainApp.stage) //per define dialog is a window, from 1 window to another window, we must keep track on the relationship (call warning window will fall back to the stage window)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table."
      alert.showAndWait()

  @FXML
  def handleNewPerson(action: ActionEvent) =
    val person = new Person("", "") //when someone click new botton, it create the person with empty first and last name
    val okClicked = MainApp.showPersonEditDialog(person); //pass in the person that u created
    if (okClicked) then //wait for the return value
      MainApp.personData += person //if ok append the person to ur collection

  @FXML
  def handleEditPerson(action: ActionEvent) =
    val selectedPerson = personTable.selectionModel().selectedItem.value //need to make sure user select an user to edit
    if (selectedPerson != null) then //check is null anot
      val okClicked = MainApp.showPersonEditDialog(selectedPerson) //if not null, show

      if (okClicked) then showPersonDetails(Some(selectedPerson)) //if select ok, show selected person details

    else
      // Nothing selected.
      val alert = new Alert(Alert.AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table."
      alert.showAndWait()






