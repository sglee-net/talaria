var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect(event) {
	console.log('prepare');
    var socket = new SockJS('/sockJS');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError); 
    
//    event.preventDefault();
}

function onConnected() {
	setConnected(true);
	stompClient.subscribe('/topic/vibration', onMessageReceived);
}

function onError(error) {
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.send("/ap/execute", {}, $("#name").val());//JSON.stringify({'name': $("#name").val()}));
}

function onMessageReceived(payload) {
	// JSON.parse(payload.body).content
//    $("#greetings").append("<tr><td>" + payload.body + "</td></tr>");
    
//    if(Number.isInteger(payload.body)) {
//    	console.log("payload is NaN " + payload.body);
//    	return;
//    }
    if(payload == null || payload.body == null || payload.body == "") {
    	return;
    }
//    var x = new Date();  // current time
//    var y = parseFloat(payload.body);
    var jsonObject = JSON.parse(payload.body);
    var senderId = jsonObject.senderId;
    var listDouble = jsonObject.listDouble;
    var timestamp = parseInt(jsonObject.timestamp);
    if(senderId === "d0") {
	    var count = 0;
	    for(var i = 0; i < listDouble.length; i++) {
	    	if(i % 1000 == 0) {
		    	var x = new Date((timestamp + count*100));
		    	var y = parseFloat(listDouble[i]);
				if(x=="" || y=="") {
					return;
				}
	    		data.push([x,y]);//y[0]]);
	    		count++;
	    	}
	    }
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});

var buffer = [];
var dataSize = 100;
var samplingTimeToUpdateChart = 100; // ms
var data = [];

$(document).ready(function () {
	
	var t = new Date();
    for (var i = dataSize; i > 0; i--) {
    	var x = new Date(t.getTime() - i * 10000);
    	data.push([x, 0.0]);
    }

    var g = new Dygraph(document.getElementById("div_g"), data,
                        {
                          drawPoints: true,
                          showRoller: true,
                          valueRange: [0.0, 0.2],
                          labels: ['time with ms', 'Value']
                        });
    // It sucks that these things aren't objects, and we need to store state in window.
    window.intervalId = setInterval(function() {
    	
    	g.updateOptions( { 'file': data } );
		var i = 0;
		while(data.length >= dataSize) {
			data.shift();
		}
    }, samplingTimeToUpdateChart);
  }
);
