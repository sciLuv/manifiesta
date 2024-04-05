var socket = new SockJS('/websocket');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    stompClient.subscribe('/topic/updates', function (update) {
        alert("Mise à jour reçue : " + update.body);
    });
});  