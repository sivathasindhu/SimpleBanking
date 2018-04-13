$(document).ready(function(){
	
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/banking/getAllCustomerEntries',
        dataType: 'json',
        success: function(json) {

            var $el = $("#toUserId");
            $el.empty();
            $.each(json, function(value, key) {
                $el.append($("<option></option>").attr("value", key.USER_ID).text(key.NAME+" - "+key.ACCOUNT_NO));
            });														
        }
    });
    
    $("#transferForm").submit(function(event) {
      event.preventDefault();
      
      var post_url = "http://localhost:8080/banking/transfer";
      var request_method = $(this).attr("method");
      var form_data = $(this).serialize();
      
      $.ajax({
          url : post_url,
          type: request_method,
          data : form_data,
          success: function(response){  
        	  $('#status').empty().append(response); 
          },
          error: function(xhr, textStatus, errorThrown) {
        	  $('#status').empty().append(xhr.responseText); 
          } 
      });
      
    });
    
});