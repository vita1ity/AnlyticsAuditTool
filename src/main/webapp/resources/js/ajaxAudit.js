/*$('#accountSelector').on('change', function() {
	console.log( "blabla" ); 
	console.log( $(this).val() ); 
  $.ajax({
	  url:url,
	  type: "GET"
	}).done (function(data) {
		
	
	}).fail (function(err) {
	    console.error(err);
	});
});
	*/
function getAnalytics(sel) {
	
	if (sel.value != "default") {
		console.log(sel.value);
		var res = sel.value.split(",");
		var accountId = res[0];
		var propertyId = res[1];
		var viewId = res[2];
		
		console.log(accountId);
		console.log(propertyId);
		console.log(viewId);
		
		var url = $('#accountSelector').data("url");
		console.log(url);
		$.ajax({
			  url:url,
			  type: "POST",
			  dataType: "JSON",
			  data: {accountId: accountId, propertyId: propertyId, viewId: viewId}
			}).done (function(data) {
				var analyticsData = "";
				var currency = data.basicSettings.currency;
				var timezone = data.basicSettings.timezone;
				var pass = data.basicSettings.pass;
				console.log(currency);
				console.log(timezone);
				console.log(pass);
				analyticsData += "<p>Currency: " + currency + "</p>\n";
				analyticsData += "<p>Timezone: " + timezone + "</p>\n";
				if (pass) {
					analyticsData += "<p>Pass</p>\n";
				}
				else {
					analyticsData += "<p>Fail</p>\n";
				}
				$('#analytics').html(analyticsData);
			
			}).fail (function(err) {
			    console.error(err);
			});
	}
 }	