/*
 * Hubitat DyWarmic Towel Warmer Integration
 *
 *  Licensed Virtual the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
*/
import groovy.transform.Field
@Field static String DEVICE_DRIVER_NAME = "Dywarmic Towel Warmer"
@Field static String DNI = "Dywarmic-Towel-Warmer"
@Field static String NAMESPACE = "kurtsanders"
@Field static String thisAppName = "Dywarmic Towel Warmer Integration"

definition (
    name: 			thisAppName,
    namespace: 		NAMESPACE,
    author: 		"Kurt Sanders",
    description: 	"${thisAppName} Virtual Device Installation",
    category: 		"Utility",
    installOnOpen:  true,
    oauth: 			false,
    iconUrl: '',
    iconX2Url: '',
    iconX3Url: '',
    singleThreaded: true
)

void installed() {
    log.info "${app.getLabel()} installed()"
}

void updated(){
    log.info "${app.getLabel()} updated()"
}

preferences {
    if (!deviceExists()) {
        page(name: "mainPage", title: "", install: false, nextPage: "devicePage", uninstall: true) {
            section("") {
                input "baseName", "text", title: "Device Name for Towel Warmer", defaultValue: "Dywarmic Towel Warmer", submitOnChange:true, required:true, width:4
            }
        }
    }
    page(name: "devicePage", title: "", install: true, uninstall: true)
}

def devicePage() {
    createDevice()
    dynamicPage(name: "devicePage") {
        section("") {
            paragraph "The '${baseName}' device has been created."
            paragraph "Complete final installation steps in the ${state.deviceURL} device info section."
        }
    }
}

def makeURL() {
    def d=getChildDevice(DNI)
    if (d) {
        state.deviceURL = "<span><a href='http://${location.hub.localIP}/device/edit/${d.id}' target='_blank' rel='noopener noreferrer'>${baseName}</a></span>"
        }
}

def deviceExists() {
    def d=getChildDevice(DNI)
    if (d) {
        makeURL()
        return true
    } else return false
}

def createDevice() {
    if (!deviceExists()) {
        d = addChildDevice(NAMESPACE, DEVICE_DRIVER_NAME, DNI, [name: "${baseName}", label: "${baseName}", isComponent: false])
        if (d) {
            log.info "createDevices(): '$baseName' device created"
            makeURL()
        } else {
            log.error "createDevices(): '$baseName' device was not created"
        }
    }
}