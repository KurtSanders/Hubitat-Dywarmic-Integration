## Hubitat Tuya Drivers

### Version 1.0.0 (Jan 1, 2025) 

## Features:
-- 
<a href="https://www.amazon.com/Dywarmic-Smart-Towel-Warmer-Coolbreeze/dp/B0D9Y8DSMS" target="_blank"> <img src="http://raw.githubusercontent.com/KurtSanders/Hubitat-Smart-Life/refs/heads/main/Images/Dywarmic%20Warmer%20Top%20Down.jpg" width="300" height="300" /> </a>

* Integrates a Smart Life (Tuya) Heated Towel Rack Compatible Devices.
	* [Dywarmic Smart Towel Warmer Website](https://www.dywarmic.com/)
	* [Amazon US](https://www.amazon.com/Dywarmic-Smart-Towel-Warmer-Coolbreeze/dp/B0D9Y8DSMS"  target="_blank") <a href="https://www.amazon.com/Dywarmic-Smart-Towel-Warmer-Coolbreeze/dp/B0D9Y8DSMS" target="_blank">
  <img src="http://raw.githubusercontent.com/KurtSanders/Hubitat-Smart-Life/refs/heads/main/Images/amazon-link-logo.jpg" width="30" height="25" />
 </a>
* Hubitat local access on Home Network
* On/Off
* Set Delay

## Overview:

* TBD

## Prerequisites 
* [Hubitat Elevation Hub](https://hubitat.com/)
	* Knowledge of adding/configuring Hubitat User Apps  
	* Use of [Hubitat Package Manager](https://hubitatpackagemanager.hubitatcommunity.com/installing.html) to install/update user apps
	* Member of the [Hubitat Community](https://community.hubitat.com/) for support and new release information.

## Installation
### Tuya Hubitat Plugin

The hubitat custom driver is for controlling powered by Tuya (PBT) devices. It's based on [Tuya Open API](https://developer.tuya.com/en/docs/cloud/?_source=2e646f88eae60b7eb595e94fc3866975). This unsupported plugin was written for the open forum community at Hubitat.

### Preparation

1. [Tuya IoT Platform Configuration](https://github.com/tuya/tuya-homebridge/wiki/Tuya-IoT-Platform-Configuration-Guide-Using-Smart-Home-PaaS?_source=d8fba44feeef4757f7f22a14c2295f3f)
2. Driver username and password: The username and password of the Tuya Smart or Smart Life app that you used to link devices on the Tuya IoT Platform.
3. Driver accessId and accessKey are the Access ID and Access Secret of your cloud project, respectively. Go to the Tuya IoT Platform and select your cloud project. Click Project Overview and find the Access ID and Access Secret.
4. Determine your device's local_key

## FAQ

**Q: Will I be billed after the 1-year free trial of the API product expires?**

A: After expiration, you can renew the subscription for free. The Trial Edition allows you to use all free API products but limits the quota of API calls. For more information about the quota, see [Pricing](https://developer.tuya.com/en/docs/iot/membership-service?id=K9m8k45jwvg9j&_source=bb1b5b405f43ab2b3c7a7cb9ca95773d).

**Q: Issues / Limitations** 

**Q: Misc Features / Tips**
