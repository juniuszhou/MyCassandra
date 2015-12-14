package CassandraAccess

import java.nio.charset.Charset
import java.nio.ByteBuffer

import org.apache.thrift.transport.TSocket
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.cassandra.thrift.Cassandra
import org.apache.cassandra.thrift.ColumnPath

import org.apache.cassandra.thrift._

/*
  use thrift raw api to communicate with cassandra.
 */

object DatastaxClient {

    def main(args: Array[String])  {

      // 建立数据库连接
      val tr = new TSocket("127.0.0.1", 9160)
      val proto = new TBinaryProtocol(tr)
      val client = new Cassandra.Client(proto)
      tr.open()
      if(tr.isOpen) println("open successful.")

      //set key space
      val keyspace = "test"
      client.set_keyspace(keyspace)

      //set table
      val cf = "users"
      val path = new ColumnParent(cf)

      def insert() = {
        val key = "user_name"
        val column = new Column()
        column.setName(key.getBytes)
        column.setValue("newValue".getBytes)
        column.setTimestamp(System.currentTimeMillis())

        val timestamp = ByteBuffer.wrap(System.currentTimeMillis().toString.getBytes)

        val charset = Charset.forName("UTF-8")
        val encoder = charset.newEncoder()

        //val bf = encoder.encode(CharBuffer.wrap(keyspace))
        client.insert(timestamp, path, column, org.apache.cassandra.thrift.ConsistencyLevel.ONE)
      }

      def read() = {
        val columnPath = new ColumnPath("user_name")
        val value = client.get(ByteBuffer.wrap("zhou".getBytes), columnPath, ConsistencyLevel.ONE)
        println(value)

        //read a row
        val predicate = new SlicePredicate()
        val range = new SliceRange(ByteBuffer.wrap("".getBytes), ByteBuffer.wrap("".getBytes), false, 10)
        predicate.setSlice_range(range)
        val data = client.get_slice(ByteBuffer.wrap("zhou".getBytes), path, predicate, ConsistencyLevel.ONE)
        data.toArray.foreach(obj => {
          val col = obj.asInstanceOf[Column]
          println(col.name.toString)
          println(col.value.toString)
        })
      }

      insert()
      /*
      path.setColumn("age".getBytes("UTF-8"))

      path.setColumn("height".getBytes("UTF-8"))
      client.insert(keyspace,key,path,"172cm".getBytes("UTF-8"),
        ONE)

      // 读取数据
      path.setColumn("height".getBytes("UTF-8"))
      //val bf = new ByteBuffer(key)

      val cc = client.get("key", path, ConsistencyLevel.ONE)
      val c = cc.getColumn()
      val v = new String(c.value, "UTF-8")
      // 关闭数据库连接
      tr.close()

      */

  }
}
