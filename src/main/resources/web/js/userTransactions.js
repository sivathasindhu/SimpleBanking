$(document).ready(function(){

	  $.ajax({
		dataType: "json",
		type: "GET",
	    url: "http://localhost:8080/banking/getCustomerTransactionDetails"
	  }).then(function(data) {
		  var trHTML = '<thead><tr><th>Account No</th><th>Transaction Type</th><th>Transaction Amount</th><th>Balance</th><th>Transaction Date</th></tr></thead>';
		  $.each(data, function (i, item) {
			  trHTML += '<tr><td>' + item.ACCOUNT_NO + '</td><td>' + item.TRANSACTION_TYPE + '</td><td>' + item.TRANSACTION_AMOUNT + '</td><td>' + item.BALANCE + '</td><td>' + item.TRANS_DATE + '</td></tr>';
		  });
	  	  $('#tableUserTransactions').empty().append(trHTML);
	  });

});
