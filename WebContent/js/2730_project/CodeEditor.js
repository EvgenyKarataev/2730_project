var codeEditor = 1;

$(function() {
	codeEditor = (function() {
		var codeEditor = {};
		
		/* variables */

		codeEditor.leftCP = $('#leftCP');
		codeEditor.middleCP = $('#middleCP');
		codeEditor.rightCP = $('#rightCP');

		// Editor instance.
		var myCodeMirror = CodeMirror(codeEditor.middleCP.get(0), {
			value : 'public static void main(String[] args){System.out.println("Hello World");}',
			lineWrapping : true,
			lineNumbers : true,
			mode: "text/x-java"
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

		/* init code */

		$('#content').height($(window).height() - 40);
		// codeEditor.autoFormat();
		
		return codeEditor;
	})();
});