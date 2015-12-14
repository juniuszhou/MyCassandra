package CassandraAccess

import com.datastax.driver.core.Cluster

/**
 * sql level api and driver from datastax.
 * http://docs.datastax.com/en/developer/java-driver/2.1/common/drivers/reference/driverReference_r.html
 *
 */
object DatastaxClient2 {
  def main (args: Array[String]) {
    // Connect to the cluster and keyspace test
    val cluster = Cluster.builder().addContactPoint("127.0.0.1").build()
    val session = cluster.connect("test")

    // Insert one record into the users table
    //  session.execute("INSERT INTO users (lastname, age, city, email, firstname) VALUES ('Jones', 35, 'Austin', 'bob@example.com', 'Bob')");

    // Use select to get the user we just entered
    val results = session.execute("SELECT * FROM users")
    val iter = results.iterator()
    while (iter.hasNext()) println({
      val str = iter.next()
      str
    })

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
