package CassandraAccess

import com.datastax.driver.core.Cluster

/**
 * sql level api and driver from datastax.
 */
object DatastaxClientLpas {
  def main (args: Array[String]) {
    // Connect to the cluster and keyspace test
    val hosts = Seq("10.2.12.82", "10.2.12.82")
    val cluster = Cluster.builder().addContactPoints("10.2.12.115", "10.2.12.82").build()
    val session = cluster.connect("lpas")



    // Insert one record into the users table
    //  session.execute("INSERT INTO users (lastname, age, city, email, firstname) VALUES ('Jones', 35, 'Austin', 'bob@example.com', 'Bob')");

    session.execute(s"CREATE TABLE IF NOT EXISTS LpasPricingAnalysis (hotelId TEXT PRIMARY KEY, available TEXT)")

    session.execute(s"INSERT INTO LpasPricingAnalysis (hotelId, available) VALUES ('1', '1')")
    /*
    val results = session.execute("SELECT * FROM users")
    val iter = results.iterator()
    while (iter.hasNext()) println({
      val str = iter.next()
      str
    })
*/

    // Update the same user with a new age
    //session.execute("update users set age = 36 where lastname = 'Jones'")

    // Delete the user from the users table
    //session.execute("DELETE FROM users WHERE lastname = 'Jones'")
    println("access over")
    session.closeAsync()
    println("over")

    // must call cluster close otherwise program will still running.
    cluster.closeAsync()
    println("final over")

  }
}
