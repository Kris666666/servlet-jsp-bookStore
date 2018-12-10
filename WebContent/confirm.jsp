<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p:user />
	<!-- 确认支付form -->
	<form action="https://www.yeepay.com/app-merchant-proxy/node"
		method="get">
		<h3>订单号：${p2_Order},付款金额 ：0.01</h3>
		<input type="" name="pd_FrpId" value="${pd_FrpId }" /> <input
			type="" name="p0_Cmd" value="${p0_Cmd }" /> <input
			type="" name="p1_MerId" value="${p1_MerId }" /> <input
			type="" name="p2_Order" value="${p2_Order }" /> <input
			type="" name="p3_Amt" value="${p3_Amt }" /> <input
			type="" name="p4_Cur" value="${p4_Cur }" /> <input
			type="" name="p5_Pid" value="${p5_Pid }" /> <input
			type="" name="p6_Pcat" value="${p6_Pcat }" /> <input
			type="" name="p7_Pdesc" value="${p7_Pdesc }" /> <input
			type="" name="p8_Url" value="${p8_Url }" /> <input
			type="" name="p9_SAF" value="${p9_SAF }" /> <input
			type="" name="pa_MP" value="${pa_MP }" /> <input type="hidden"
			name="pr_NeedResponse" value="${pr_NeedResponse }" /> <input
			type="" name="hmac" value="${hmac }" /> 
	</form>
	<script type="text/javascript">
		document.forms[0].submit();
	</script>
</body>
</html>