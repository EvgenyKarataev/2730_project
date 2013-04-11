var codeEditor = 1;

$(function() {
	codeEditor = (function() {
		var codeEditor = {};

		/* variables */

		codeEditor.leftCP = $('#leftCP');
		codeEditor.middleCP = $('#middleCP');
		codeEditor.rightCP = $('#rightCP');

		// Control buttons.
		codeEditor.undoBtn = $('#undoBtn');
		codeEditor.redoBtn = $('#redoBtn');
		codeEditor.runBtn = $('#runBtn');
		codeEditor.saveBtn = $('#saveBtn');
		codeEditor.formatBtn = $('#formatBtn');		
		
		// Other components.
		codeEditor.runtimeSelect = $('#languageSelect');
		codeEditor.savePanel = $('#savePanel');
		codeEditor.outputPanel = $('#outputPanel');

		/* private variables */
		var isRunningCode = false;
		
		// Editor instance.
		var myCodeMirror = CodeMirror(
				codeEditor.middleCP.get(0),
				{
					value : 'public static void main(String[] args){\nSystem.out.println("Hello World");\n}',
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
				
				$(codeEditor.outputPanel).empty();
				
				if(data.isSuccess){
					$(codeEditor.outputPanel).append('<div>Your code compiled and ran successfully.<div>');
				}else{
					$(codeEditor.outputPanel).append('<div class="errorText">We are unable to run your code, please check following output and revise your code.<div>');
				}
				
				data.output = data.output.replace(/\n/ig, '<br/>');
				$(codeEditor.outputPanel).append('<div class="outputText">' + data.output +'</div>');
				
				isRunningCode = false;
				
			}, 'json').fail(function() { 
				$(codeEditor.outputPanel).append('<div class="errorText">Server has encountered errors, please try again.<div>');
				isRunningCode = false;
			});
		}
		
		// Save code file.
		// If file doesn't exist, create a nw file.
		function saveFile(filename, code){
			
		}

		/* init code */

		$('#content').height($(window).height() - 40);
		// codeEditor.autoFormat();

		$(codeEditor.undoBtn).click(function(e){myCodeMirror.getDoc().undo();});
		$(codeEditor.redoBtn).click(function(e){myCodeMirror.getDoc().redo();});
		$(codeEditor.formatBtn).click(function(e){});
		
		
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
		
		// Bind save file btn event.
		// Open save panel
		$(codeEditor.saveBtn).click(function(e){
			
			// If save panel is open, close it.
			if(!$(codeEditor.savePanel).hasClass('hidden')){
				$(codeEditor.savePanel).addClass('hidden');
				return;
			}
			
			// Open save panel.
			$(codeEditor.savePanel).removeClass('hidden');
			
			// Show default filename nased on type select.
			var filenameInput = $(codeEditor.savePanel).find('#filenameInput');
			
			// Focus and select all.	
			setTimeout(function(){$(filenameInput).focus().select();}, 100);
			
			if($(codeEditor.runtimeSelect).val() == 'Java'){
				$(filenameInput).val('NewFile.java');
			}else if($(codeEditor.runtimeSelect).val() == 'Python'){
				$(filenameInput).val('NewFile.py');
			}
		});
		
		// Hide save panel.
		$(codeEditor.savePanel).find('.icon-remove-circle').click(function(e){
			$(codeEditor.savePanel).addClass('hidden');
		});
		
		// Save file.
		$(codeEditor.savePanel).find('.icon-save').click(function(e){
			
		});
		
		// Load mediaContainer.
		mediaContainer.loadMediaContainer($('#fileContainerWrapper'));
		
		return codeEditor;
	})();
});