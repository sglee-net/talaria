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
    
    event.preventDefault();
}

function onConnected() {
	setConnected(true);
	stompClient.subscribe('/topic/reply', onMessageReceived);
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
    $("#greetings").append("<tr><td>" + payload + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});

$(document).ready(function () {
	var data_size = 1000;
    var data = [];
//    var x_init = new Date();
//    var y_init = 0;
    data.push([new Date(), 0]);

//    for (var i = 10; i >= 0; i--) {
//      var x = new Date(t.getTime() - i * 1000);
//      data.push([x, Math.random()]);
//    }

    var g = new Dygraph(document.getElementById("div_g"), data,
                        {
                          drawPoints: true,
                          showRoller: true,
                          valueRange: [0.0, 1.2],
                          labels: ['Time', 'Value']
                        });
    // It sucks that these things aren't objects, and we need to store state in window.
    window.intervalId = setInterval(function() {
      var x = new Date();  // current time
      var y = Math.random();
      data.push([x, y]);
      if(data.length > data_size) {
    	  data.shift();
      }
      g.updateOptions( { 'file': data } );
    }, 1000);
  }
);
