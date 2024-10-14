package util

import java.nio.charset.Charset

internal object TestResourceReader {
  fun readFileAsString(
    fileName: String,
    charset: Charset = Charset.defaultCharset(),
  ): String = javaClass.classLoader!!.getResource(fileName).readText(charset)
}
