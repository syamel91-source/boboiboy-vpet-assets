package com.boboiboy.vpet.offline

data class BluetoothPacket(val type:String,val value:String)

object BluetoothVpetProtocol {
    const val SERVICE_UUID="0000B0B0-0000-1000-8000-00805F9B34FB"
    const val CHARACTERISTIC_UUID="0000B0B1-0000-1000-8000-00805F9B34FB"
    fun encode(packet:BluetoothPacket):ByteArray=(packet.type+"|"+packet.value+"\n").toByteArray(Charsets.UTF_8)
    fun decode(bytes:ByteArray):BluetoothPacket? {
        val p=String(bytes,Charsets.UTF_8).trim().split("|",limit=2)
        return if(p.size==2) BluetoothPacket(p[0],p[1]) else null
    }
}
