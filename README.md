# Low Utils
## init
* unlock():void
* openSet(String):void
* clearNotifications():void

## App
* openAppList():void
* getAppList():ArrayList<App>
* openApp(String):void

## UiObject
* getObjByTxt(String):UiObject
* getObjByTxtContains(String):UiObject
* getObjByClsTxt(String, String):UiObject
* getObjByClsTxtContains(String, String):UiObject
* getObjById(String):UiObject
* getObjByClsIdx(String, String):UiObject
* getObjByDesc(String):UiObject
* getObjByDescContains(String):UiObject
* getChildByClsTxt(String, String):UiObject

## UiScrollable
* getScrObj():UiScrollable
* getScrObjByCls(String):UiScrollable

## Editor
* getEdit():UiObject
* getEditByTxt(String):UiObject
* getMultiEdit():UiObject
* getMultiEditByTxt(String):UiObject
* clearEditTxt(UiObject):void
* setEditTxt(UiObject, String):void

## RecentApp
* getRecentApp(String):UiObject
* openRecentApp(String):void
* clearRecentApp(String):void
* clearAllRecentApp():void

## Action
* toScreenX(float):int
* toScreenY(float):int
* pressHome():void
* pressBack():void
* PressDelete():void
* pressMenu():void
* pressEnter():void
* swipe(String):boolean
* longPress(UiObject):void      //TODO:
* longPress(int, int):void      //TODO:
* longPress(float, float):void  //TODO:


# Mid Utils

## Settings
### Wifi
* getWifiStat():String
* openWifi():void
* closeWifi():void
* connectWifi(String, String):void

### ApMode
* openApMode():void
* closeApMode():void

### Bluetooth
* openBluetooth():void
* closeBluetooth():void

### DataSet
* openDataSet():void
* closeDataSet():void

## Contact
* openContact(String):void
* addContact(Contact):void
* delContact(String):void
* getContact(String):Contact

## Sms
* sendSms(String, String):void
* sendSmsByTelnum(String, String):void
* delSms(String):void

## Phone
* callTelnum(String):void
* callName(String):void

## Browser
* openUrl(String):void

## Calculator
* calculate(String):void

## File Manager
* openDir(String):void
* delFile(String):boolean   //TODO:

# High Utils
