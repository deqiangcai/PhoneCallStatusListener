
var phoneCallStatusListener = {
    startPhoneListener: function (args,success, error) {
        cordova.exec(success, error, 'PhoneCallStatusListener', 'startPhoneListener', [args]);
    },

    unRegisterPhoneCall: function (args, success, error) {
        cordova.exec(success, error, "PhoneCallStatusListener", "unRegisterPhoneCall", [args])
    }
}

module.exports = phoneCallStatusListener;