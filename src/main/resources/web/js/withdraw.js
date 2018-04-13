$(document).ready(function(){
	
    $("#withdrawForm").submit(function(event) {
      event.preventDefault();
      
      var post_url = "http://localhost:8080/banking/withdraw";
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