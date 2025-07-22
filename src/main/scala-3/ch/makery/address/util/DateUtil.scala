package ch.makery.address.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

object DateUtil :
  val DATE_PATTERN = "dd.MM.yyyy" //define date pattern
  val DATE_FORMATTER =  DateTimeFormatter.ofPattern(DATE_PATTERN)

  extension (date: LocalDate)//extension allow one to add methods to a type after the type is defined
    /**
     * Returns the given date as a well formatted String. The above defined
     * {@link DateUtil# DATE_PATTERN} is used.
     *
     * @param date the date to be returned as a string
     * @return formatted string
     */
    def asString: String = //create a convertion to all local date object 
      if (date == null)
        return null;
      return DATE_FORMATTER.format(date);

  extension (data : String)//add extra 1 more methods
    /**
     * Converts a String in the format of the defined {@link DateUtil# DATE_PATTERN}
     * to a {@link LocalDate} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    def parseLocalDate: Option[LocalDate] = //no null value 
      try
        Option(LocalDate.parse(data, DATE_FORMATTER))
      catch
        case  e: DateTimeParseException => None

    def isValid : Boolean =
      data.parseLocalDate != None
