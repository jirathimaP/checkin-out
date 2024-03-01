package com.jirap.checkinout

object Constants {
    object MockData {
        //const val language = "en"
        const val userAgent = "android"
        //const val sessionRefCode = "20190219105321000" // yyyyMMddHHmmSSXXX
        //const val deviceID = "123456789012345678"
        //const val appVersion = "test_app_version"
        //const val deviceVersion = "test_device_version"
        //const val actionBy = "1"

    }
    object ResponseCode {
        const val success = "0000"
        const val sessionNotFound = "cm0104"
        const val sessionExpire = "cm0105"
    }

    object ResponseMessage {
        const val connectionProb = "พบปัญหาการเชื่อมต่อ"
        const val tryAgain = "โปรดลองใหม่ภายหลัง"
        const val sessionExp = "การเชื่อมต่อหมดอายุ"
        const val sessionExpDesc = "โปรดเข้าสู่ระบบอีกครั้งเพื่อต่ออายุเซสชัน"
        const val sessionNotFound = "พบปัญหาการเชื่อมต่อ"
        const val sessionNotFoundDesc = "ไม่พบเซสชัน โปรดเข้าสู่ระบบอีกครั้ง"
        const val baseTitle = "Sorry!"
        const val networkLost = "กรุณาตรวจสอบการเชื่อมต่ออินเตอร์เน็ตของท่าน"
        const val duplicateItemCodeDesc = "item code ถูกใช้งานไปแล้ว"
        const val inputInvalid = "กรุณากรอกข้อมูลให้ครบ"
    }
}