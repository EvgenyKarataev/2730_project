var codeEditor = 1;

$(function() {
	codeEditor = (function() {
		var codeEditor = {};

		/* variables */

		codeEditor.leftCP = $('#leftCP');
		codeEditor.middleCP = $('#middleCP');
		codeEditor.rightCP = $('#rightCP');

		// Control buttons.
		codeEditor.runBtn = $('#runBtn');
		codeEditor.saveBtn = $('#saveBtn');
		codeEditor.formatBtn = $('#formatBtn');
		codeEditor.shareBtn = $('#shareBtn');
		
		codeEditor.runtimeSelect = $('#languageSelect');
		codeEditor.outputPanel = $('#outputPanel');

		/* private variables */
		var isRunningCode = false;
		
		// Editor instance.
		var myCodeMirror = CodeMirror(
				codeEditor.middleCP.get(0),
				{
					value : 'public static void main(String[] args){System.out.println("Hello World");}',
					lineWrapping : true,
					lineNumbers : true,
					mode : "text/x-java"
				});

		/* public functions */

		codeEditor.autoFormat = function() {
			var totalLines = myCodeMirror.lineCount();
			myCodeMirror.autoFormatRange({
				line : 0,
				ch : 0
			}, {
				line : totalLines - 1
			});
		};

		/* helper functions */

		// Run code.
		// runtimeType should be a supported language.
		function runCode(runtimeType, code) {
			isRunningCode = true;
			
			$.post(appRootPath + "/RunCode", {
				runtimeType : runtimeType,
				code : code
			}, function(data, statusText, xhr){
				
				if(data.isSuccess){
					$(codeEditor.outputPanel).append('<div>Your code compiles and runs successfully.<div>');
				}else{
					$(codeEditor.outputPanel).append('<div class="errorText">We are unable to run your code, please check following output.<div>');
				}
				
				data = data.replace('\n', '<br/>');
				$(codeEditor.outputPanel).text(data);
				
				isRunningCode = false;
			}).fail(function() { 
				$(codeEditor.outputPanel).append('<div class="errorText">Server has encountered errors, please try again.<div>');
				isRunningCode = false;
			});
		}

		/* init code */

		$('#content').height($(window).height() - 40);
		// codeEditor.autoFormat();

		// Bind run code event.
		$(codeEditor.runBtn).click(function(e){
			
			// Show processing text.
			$(codeEditor.outputPanel).text('Processing...');
			
			var runtimeType = $(codeEditor.runtimeSelect).val();
			var code = myCodeMirror.getDoc().getValue();
			
			console.log(runtimeType);
			console.log(code);
			
			runCode(runtimeType, code);
		});
		
		return codeEditor;
	})();
});