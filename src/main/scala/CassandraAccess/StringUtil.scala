package CassandraAccess

import java.nio.ByteBuffer

object StringUtil {
  def toByteBuffer(str: String) = ByteBuffer.wrap(str.getBytes("UTF-8"))
  def toString(buf: ByteBuffer) = buf.get().toString
}
