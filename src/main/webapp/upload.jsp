<%@ page contentType="text/html;charset=utf-8"%>
<html>
<title>pdf2any</title>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="./public/jquery.uploadify.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="./public/uploadify.css">
<script type="text/javascript">
$(document).ready(function(){
  $("#bt").click(function(){
	 // $.ajax({url:"/pdf2any/SplitPDF?inSplit="+$("#inSplit").val(),async:true});
	  $.get(window.location+"SplitPDF?inSplit="+$("#inSplit").val(),download);
//  $("#myDiv").html(htmlobj.responseText);
//console.log(htmlobj.responseText);
  });
});
function download(data,status){
	window.location.assign(data);
}
</script>
<body>
<h1>pdf分割</h1>
	<form>
		<div id="queue"></div>
		<input id="file_upload" name="file_upload" type="file" multiple="true">
	</form>
	<script type="text/javascript">
		$(function() {
			$('#file_upload').uploadify({
				'buttonText'      : '选择文件',
				'swf'      : './public/uploadify.swf',
				'simUploadLimit' :1,
				'height':20,
				'fileTypeDesc' : '请选PDF文件。',
		        'fileTypeExts' : '*.*',
				'uploader' : 'UploadFile', 
				'onUploadComplete' : function(file) {
					$("#bt").removeAttr("disabled");
				}
			});
		});
	</script>
	<input type="text" id="inSplit"/>
	<div>例：1-5;2-6</div>
<button id="bt" type="button" disabled="disabled">提交</button>
</body>
</html>