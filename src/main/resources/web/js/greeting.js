$(document).ready(function() {
    $.ajax({
    	dataType: "json",
    	type: "GET",
        url: "http://localhost:8080/banking/greeting"
    }).then(function(data) {
    	$('.customer-name').append(data.name);
        $('.customer-accountNo').append(data.accountNo);
        $('.customer-emailId').append(data.emailId);       
    });
});