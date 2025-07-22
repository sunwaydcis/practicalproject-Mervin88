package ch.makery.address.util
import scalikejdbc.* //library to connect the database
import ch.makery.address.model.Person

trait Database : //
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
  val dbURL = "jdbc:derby:myDB;create=true;"; //point to the database, if the database is not created it will auto create by set it to true
  // initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname) 
  ConnectionPool.singleton(dbURL, "me", "mine") //is a library for executing sql into the database
  // ad-hoc session provider on the REPL
  given AutoSession = AutoSession 

object Database extends Database :
  def setupDB() =
    if (!hasDBInitialize)
      Person.initializeTable()
  def hasDBInitialize : Boolean =
    DB getTable "Person" match
      case Some(x) => true
      case None => false

