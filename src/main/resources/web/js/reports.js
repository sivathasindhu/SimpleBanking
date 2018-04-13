$(document).ready(function(){

	$('.customerClass').click(function(){
	  $.ajax({
		dataType: "json",
		type: "GET",
	    url: "http://localhost:8080/banking/" + this.title
	  }).then(function(data) {
		  var trHTML = '<thead><tr><th>Account No</th><th>Name</th><th>E-Mail ID</th><th>Balances&nbsp;&nbsp;&nbsp;</th></tr></thead>';
		  $.each(data, function (i, item) {
			  trHTML += '<tr><td>' + item.ACCOUNT_NO + '</td><td>' + item.NAME + '</td><td>' + item.EMAIL_ID + '</td><td>' + item.BALANCE + '</td></tr>';
		  });
	  	  $('#tableId').empty().append(trHTML);
	  });
	  
	});
	
	$('.transactionClass').click(function(){
		  $.ajax({
			dataType: "json",
			type: "GET",
		    url: "http://localhost:8080/banking/" + this.title
		  }).then(function(data) {
			  var trHTML = '<thead><tr><th>Account No</th><th>Name</th><th>E-Mail ID</th><th>Transactions</th></tr></thead>';
			  $.each(data, function (i, item) {
				  trHTML += '<tr><td>' + item.ACCOUNT_NO + '</td><td>' + item.NAME + '</td><td>' + item.EMAIL_ID + '</td><td>' + item.TRANS_COUNT + '</td></tr>';
			  });
		  	  $('#tableId').empty().append(trHTML);
		  });
		  
	});
	
	$('.reportClass').click(function(){
		  $.ajax({
			type: "GET",
		    url: this.href
		  });
	});

});
