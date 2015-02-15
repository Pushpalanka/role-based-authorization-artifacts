<html>

<head></head>

<body>

	<form id="assoc_account_form" name="assoc_account_form" action="../../commonauth"
		method="POST">
		<table>
			<tr>
				<td><input type="hidden" name="sessionDataKey"
					value="<%=request.getParameter("sessionDataKey")%>" /></td>
			</tr>
		</table>
	</form>

	<script type="text/javascript">
		document.forms[0].submit();
	</script>

</body>

</html>
