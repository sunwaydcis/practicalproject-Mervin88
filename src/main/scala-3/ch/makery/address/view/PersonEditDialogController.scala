//controller of our FXML window
package ch.makery.address.view

import ch.makery.address.model.Person
import ch.makery.address.MainApp
import javafx.scene.control.{ Label, TableColumn, TextField}
import scalafx.scene.control.Alert
import scalafx.stage.Stage
import scalafx.Includes.*
import ch.makery.address.util.DateUtil.*
import javafx.fxml.FXML
import javafx.event.ActionEvent

@FXML
class PersonEditDialogController ():
  @FXML
  private var firstNameField: TextField = null
  @FXML
  private var lastNameField: TextField = null
  @FXML
  private var streetField: TextField = null
  @FXML
  private var postalCodeField: TextField = null
  @FXML
  private var cityField: TextField = null
  @FXML
  private var birthdayField: TextField = null

  var         dialogStage  : Stage  = null //all window value
  private var __person     : Person = null //person property
  var         okClicked             = false

  def person = __person //person property
  def person_=(x : Person): Unit = //private data field
    __person = x //create getter and setter

    firstNameField.text = __person.firstName.value
    lastNameField.text  = __person.lastName.value
    streetField.text    = __person.street.value
    postalCodeField.text= __person.postalCode.value.toString
    cityField.text      = __person.city.value
    birthdayField.text  = __person.date.value.asString
    birthdayField.setPromptText("dd.mm.yyyy")

  @FXML
  def handleOk(action :ActionEvent): Unit = { //variable to store whether user click ok anot
    if (isInputValid())
      __person.firstName <== firstNameField.text
      __person.lastName  <== lastNameField.text
      __person.street    <== streetField.text
      __person.city      <== cityField.text
      __person.postalCode.value = postalCodeField.getText().toInt
      __person.date.value       = birthdayField.text.value.parseLocalDate.getOrElse(null); //option object cannot straight away assign

      okClicked = true
      dialogStage.close()
  }

  @FXML
  def handleCancel(action :ActionEvent): Unit =
    dialogStage.close()

  def nullChecking (x : String) = x == null || x.length == 0

  def isInputValid() : Boolean =
    var errorMessage = ""

    if (nullChecking(firstNameField.text.value)) then
      errorMessage += "No valid first name!\n"
    if (nullChecking(lastNameField.text.value)) then
      errorMessage += "No valid last name!\n"
    if (nullChecking(streetField.text.value)) then
      errorMessage += "No valid street!\n"
    if (nullChecking(postalCodeField.text.value)) then
      errorMessage += "No valid postal code!\n"
    else
      try
        postalCodeField.getText().toInt //check postal code is integer
      catch
        case e : NumberFormatException =>
          errorMessage += "No valid postal code (must be an integer)!\n"

    if (nullChecking(cityField.text.value)) then
      errorMessage += "No valid city!\n"
    if (nullChecking(birthdayField.text.value)) then
      errorMessage += "No valid birtday!\n"
    else
      if (!birthdayField.text.value.isValid) then //check for the birthday pattern
        errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";

    if (errorMessage.length() == 0) then //check error message is empty anot
      true
    else
      // Show the error message.
      val alert = new Alert(Alert.AlertType.Error): //predefine window
        initOwner(dialogStage)
        title = "Invalid Fields"
        headerText = "Please correct invalid fields"
        contentText = errorMessage
      alert.showAndWait()
      false
    end if
