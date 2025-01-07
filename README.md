## Hubitat Dywarmic Towel Warmer Integration

#### Version 0.0.1 (Jan 1, 2025) 

This Hubitat integration provides local network control and the status of the Dywarmic towel warmer (tuya category: mjj).  

## tuya Tested Towel Warmers
* [Dywarmic Smart Towel Warmer](https://www.dywarmic.com/)

<a href="https://www.amazon.com/Dywarmic-Smart-Towel-Warmer-Coolbreeze/dp/B0D9Y8DSMS" target="_blank"> <img src=https://raw.githubusercontent.com/KurtSanders/Hubitat-Dywarmic-Integration/refs/heads/main/Images/Dywarmic%20Warmer%20Top%20Down.jpg align="center" width="400" height="400" /> </a>

[See it on Amazon USA](https://www.amazon.com/Dywarmic-Smart-Towel-Warmer-Coolbreeze/dp/B0D9Y8DSMS) <a href="https://www.amazon.com/Dywarmic-Smart-Towel-Warmer-Coolbreeze/dp/B0D9Y8DSMS" target="_blank"> <img src="https://raw.githubusercontent.com/KurtSanders/Hubitat-Dywarmic-Integration/refs/heads/main/Images/amazon-link-logo.jpg" width="30" height="25" /> </a>

## Features:

| Feature | Description |
|------|--------|
| Hubitat local direct device API access|Local WiFi connectivity which eliminates cloud dependency|
| Switch | * Turn On <br>* Turn Off|
| Set/Change Countdown Duration Timer | * Set/Cancel warming duration in intervals of 20-minute increments|
| Set Countdown Duration Timer Default | * Set countdown duration timer default value when towel warmer is switched on|
|Reduce hub events| * Reduce frequent 'current temperature' events by **only** creating an event to the hub every ± delta N° units.<br>* Ignore nonessential towel warmer events.|
| Device API Debugging | * Custom Data To Device<br>* Send Custom JSON Object |
| Set Features| * Child Lock<br>* Eco<br>* Light|
| Refresh| Updates device states |

## Prerequisites 
* [Hubitat Elevation Hub](https://hubitat.com/)
*  [Dywarmic Smart Towel Warmer](https://www.dywarmic.com/)
   * WiFi connected to a static IP address
   * Integrated into the Smart Life app
     * [Google Play](https://play.google.com/store/apps/detailsid=com.tuya.smartlife&hl=en_US)
     * [Apple Store](https://apps.apple.com/us/app/smart-life-smart-living/id1115101477)
* Knowledge of [configuring a Hubitat virtual device](https://community.hubitat.com/t/creating-a-virtual-device-for-wifi-devices/46514)  
* Use of [Hubitat Package Manager (HPM)](https://hubitatpackagemanager.hubitatcommunity.com/installing.html) to install/update this custom application.
* [tuya Developer Account](https://developer.tuya.com/en/)
* Knowledge of and access to a computer with [Python Language](https://www.datacamp.com/blog/how-to-learn-python-expert-guide)

## Installation
### Install the Dywarmic Towel Warmer Application on your Hubitat Hub
 * Add the Dywarmic Towel Warmer application using [Hubitat Package Manager](https://hubitatpackagemanager.hubitatcommunity.com/) by selecting 'Install' and searching for 'Dywarmic Towel Warmer' or by tags 'Bathroom' or 'Temperature' or 'Pools & Spas.'
* Allow HPM to automatically launch the Dywarmic Towel Warmer app, which will create a virtual child device (Dywarmic Towel Warmer device driver) that you can name, i.e., 'Towel Warmer.' 
* Verify this new device has been created in your devices' view
 
### Preparation

1. Read [tuya IoT Platform Configuration](https://github.com/tuya/tuya-homebridge/wiki/tuya-IoT-Platform-Configuration-Guide-Using-Smart-Home-PaaS?_source=d8fba44feeef4757f7f22a14c2295f3f) documentation
2. Driver username and password: The username and password of the tuya Smart or Smart Life app that you used to link devices on the tuya IoT Platform.
3. Driver accessId and accessKey are your cloud project's Access ID and Access Secret, respectively. Go to the tuya IoT Platform and select your cloud project. Click Project Overview and find the Access ID and Access Secret.
4. Determine your towel warmers' local_key.

### tuya Wifi Hubitat device Drivers

* This integration utilizes the API tuya protocol, which connects to the device on the local network, i.e., not via the tuya servers, to control and read the status of devices. This Hubitat device driver supports versions 3.1 - 3.4 of the tuya API protocol. 
  * The Dywarmic Towel Warmer uses tuya API protocol **3.4**

* All tuya devices are communicating over the network using AES ECB encryption. The key for encrypting and decrypting is created to pair the device with the SmartLife mobile app. One needs to obtain this key to use this Hubitat device driver.

### How to get the Hubitat tuya integration working?

1. You need to get the Dywarmic Towel Warmer device ID, device key, device local ip-address, and device endpoints by following this [tuya Guide](https://github.com/jasonacox/tinytuya#tinytuya) and the 'Install steps' listed below.

> Be aware that a new local key may be generated when a new device is added to or paired with the SmartLife app; hence, it must be updated when/if you get another device and add it to Smart Life!

### Install Steps
1. Install the latest version of [Python](https://www.python.org/) on a computer, if needed.
2. Install the [Tinytuya module](https://github.com/jasonacox/tinytuya#tinytuya-setup) on this same computer.
3. Install the Smart Life app ([Apple](https://apps.apple.com/us/app/smart-life-smart-living/id1115101477) or [Google](https://play.google.com/store/apps/details?id=com.tuya.smartlife&hl=en_US)) and pair all devices you want to use in Hubitat. Refer to the device user guide on how to pair the device.
4. Scan your network for the paired tuya devices by writing the following in the Unix/Dos command line ```python -m tinytuya scan```
5. Create a [iot.tuya.com](https://iot.tuya.com) developer account and log on.  Connect your SmartLife app to this developer account using these [instructions](https://github.com/jasonacox/tinytuya#setup-wizard---getting-local-keys).
6. Run the [tinytuya wizard](https://github.com/jasonacox/tinytuya#setup-wizard---getting-local-keys)
7. Now you should have a full overview of everything you need to get the Hubitat device driver working
8. In Hubitat, open "Devices", then click "Add Device", and click "Virtual".
9. Give the device a Device Name" and select the Type: "Hubitat SmartLife Integration", and click "Save Device."
10. Now, add the "Device IP", "Device ID", "Device local Key" and select the tuya protocol version that you got from point 6.
11. Lastly, set the Endpoint you want to control. During step 6, you should see something called "Status" together with each device, something like this: ```Status: {'1': False, '2': False, '3': False, '5': 0}```, the endpoints are the numbers in front of the ```False```. This status block is for a "Dywarmic Towel Warmer," where each number corresponds to the status of the device.
12. Click "Save Preferences"
13. You should now be able to control your Towel Warmer device via Hubitat on the local network only.

* *It is highly recommended that you lock a static IP on your (wifi)router to the towel warmer device so it does not change its IP address. If it gets a new ip address, then this Hubitat integration won't be able to find the device, and you will need to change the ip address in the device driver preferences.*

## FAQ

**Q: Will I be billed after the 1-year free trial of the API product expires?**

A: After expiration, you can renew the subscription for free. The Trial Edition allows you to use all free API products but limits the quota of API calls. For more information about the quota, see [Pricing](https://developer.tuya.com/en/docs/iot/membership-service?id=K9m8k45jwvg9j&_source=bb1b5b405f43ab2b3c7a7cb9ca95773d).

**Q: Issues / Limitations** 

A: Updates to a towel warmer firmware or API may render this custom device driver's connectivity in the future.  Please report to Habitat's open forum support thread.


### Version 0.0.1 (1/6/2024)
* Added to HPM
* User-specified control of frequently reported temperature events by ± temperature delta.

   |Filename|Type|Version|
|---|---|---|
|SanderSoft-Library.groovy|Library|Version 0.0.4|
|Dywarmic Towel Warmer Integration.groovy|App|Version 0.0.1|
|Dywarmic Towel Warmer.groovy|Driver|0.0.1|
