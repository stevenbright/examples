
(function ($, ns) {
    function clearLog() {
        $("#log-holder li").remove()
    }


    ns.log = (function () {
        var count = 0
        var MAX_LOG_RECORDS = 64

        return function (text) {
            ++count
            if (count > MAX_LOG_RECORDS) {
                clearLog()
                count = 0
            }

            var newElem = $("<li></li>")
            $(newElem).text(text)
            $("#log-holder").append(newElem)
        }
    })()


    $("#clear-log").click(function() { clearLog() })
})(jQuery, app)
