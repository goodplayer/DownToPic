<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>输入下载地址</title>
</head>
<body>
<form name="f" action="Submit.action" method="post">
下载地址:<input name="downurl" style="width: 500px" /><br />
保存的文件名:<input name="savename" style="width: 500px" /><br />
cookie(可选):<input name="cookie" style="width: 500px" /><br />
分卷大小:<input name="partSize" style="width: 500px">(可选, 默认为2140000000, 最大不可超过此值)<br />
<input type="submit" value="提交" /><br />
<a href="DownloadList.action" target="_blank">下载列表</a><br />
<a href="DownloadFile.action" target="_blank">下载某个文件</a><br />
<a href="FileDelete.action" target="_blank">删除某个文件</a><br />
</form>
</body>
</html>