package objektwerks

import java.text.DecimalFormat

import scala.collection.mutable

object Reporter {
  def buildReport(store: Store): Array[String] = {
    val devices = store.listDevices
    val builder = mutable.ArrayBuilder.make[String]
    builder += s"*** Device Report [${devices.size}]"
    for( (deviceId, readings) <- store.listDeviceReadings.groupBy(_.deviceId) ) {
      devices.find(device => device.id == deviceId).foreach {
        val formatter = new DecimalFormat("#.##")
        val valueAvg = formatter.format( readings.map(reading => reading.value).fold(0.0)(_ + _) / readings.size )
        device => builder += s"*** Device: ${device.name} Readings: ${readings.size} Average: $valueAvg"
      }
    }
    builder.result()
  }
}