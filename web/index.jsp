<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>SSLChain</title>
    </head>
    <body>
        <form action="do" method="post">
            <textarea name="certificate" placeholder="Paste your CRT here..." cols="50" rows="30"></textarea><br />
            <input type="checkbox" id="intermediateOnly" name="intermediateOnly" value="1" /> <label for="intermediateOnly">Intermediates only</label><br />
            <input type="submit" value="Chain me" />
        </form>
    </body>
</html>
