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
