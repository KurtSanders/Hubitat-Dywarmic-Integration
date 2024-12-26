import groovy.transform.Field
import java.text.SimpleDateFormat
import groovy.time.TimeCategory

@Field static final List COUNTDOWNLIST = ["1h","2h","3h","4h","5h","6h","cancel"]
@Field static final List THERMO_STAT_OPERATING_STATE = ["heating", "idle", "off"]
@Field static final List MODES = ["smart", "manual"]
@Field static final List ONOFF = ["on", "off"]
@Field static final List CODES = ["light","child_lock","eco"]
@Field static final Map POLLING_INT = ['01':'01 Mins','02':'02 Mins','03':'03 Mins','04':'04 Mins','05':'05 Mins','10':'10 Mins','30':'30 Mins','0':'No Polling']


metadata {
    definition(name: 'Component Generic Towel Rack Device', namespace: 'component', author: 'Jonathan Bradshaw') {
        capability 'Actuator'
        capability 'Switch'
        capability 'TemperatureMeasurement'
        capability 'ThermostatHeatingSetpoint'
        capability 'Refresh'
        attribute "countdown_left", "number"
        attribute "countdown_set", "string"
        attribute "light", "string"
        attribute "child_lock", "string"
        attribute "fault", "string"
        attribute "mode", "string"
        attribute "level", "string"
        attribute "eco", "string"
        attribute "temp_unit_convert", "string"
        attribute "temp_current_f", "string"
        attribute "error", "string"
        attribute "polling", "boolean"

        attribute "thermostatOperatingState", "enum",  THERMO_STAT_OPERATING_STATE
        attribute "thermostatMode", "enum", THERMO_STAT_MODES

        command "adHocPolling", [[name: "Ad Hoc Polling", type:"ENUM", description:"Starts/Stops Polling Outside of Auto Schedule", constraints:['Start','Stop']]]
        command "setHeatingSetpoint", [[name:'Heating Setpoint* 32-270°F', type:'NUMBER', description:'Heating setpoint temperature from 32°F-270°F', range: "32..270"]]
        command "setCountDown", [[name: "Count Down Timer Units*", type:"ENUM", description:"Sets the Count Down Timer [in device dependent increments]", constraints:COUNTDOWNLIST]]
        command "setMode", [[name: "Mode*", type:"ENUM", description:"Set Mode", constraints:MODES]]
        command "setCode", [
            [name: "Code*", type:"ENUM", description:"Code", constraints:CODES],
            [name: "Value*", type:"ENUM", description:"Value", constraints:ONOFF],
        ]
    }
}

preferences {
    section {
        input name: 'logEnable',
            type: 'bool',
            title: 'Enable debug logging for 2 hrs',
            required: false,
            defaultValue: true

        input name: 'txtEnable',
            type: 'bool',
            title: 'Enable descriptionText logging',
            required: false,
            defaultValue: true

        input name: 'pollInterval',
            type: 'enum',
            title: 'Polling Interval',
            description: 'Frequency of Device Polling',
            required: true,
            defaultValue: '0',
            options: POLLING_INT

        input name: "pollingStartTime",
            type: "time",
            title: "Auto Start Time for Polling",
            required: true

            input name: "pollingEndTime",
            type: "time",
            title: "Auto End Time for Polling",
            required: true
    }
}

// Called when the device is first created
void installed() {
    device.updateSetting('pollInterval', [type: "enum", value: "0"])
    log.info "${device} driver installed"
}

// Component command to control Towel Rack features
void setCode(code='',value='off') {
    code = code.toLowerCase()
    value = value.toLowerCase()

    if (logEnable) { log.debug "setCode(): code: ${code}, value ${value}" }
    if (!CODES.contains(code)) {
        log.error "setCode(): Invalid code '${code}'.  Code must be ONE of these following codes: ${CODES.join(', ')}."
        return
    }
    if (!ONOFF.contains(value)) {
        log.error "setCode(): Invalid value '${value}'.  Value must be either: ${ONOFFCODES.join(', ')}."
        return
    }
    parent?.componentTowelRack(device, code, (value=='on'? true : false))
    runIn(3, "refresh")
}

// Component command to control Towel Rack features
void setMode(value='smart') {
    if (logEnable) { log.debug "setMode(): value ${value}" }
    value = value.toLowerCase()
    if (!MODES.contains(value)) {
        log.error "setMode(): Invalid value '${value}'.  Value must be ONE of these following: ${MODES.join(', ')}."
        return
    }
    parent?.componentTowelRack(device, 'mode', value)
    runIn(3, "refresh")
}

void setCountDown(value) {
    if (logEnable) { log.debug "setCountDown(): value ${value}" }
    value = value.toLowerCase()
    if (!COUNTDOWNLIST.contains(value)) {
        log.error "setCountDown(): Invalid value '${value}'.  Value must be ONE of these following: ${COUNTDOWNLIST.join(', ')}."
        return
    }
    parent?.componentTowelRack(device, 'countdown_set', value)
    runIn(3, "refresh")
}


// Component command to turn on device
void on() {
    if (logEnable) { log.debug "Switch on()" }
    parent?.componentOn(device)
    runIn(3, "refresh")
}

// Component command to turn off device
void off() {
    if (logEnable) { log.debug "Switch off()" }
    parent?.componentOff(device)
    runIn(3, "refresh")
}

// Component command to refresh device
void refresh() {
    parent?.componentRefresh(device)
}

// parse commands from parent
void parse(List<Map> description) {
    if (logEnable) { log.debug "parse(): description → ${description}" }
    description.each { d ->
        if (logEnable) { log.info "parse(): ${device} ${d.descriptionText}" }
        switch (d.name) {
            case 'switch':
                sendEvent([ name: 'thermostatMode', value: d.value == 'on' ? 'heat' : 'off' ])
                sendEvent([ name: 'thermostatOperatingState', value: d.value == 'on' ? 'heating' : 'idle' ])
                break
        }
        if (logEnable) { log.debug "parse() description.each → ${d}" }
        sendEvent(d)
    }
}

// Component command to set position of device
void setHeatingSetpoint(BigDecimal temperature) {
    parent?.componentSetHeatingSetpoint(device, temperature)
}

// Called when the device is removed
void uninstalled() {
    log.info "${device} driver uninstalled"
}

// Called when the settings are updated
void updated() {
    log.info "${device} driver configuration updated"
    unschedule()
    if (logEnable) {
        log.debug settings
        runIn(7200, 'logsOff')
    }
    if (pollInterval == '0') {
        state.remove('pollCronString')
        pollScheduler("unschedule")
    } else {
        pollScheduler("schedule")
    }
}

void adHocPolling(cmd='Start') {
    if (cmd.toLowerCase()=='start') {
        startPolling()
    } else {
        stopPolling()
    }
}

void startPolling() {
    if (state?.pollCronString) {
        schedule(state.pollCronString, 'refresh')
        sendEvent(name: 'polling', value: true)
    } else {
        log.error "Start and Stop polling times are not initialized..  Please set and re-try"
    }
}

void stopPolling() {
    unschedule('refresh')
    sendEvent(name: 'polling', value: false)
}

void pollScheduler(option) {
    switch (option) {
        case 'unschedule':
        unschedule('refresh')
        unschedule('startPolling')
        unschedule('stopPolling')
        break

        case 'schedule':
        unschedule('refresh')

        def dtPollingStartTime = toDateTime(pollingStartTime)
        def dtPollingEndTime = toDateTime(pollingEndTime)
        def dtNow = new Date()

        if (!dtCheck(dtPollingStartTime,dtPollingEndTime)) return

        def startHH = dtPollingStartTime.format("HH")
        def endHH   = dtPollingEndTime.format("HH")
        def startMM = dtPollingStartTime.format("mm")
        def endMM   = dtPollingEndTime.format("mm")

        schedule("0 ${startMM} ${startHH} ? * *", 'startPolling')
        schedule("0 ${endMM}   ${endHH}   ? * *", 'endPolling')

        switch (pollInterval) {
            case '0':
            state.remove('pollCronString')
            break
            case '01':
            case '02':
            case '03':
            case '04':
            case '05':
            case '10':
            case '30':
            log.info "Polling scheduled for every ${pollInterval} minutes during ${toDateTime(pollingStartTime).format('h:mm a')} and ${toDateTime(pollingEndTime).format('h:mm a')}"
            state.pollCronString = "0 */${pollInterval} * ? * *"
            if (dateBetween(dtPollingStartTime, dtPollingEndTime, dtNow)) {
                log.info "Starting the polling now..."
                startPolling()
            }
            break
            default:
                log.error "Polling Interval '${pollingInterval}' is invalid."
            break
        }
    }
}

def dateBetween(Date date1, Date date2, Date toCheck){
    return toCheck.after(date1) && toCheck.before(date2)
}

def dtCheck(Date dtPollingStartTime,Date dtPollingEndTime) {
    // Validate and Set Polling Interval
    if (dtPollingStartTime > dtPollingEndTime) {
        def errorMSG = "The polling start time of '${toDateTime(pollingStartTime).format('h:mm a')}' is after the polling end time of '${toDateTime(pollingEndTime).format('h:mm a')}'.  Device polling will not be set."
        sendEvent(name:"error",value:"<font color='red'>${errorMSG}</font>")
        return false
    }
    device.deleteCurrentState('error')
    return true
}

/* groovylint-disable-next-line UnusedPrivateMethod */
private void logsOff() {
    log.warn "debug logging disabled for ${device}"
    device.updateSetting('logEnable', [value: 'false', type: 'bool'] )
}
