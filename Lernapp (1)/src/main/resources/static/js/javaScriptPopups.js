document.addEventListener('DOMContentLoaded', function(){
let messageElement = document.getElementById("message");//Holt die Flash-Nachricht
let message = messageElement ? messageElement.textContent.trim() : null;
if(message) {
    var modal = new bootstrap.Modal(document.getElementById('successModal'));
    modal.show();
    }
 });


