## Hubitat SmartLife Device Integration

#### Version 0.0.1 (Jan 1, 2025) 

## SmartLife (Tuya) Tested Devices
* [Dywarmic Smart Towel Warmer](https://www.dywarmic.com/)

<a href="https://www.amazon.com/Dywarmic-Smart-Towel-Warmer-Coolbreeze/dp/B0D9Y8DSMS" target="_blank"> <img src=http://raw.githubusercontent.com/KurtSanders/Hubitat-Smart-Life/refs/heads/main/Images/Dywarmic%20Warmer%20Top%20Down.jpg align="center" width="300" height="300" /> </a>

[Amazon USA](https://www.amazon.com/Dywarmic-Smart-Towel-Warmer-Coolbreeze/dp/B0D9Y8DSMS) <a href="https://www.amazon.com/Dywarmic-Smart-Towel-Warmer-Coolbreeze/dp/B0D9Y8DSMS" target="_blank"> <img src="http://raw.githubusercontent.com/KurtSanders/Hubitat-Smart-Life/refs/heads/main/Images/amazon-link-logo.jpg" width="30" height="25" /> </a>

## Integration Features:
* Hubitat local direct device API access (No Cloud)
* Switch: Turn On/Off
* Duration: Set Intervals in 20 min increments
* Device API Debugging

## Prerequisites 
* [Hubitat Elevation Hub](https://hubitat.com/)
* Knowledge of adding/configuring Hubitat User Apps  
* Use of [Hubitat Package Manager](https://hubitatpackagemanager.hubitatcommunity.com/installing.html) to install/update device preferences
* Active member of the [Hubitat Community](https://community.hubitat.com/) for "Best-Effort" support and new release information.
* Tuya Developer Account
* Knowledge of using the Python Language

## Installation
### Tuya Hubitat Plugin

The hubitat custom driver is for controlling powered by Tuya (PBT) devices. It's based on [Tuya Open API](https://developer.Tuya.com/en/docs/cloud/?_source=2e646f88eae60b7eb595e94fc3866975). This custom plugin was written for the open forum community at Hubitat.

### Preparation

1. [Tuya IoT Platform Configuration](https://github.com/Tuya/Tuya-homebridge/wiki/Tuya-IoT-Platform-Configuration-Guide-Using-Smart-Home-PaaS?_source=d8fba44feeef4757f7f22a14c2295f3f)
2. Driver username and password: The username and password of the Tuya Smart or Smart Life app that you used to link devices on the Tuya IoT Platform.
3. Driver accessId and accessKey are the Access ID and Access Secret of your cloud project, respectively. Go to the Tuya IoT Platform and select your cloud project. Click Project Overview and find the Access ID and Access Secret.
4. Determine your device's local_key

# Tuya Wifi Hubitat device Drivers

This integration utilizes the API Tuya protocol, which connects to the device on the local network, i.e., not via the Tuya servers, to control and read status from devices. This driver supports 3.1 - 3.4 versions of the Tuya API protocol.

All Tuya devices are communicating over the network using AES ECB encryption. The key used for encrypting and decrypting is created to pair the device with a Tuya smart app (SmartLife). One needs to obtain this key to use this Hubitat device driver.

***

## How to get the Hubitat Tuya integration working?

1. You need to get the device id, device key, device local ip-address, and device endpoints by following this [Tuya Guide](https://github.com/jasonacox/tinyTuya#tinyTuya)

> Be aware that every time you pair a device to a smart app, a new local key is generated; hence, it must be updated!

### Quick Summary
1. Install [Python](https://www.python.org/) on a computer, if needed.
2. Install the [TinyTuya module](https://github.com/jasonacox/tinyTuya#tinyTuya-setup)
3. Using your phone, pair all devices you want to use in Hubitat using the SmartLife app. Refer to the device user guide on how to pair the device.
4. Scan you network for Tuya devices by writing the following in command line ```python -m tinyTuya scan```
5. Create an account and log on to iot.Tuya.com and connect your Tuya smart app to this account see step 3 here: https://github.com/jasonacox/tinyTuya#setup-wizard---getting-local-keys
6. Run the tinyTuya wizard, step 4 here: https://github.com/jasonacox/tinyTuya#setup-wizard---getting-local-keys
7. Now you should have full overview of everything you need to get the Hubitat device driver working
8. Add this device driver using Hubitat Package Manager.
9. In Hubitat, open "Devices", then click "Add Device", and "</>Virtual".
10. Give the device a Device Name" and select the Type: "Hubitat SmartLife Integration", and click "Save Device"
11. Now, add the "Device IP", "Device ID", "Device local Key" and select the Tuya protocol version that you got from point 6.
12. Lastly set the Endpoint you want to control. During step 6 you should see something called "Status" together with each device, something like this: ```Status: {'1': False, '2': False, '3': False, '5': 0}```, the endpoints are the numbers in front of the ```False```. This status block is for a "Dywarmic Towel Warmer", where each number corresponds to a status of the device.
13. Click "Save Preferences"
14. You should now be able to control your Tuya device via Hubitat on local network only.

It is highly recommended to assign a static IP on your (wifi)router to the device so that it does not change ip-address, then the Hubitat integration won't be able to find the device.

## FAQ

**Q: Will I be billed after the 1-year free trial of the API product expires?**

A: After expiration, you can renew the subscription for free. The Trial Edition allows you to use all free API products but limits the quota of API calls. For more information about the quota, see [Pricing](https://developer.Tuya.com/en/docs/iot/membership-service?id=K9m8k45jwvg9j&_source=bb1b5b405f43ab2b3c7a7cb9ca95773d).

**Q: Issues / Limitations** 

**Q: Misc Features / Tips**
