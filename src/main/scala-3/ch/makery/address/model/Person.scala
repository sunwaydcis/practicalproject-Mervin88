package ch.makery.address.model

import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}
import java.time.LocalDate;

class Person ( firstNameS : String, lastNameS : String )://6 properties
  var firstName  = new StringProperty(firstNameS) //
  var lastName   = new StringProperty(lastNameS)
  var street     = new StringProperty("some Street")
  //var postalCode = IntegerProperty(1234)
  var postalCode = ObjectProperty[Int](1234) //companion object
  var city       = new StringProperty("some city")
  var date       = ObjectProperty[LocalDate](LocalDate.of(1999, 2, 21))
