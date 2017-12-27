function connectWebViewJavascriptBridge(callback) {
    if (window.WebViewJavascriptBridge) {
        callback(WebViewJavascriptBridge)
    } else {
        document.addEventListener('WebViewJavascriptBridgeReady',
        function() {
            callback(WebViewJavascriptBridge)
        },
        false);
    }
}

connectWebViewJavascriptBridge(function(bridge) {

    bridge.init(function(message, responseCallback) {
        console.log('JS got a message', message);
        var data = {
            'Javascript Responds': 'Wee!'
        };
        console.log('JS responding with', data);
        responseCallback(data);
    });

    bridge.registerHandler("getMusicStatus",
    function(data, responseCallback) {
        var audios = document.getElementsByTagName("audio");
        var responseData = false;
        if (audios.length == 0) {
            responseData = false;
        }
        for (var i = 0; i < audios.length; i++) {
            if (audios[i].duration > 0 && !audios[i].paused) {
                responseData = true;
            }
        }
        responseCallback(responseData);
    });

    bridge.registerHandler("setPlayOrPaused",
    function(data, responseCallback) {
        var audios = document.getElementsByTagName("audio");
        var responseData = false;
        if (audios.length == 0) {
            responseData = false;
        } else {
            for (var i = 0; i < audios.length; i++) {
                if (audios[i].duration > 0 && !audios[i].paused) {
                    audios[i].pause();
                    responseData = false;
                } else if (audios[i].duration > 0) {
                    audios[i].play();
                    responseData = true;
                    break;
                }
            }
        }
        responseCallback(responseData);
    });

    bridge.registerHandler("getMusicName",
    function(data, responseCallback) {
        var songname = document.getElementById("songNameid");
        var showMusicName;
        if (songname) {
            showMusicName = songname.innerHTML;
            responseCallback(showMusicName);
            return;
        }
        var songname = document.getElementById("songname");
        var singername = document.getElementById("singername");
        if (songname && singername) {
            showMusicName = songname.innerHTML + '-' + singername.innerHTML;
        } else if (songname) {
            showMusicName = songname.innerHTML;
        } else if (singername) {
            showMusicName = singername.innerHTML;
        }
        responseCallback(showMusicName);
    });
})