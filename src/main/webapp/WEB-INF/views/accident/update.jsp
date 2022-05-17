<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<form action="<c:url value='/save?id=${accident.id}'/>" method='POST'>
    <table>
        <tr>
            ${accident}
        </tr>
        <tr>
            <td>Название:</td>
            <td><input type='text' name='name' value="${accident.name}"></td>
            <td><input type='hidden' name='text' value="${accident.text}"></td>
            <td><input type='hidden' name='address' value="${accident.address}"></td>
        </tr>
        <tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit" value="Сохранить" /></td>
        </tr>
    </table>
</form>
</body>
</html>