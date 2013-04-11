var login;

$(function() {
	login = (function() {
		var login = {};

		login.loginFrom = $('#loginForm');
		login.registerForm = $('#registerForm');

		// Disable button and show loading icon.
		function formBeforeSubmit(arr, formDom, options) {
			var formMsgDom = $(formDom).find('.formMessage');
			$(formMsgDom).children('*').addClass('hidden');
			$(formMsgDom).children('.loadingIcon').removeClass('hidden');
			$(formMsgDom).find('input[type="submit"]').prop('disabled', true);
		}

		// Show message from server.
		function formSuccess(data, status, xhr, formDom) {
			var formMsgDom = $(formDom).find('.formMessage');
			
			$(formMsgDom).children('*').removeClass('hidden');
			$(formMsgDom).children('.loadingIcon').addClass('hidden');
			
			$(formMsgDom).children('.formMessageText').text(data.message);
			$(formMsgDom).find('input[type="submit"]').prop('disabled', false);
			
			if(data.isSuccess){
				$(formMsgDom).css({color:"green"});
				$(formMsgDom).children('.icon-remove-sign').addClass('hidden');
			} else {
				$(formMsgDom).css({color:"red"});
				$(formMsgDom).children('.icon-ok-sign').addClass('hidden');
			}						
		}

		$(login.loginFrom).ajaxForm({
			beforeSubmit : formBeforeSubmit,
			success : function(data, status, xhr, formDom) {
				// Show message.
				formSuccess(data, status, xhr, formDom);

				if (data.isSuccess) {
					// After delay a while, redirect to editor page.
					setTimeout(function() {
						window.location.replace('CodeEditor');
					}, 1000);
				}
			},
			dataType: 'json'
		});

		$(login.registerForm).ajaxForm({
			beforeSubmit : formBeforeSubmit,
			success : function(data, status, xhr, formDom) {
				// Show message.
				formSuccess(data, status, xhr, formDom);

				if (data.isSuccess) {
					// After delay a while, redirect to login page.
					setTimeout(function() {
						window.location.replace('Login');
					}, 1000);
				}							
			},
			dataType: 'json'
		});

		return login;
	})();
});
