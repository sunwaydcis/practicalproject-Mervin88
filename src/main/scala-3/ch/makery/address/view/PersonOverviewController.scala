package ch.makery.address.view
import ch.makery.address.model.Person
import ch.makery.address.MainApp
import javafx.fxml.FXML
import javafx.scene.control.{Label, TableColumn, TableView}
import scalafx.Includes.*
import javafx.scene.control.TextField
import ch.makery.address.util.DateUtil.*
import javafx.event.ActionEvent
import scalafx.beans.binding.Bindings //all the string & local date inside this source code will have extension

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
    val selectedIndex = personTable.selectionModel().selectedIndex.value //selected intex - which row
    if (selectedIndex >= 0) then
      personTable.items().remove(selectedIndex);



