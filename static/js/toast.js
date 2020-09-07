function toast(sMessage, error) {
	var container = $(document.createElement("div"));
	container.addClass("toast");

	var message = $(document.createElement("div"));
	if (error) {
		message.removeClass("message");
		message.addClass("error");
	} else {
		message.removeClass("error");
		message.addClass("message");
	}
	message.html(sMessage);
	message.appendTo(container);

	container.appendTo(document.body);

	var ddelay = (error?5000:2000);
	
	container.delay(100).fadeIn("slow", function() {
		$(this).delay(ddelay).fadeOut("slow", function() {
			$(this).remove();
		});
	});
}